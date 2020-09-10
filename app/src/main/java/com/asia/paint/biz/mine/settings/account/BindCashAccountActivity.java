package com.asia.paint.biz.mine.settings.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityBindCashAccountBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.CashService;
import com.asia.paint.base.network.bean.CashAccount;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.login.LoginViewModel;
import com.asia.paint.biz.login.SmsCode;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2020-01-05.
 */
public class BindCashAccountActivity extends BaseTitleActivity<ActivityBindCashAccountBinding> {
    @Override
    protected String middleTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_cash_account;
    }

    private UserInfo mUserInfo;

    private static final int MAX_COUNT = 100;
    private static final int MIN_COUNT = 0;
    private static final String KEY_ACCOUNT_ID = "KEY_ACCOUNT_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    public static final int REQUEST_BIND_ACCOUNT = 0x3321;
    private int count = MAX_COUNT;
    private SmsCode mCurSmsCodeStatus;
    private int mType;
    private CashAccount mCashAccount;
    private Runnable mSmsCodeCountRunnable = () -> {

        if (count == MIN_COUNT) {
            setSmsCodeStatus(SmsCode.ENABLE);
            count = MAX_COUNT;
        } else {
            setSmsCodeStatus(SmsCode.COUNT);
            count--;
        }
    };

    private CashAccountViewModel mViewModel;
    private LoginViewModel mLoginViewModel;


    public static void start(Activity activity, int type, CashAccount account) {
        Intent intent = new Intent(activity, BindCashAccountActivity.class);
        intent.putExtra(KEY_ACCOUNT_ID, type);
        if (account != null) {
            intent.putExtra(KEY_ACCOUNT, account);
        }
        activity.startActivityForResult(intent, REQUEST_BIND_ACCOUNT);
    }

    @Override
    protected void intentValue(Intent intent) {
        mType = intent.getIntExtra(KEY_ACCOUNT_ID, CashService.ZHI_FU_BAO);
        mCashAccount = intent.getParcelableExtra(KEY_ACCOUNT);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = AsiaPaintApplication.getUserInfo();
        mViewModel = getViewModel(CashAccountViewModel.class);
        mLoginViewModel = getViewModel(LoginViewModel.class);
        mBinding.etSmsCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mBinding.tvSendSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                count = MAX_COUNT;
                setSmsCodeStatus(SmsCode.COUNT);
                mLoginViewModel.requestSmsCode(getPhone(), "alipay");
            }
        });


        mBinding.btnNext.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String name = getText(mBinding.etName);
                String account = getText(mBinding.etAccount);
                if (isBankAccount()) {
                    account = getText(mBinding.etBankAccount);
                }
                String bank = getText(mBinding.etBank);
                String establishBank = getText(mBinding.etEstablishBank);
                String idcard = getText(mBinding.etIdcard);
                String tel = getText(mBinding.etTel);
                if (isBankAccount() && TextUtils.isEmpty(account)) {
                    AppUtils.showMessage("请输入卡号");
                    return;
                }
                if (isZhiFuBaoAccount() && TextUtils.isEmpty(account)) {
                    AppUtils.showMessage("请输入账号");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    AppUtils.showMessage("请输入姓名");
                    return;
                }
                if (isBankAccount() && TextUtils.isEmpty(bank)) {
                    AppUtils.showMessage("请输入银行");
                    return;
                }
                if (TextUtils.isEmpty(idcard)) {
                    AppUtils.showMessage("请输入身份证");
                    return;
                }
                if (TextUtils.isEmpty(tel)) {
                    AppUtils.showMessage("请输入手机号");
                    return;
                }
                if (isEdit()) {
                    mViewModel.editCashAccount(mCashAccount.id, mType, account, name, bank, establishBank,idcard,tel)
                            .setCallback(result -> {
                                if (result) {
                                    finish();
                                }
                            });
                } else {
                    mViewModel.addCashAccount(mType, account, name, bank, establishBank,idcard,tel)
                            .setCallback(result -> {
                                if (result) {
                                    finish();
                                }
                            });
                }
            }
        });
        setSmsCodeStatus(SmsCode.ENABLE);
        setAccountType();
    }

    private boolean isEdit() {
        return mCashAccount != null;
    }

    private boolean isZhiFuBaoAccount() {
        return mType == CashService.ZHI_FU_BAO;
    }

    private boolean isBankAccount() {
        return mType == CashService.BANK;
    }

    private void setAccountType() {
        mBinding.layoutZhiFuBaoAccount.setVisibility(isZhiFuBaoAccount() ? View.VISIBLE : View.GONE);
        mBinding.layoutBankAccount.setVisibility(isBankAccount() ? View.VISIBLE : View.GONE);
        mBinding.layoutBank.setVisibility(isBankAccount() ? View.VISIBLE : View.GONE);
        mBinding.layoutIdcard.setVisibility(isBankAccount() ? View.VISIBLE : View.GONE);
        mBinding.layoutEstablishBank.setVisibility(isBankAccount() ? View.VISIBLE : View.GONE);
        mBinding.layoutTel.setVisibility(isBankAccount() ? View.VISIBLE : View.GONE);
        mBaseBinding.tvTitle.setText(isZhiFuBaoAccount() ? "绑定支付宝收款账号" : "绑定银行卡收款账号");
        mBinding.btnNext.setText(isZhiFuBaoAccount() ? "绑定支付宝" : "绑定银行卡");
        if (mCashAccount != null) {
            mBinding.etAccount.setText(mCashAccount.account);
            mBinding.etBankAccount.setText(mCashAccount.account);
            mBinding.etName.setText(mCashAccount.name);
            mBinding.etIdcard.setText(mCashAccount.idcard);
            mBinding.etBank.setText(mCashAccount.bank);
            mBinding.etEstablishBank.setText(mCashAccount.bank_name);
            mBinding.etTel.setText(mCashAccount.tel);
        }
    }


    private String getPhone() {
        return mUserInfo != null ? mUserInfo.mobile : "";
    }

    private void setSmsCodeStatus(SmsCode code) {
        mCurSmsCodeStatus = code;
        switch (mCurSmsCodeStatus) {
            case COUNT:
                mBinding.tvSendSmsCode.setEnabled(false);
                String smsCodeCountFormat = "已发送（%ss）";
                mBinding.tvSendSmsCode.setText(String.format(smsCodeCountFormat, count));
                mBinding.tvSendSmsCode.postDelayed(mSmsCodeCountRunnable, 1000);
                break;
            case ENABLE:
                mBinding.tvSendSmsCode.setEnabled(true);
                mBinding.tvSendSmsCode.setText("发送验证码");
                break;
            case DISABLE:
                mBinding.tvSendSmsCode.setEnabled(false);
                mBinding.tvSendSmsCode.setText("发送验证码");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mBinding.tvSendSmsCode.removeCallbacks(mSmsCodeCountRunnable);
        super.onDestroy();
    }

}
