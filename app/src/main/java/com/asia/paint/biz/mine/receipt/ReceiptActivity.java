package com.asia.paint.biz.mine.receipt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityReceiptBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.ReceiptService;
import com.asia.paint.base.network.bean.Receipt;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-06.
 */
public class ReceiptActivity extends BaseActivity<ActivityReceiptBinding> {
    public static final int REQUEST_CODE_RECEIPT = 0xFD23;
    public static final String KEY_RECEIPT_NEW = "KEY_RECEIPT_NEW";
    public static final String KEY_RECEIPT = "KEY_RECEIPT";

    private Receipt mReceipt;
    private static OnChangeCallback<Receipt> mOnChangeCallback;

    public static void start(Activity activity, Receipt receipt, OnChangeCallback<Receipt> onChangeCallback) {
        Intent intent = new Intent(activity, ReceiptActivity.class);
        if (receipt != null) {
            intent.putExtra(KEY_RECEIPT, receipt);
        }
        mOnChangeCallback = onChangeCallback;
        activity.startActivityForResult(intent, REQUEST_CODE_RECEIPT);
    }

    @Override
    protected void intentValue(Intent intent) {
        mReceipt = intent.getParcelableExtra(KEY_RECEIPT);
    }

    private ReceiptViewModel mReceiptViewModel;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "开具发票";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiptViewModel = getViewModel(ReceiptViewModel.class);
        setCompany(false);
        mBinding.cbEReceipt.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbPaperReceipt.setChecked(!isChecked);
                showPersonalCheck();
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbEReceipt.isChecked();
            }
        });

        mBinding.cbPaperReceipt.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbEReceipt.setChecked(!isChecked);
                showPersonalCheck();
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbPaperReceipt.isChecked();
            }
        });
        mBinding.cbCompany.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbPersonal.setChecked(!isChecked);
                setCompany(isChecked);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbCompany.isChecked();
            }
        });
        mBinding.cbPersonal.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                setCompany(!isChecked);
                mBinding.cbCompany.setChecked(!isChecked);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbPersonal.isChecked();
            }
        });
        mBinding.btnSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                int type = mBinding.cbPersonal.isChecked() ? ReceiptService.PERSONAL : ReceiptService.COMPANY;
                int receipt = mBinding.cbEReceipt.isChecked() ? ReceiptService.TYPE_ELECTRONIC : ReceiptService.TYPE_PAPER;
                String title = getText(mBinding.etCompanyName);
                if (TextUtils.isEmpty(title)) {
                    AppUtils.showMessage(type == ReceiptService.PERSONAL ? "请填写抬头" : "请填写单位名称");
                    return;
                }
                String sn = getText(mBinding.etCompanyNo);
                if (!isEPersonalReceipt(type, receipt) && TextUtils.isEmpty(sn)) {
                    AppUtils.showMessage("请填写纳税人识别号");
                    return;
                }
                String address = getText(mBinding.etCompanyAddress);
                String tel = getText(mBinding.etCompanyTel);
                String bank = getText(mBinding.etCompanyBank);
                String bankAccount = getText(mBinding.etCompanyBankAccount);
                String email = getText(mBinding.etEmail);
                String phone = getText(mBinding.etPhone);

                if (isPaperCompanyReceipt(type, receipt)) {
                    if (TextUtils.isEmpty(address)) {
                        AppUtils.showMessage("请填写企业注册地址");
                        return;
                    }
                    if (TextUtils.isEmpty(tel)) {
                        AppUtils.showMessage("请填写企业注册电话");
                        return;
                    }
                    if (TextUtils.isEmpty(bank)) {
                        AppUtils.showMessage("请填写企业开户银行");
                        return;
                    }
                    if (TextUtils.isEmpty(bankAccount)) {
                        AppUtils.showMessage("请填写企业银行账号");
                        return;
                    }
                }
                int isDefault = mBinding.scDefault.isChecked() ? 1 : 0;

                if (mReceipt == null) {
                    mReceiptViewModel.addReceipt(receipt, title, type, sn, bank, bankAccount, address, tel, phone, email, isDefault)
                            .setCallback(result -> {
                                sendNewReceipt(type, receipt, title, sn, address, tel, bank, bankAccount, email, phone, isDefault, result);
                            });
                } else {
                    mReceiptViewModel.editReceipt(mReceipt.id, receipt, title, type, sn, bank, bankAccount, address, tel, phone, email, isDefault).setCallback(
                            result -> {
                                if (result) {
                                    sendNewReceipt(type, receipt, title, sn, address, tel, bank, bankAccount, email, phone, isDefault, mReceipt.id);
                                }
                            });
                }
            }
        });
        setReceiptData();
    }

    private boolean isEPersonalReceipt(int type, int receipt) {
        return type == ReceiptService.PERSONAL && receipt == ReceiptService.TYPE_ELECTRONIC;
    }

    private boolean isPaperCompanyReceipt(int type, int receipt) {
        return type == ReceiptService.COMPANY && receipt == ReceiptService.TYPE_PAPER;
    }

    private void showPersonalCheck() {
        boolean checked = mBinding.cbPaperReceipt.isChecked();
        mBinding.cbPersonal.setVisibility(checked ? View.GONE : View.VISIBLE);
        mBinding.tvReceiptPersonalTips.setVisibility(checked ? View.GONE : View.VISIBLE);
        if (checked) {
            mBinding.cbCompany.setChecked(true);
            mBinding.cbPersonal.setChecked(false);
            setCompany(true);
        }
    }


    private void sendNewReceipt(int type, int receipt, String title, String sn, String address, String tel, String bank, String bankAccount, String email,
            String phone, int isDefault, int id) {
        if (mReceipt == null) {
            mReceipt = new Receipt();
        }
        mReceipt.id = id;
        mReceipt.title = title;
        mReceipt.receipt = receipt;
        mReceipt.type = type;
        mReceipt.is_default = isDefault;
        mReceipt.address = address;
        mReceipt.sn = sn;
        mReceipt.tel = phone;
        mReceipt.company_tel = tel;
        mReceipt.bank = bank;
        mReceipt.bank_sn = bankAccount;
        mReceipt.email = email;
  /*      Intent intent = new Intent();
        intent.putExtra(KEY_RECEIPT_NEW, mReceipt);
        setResult(RESULT_OK, intent);*/
        if (mOnChangeCallback != null) {
            mOnChangeCallback.onChange(mReceipt);
        }
        finish();
    }

    private void setReceiptData() {
        if (mReceipt != null) {
            mBinding.cbEReceipt.setChecked(mReceipt.receipt == ReceiptService.TYPE_ELECTRONIC);
            mBinding.cbPaperReceipt.setChecked(mReceipt.receipt == ReceiptService.TYPE_PAPER);
            mBinding.cbPersonal.setChecked(mReceipt.type == ReceiptService.PERSONAL);
            mBinding.cbCompany.setChecked(mReceipt.type == ReceiptService.COMPANY);
            mBinding.etCompanyName.setText(mReceipt.title);
            mBinding.etCompanyNo.setText(mReceipt.sn);
            mBinding.etCompanyAddress.setText(mReceipt.address);
            mBinding.etCompanyTel.setText(mReceipt.company_tel);
            mBinding.etCompanyBank.setText(mReceipt.bank);
            mBinding.etCompanyBankAccount.setText(mReceipt.bank_sn);
            mBinding.etEmail.setText(mReceipt.email);
            mBinding.etPhone.setText(mReceipt.tel);
            mBinding.scDefault.setChecked(mReceipt.isDefault());
        } else {
            mBinding.cbPersonal.setChecked(true);
            mBinding.cbEReceipt.setChecked(true);
        }
    }

    private void setCompany(boolean company) {
        mBinding.layoutCompanyAddress.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.dividerCompanyAddress.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.layoutCompanyTel.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.dividerCompanyTel.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.layoutCompanyBank.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.dividerCompanyBank.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.layoutCompanyBankAccount.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.dividerCompanyBankAccount.setVisibility(company ? View.VISIBLE : View.GONE);
        mBinding.tvReceiptTitle.setText(company ? "单位名称" : "发票抬头");
        mBinding.etCompanyName.setHint(company ? "请填写单位名称" : "抬头名称");
    }
}
