package com.asia.paint.biz.mine.seller.score.cash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityCashBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.CashService;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.base.network.bean.CashAccount;
import com.asia.paint.base.network.bean.CashInfoRsp;
import com.asia.paint.base.network.bean.TaxRsp;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.login.LoginViewModel;
import com.asia.paint.biz.login.SmsCode;
import com.asia.paint.biz.mine.seller.goals.WebActivity;
import com.asia.paint.biz.mine.settings.account.BindCashAccountActivity;
import com.asia.paint.biz.mine.settings.account.CashAccountViewModel;
import com.asia.paint.biz.pay.CashPayDialog;
import com.asia.paint.biz.pay.password.SetPayPwdActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CommonUtil;
import com.asia.paint.utils.utils.DigitUtils;
import com.asia.paint.utils.utils.SpanText;

import java.util.List;

/**
 * @author by chenhong14 on 2020-01-05.
 */
public class CashActivity extends BaseTitleActivity<ActivityCashBinding> {
    private static final String KEY_CASH_TYPE = "KEY_CASH_TYPE";
    private static final String KEY_RECEIPT = "KEY_RECEIPT";
    private static final String CASH_EVENT = "tixian";
    private int mType;
    private String mReceipt = "0";
    private CashAccountViewModel mViewModel;
    private static final int MAX_COUNT = 100;
    private static final int MIN_COUNT = 0;
    private CashAccount mCashAccount;
    private CashInfoRsp.ScoreInfo mScoreInfo;
    private int count = MAX_COUNT;
    private SmsCode mCurSmsCodeStatus;
    private UserInfo mUserInfo;
    private LoginViewModel mLoginViewModel;

    private Runnable mSmsCodeCountRunnable = () -> {

        if (count == MIN_COUNT) {
            setSmsCodeStatus(SmsCode.ENABLE);
            count = MAX_COUNT;
        } else {
            setSmsCodeStatus(SmsCode.COUNT);
            count--;
        }
    };

    private String mScore;

    private Runnable mQueryTaxRunnable = new Runnable() {
        @Override
        public void run() {
            final String score = mScore;
            if (!TextUtils.isEmpty(score)) {
                String toMoney = String.valueOf(DigitUtils.parseFloat(score, 0) / 10);
                mViewModel.getTax(toMoney).setCallback(new OnChangeCallback<TaxRsp>() {
                    @Override
                    public void onChange(TaxRsp result) {
                        if (result != null) {
                            setTaxTips(toMoney, result.sn);
                        }
                    }
                });
            }

        }
    };

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
    protected String middleTitle() {
        return "提现";
    }

    public static void start(Context context, int type, String receipt) {
        Intent intent = new Intent(context, CashActivity.class);
        intent.putExtra(KEY_CASH_TYPE, type);
        intent.putExtra(KEY_RECEIPT, receipt);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mType = intent.getIntExtra(KEY_CASH_TYPE, CashService.ZHI_FU_BAO);
        mReceipt = intent.getStringExtra(KEY_RECEIPT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = AsiaPaintApplication.getUserInfo();
        mLoginViewModel = getViewModel(LoginViewModel.class);
        mViewModel = getViewModel(CashAccountViewModel.class);
        mBinding.tvBindAccount.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BindCashAccountActivity.start(CashActivity.this, mType, mCashAccount);
            }
        });
        mBinding.tvAllScore.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mScoreInfo != null) {
                    mBinding.etCashScore.setText(String.valueOf(mScoreInfo.score));
                }
            }
        });
        mBinding.btnSureCash.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                String account = mCashAccount != null ? mCashAccount.account : "";
                if (TextUtils.isEmpty(account)) {
                    AppUtils.showMessage("请绑定提现账号");
                    return;
                }
                String score = getText(mBinding.etCashScore);
                if (TextUtils.isEmpty(score)) {
                    AppUtils.showMessage("请输入提现积分");
                    return;
                }
                if (!mBinding.cbCheckAgree.isChecked()) {
                    AppUtils.showMessage("请先同意《提现协议》");
                    return;
                }
                String smsCode = getText(mBinding.etSmsCode);
                if (TextUtils.isEmpty(smsCode)) {
                    AppUtils.showMessage("请输入验证码");
                    return;
                }

                int mode = mBinding.cbCheckReceipt.isChecked() ? 1 : 2;
                String receipt = getText(mBinding.etReceiptHeader);

                float toMoney = DigitUtils.parseFloat(score, 0) / 10;
                CashPayDialog cashPayDialog = CashPayDialog.createInstance(score, String.valueOf(toMoney), getTax());
                cashPayDialog.setChangeCallback(payword ->
                        mViewModel.checkSmsCode(getPhone(), CASH_EVENT, smsCode)
                                .setCallback(result -> {
                                    if (result) {
                                        mViewModel.cash(score, mType, mCashAccount.id, mode, payword, receipt).setCallback(success -> {
                                            if (success) {
                                                loadCashInfo();
                                            }
                                        });
                                    }
                                }));
                cashPayDialog.show(CashActivity.this);
            }
        });
        mBinding.tvSendSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                count = MAX_COUNT;
                setSmsCodeStatus(SmsCode.COUNT);
                mLoginViewModel.requestSmsCode(getPhone(), CASH_EVENT);
            }
        });
        setType();
        setSmsCodeStatus(SmsCode.ENABLE);
        mBinding.tvPhone.setText(CommonUtil.hidePhone(getPhone()));
        mBinding.cbCheckReceipt.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbCheckReceiptSelf.setChecked(!isChecked);
                mBinding.layoutReceiptHeader.setVisibility(View.GONE);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbCheckReceipt.isChecked();
            }
        });
        mBinding.cbCheckReceiptSelf.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbCheckReceipt.setChecked(!isChecked);
                mBinding.layoutReceiptHeader.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbCheckReceiptSelf.isChecked();
            }
        });
        mBinding.etCashScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                computeTax();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setCashItems();
        mBinding.cbCheckReceipt.setChecked(true);
    }

    private void setCashItems() {

        String statement = "点击提现默认同意《提现协议》";
        String target = "《提现协议》";
        SpanText spanText = new SpanText.Builder()
                .origin(statement)
                .target(target)
                .setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        WebActivity.start(CashActivity.this, OtherService.CASH_ITEMS);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(getResources().getColor(R.color.color_1054CB));
                        ds.setUnderlineText(false);
                    }
                }).build();

        mBinding.tvCashItems.setText(spanText.toSpan());
        mBinding.tvCashItems.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String getPhone() {
        return mUserInfo != null ? mUserInfo.mobile : "";
    }

    private String getTax() {
        return mScoreInfo != null ? mScoreInfo.point : "";
    }

    private void computeTax() {
        mScore = getText(mBinding.etCashScore);
        if (TextUtils.isEmpty(mScore)) {
            setTaxTips("0", "0");
            return;
        }
        mBinding.etCashScore.removeCallbacks(mQueryTaxRunnable);
        mBinding.etCashScore.postDelayed(mQueryTaxRunnable, 500);
    }

    private void setTaxTips(String money, String tax) {

        mBinding.tvCashTips.setText(String.format("您提现%s元，根据税法规定本次提现需要承担税费%s元，如提供提现金额的对公发票可减免抵扣税费，可在下方提现税费处理方式选择。",
                money, tax));
        setReceipt(tax);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadCashInfo();
    }

    private void loadCashInfo() {
        mViewModel.queryCashInfo(mType).setCallback(this::updateCashInfo);
    }

    private void setType() {
        mBinding.tvAccountType.setText(isZhiFuBaoAccount() ? "提现到支付宝：" : "提现到银行卡：");
    }

    private void updateCashInfo(CashInfoRsp cashInfoRsp) {
        if (cashInfoRsp == null) {
            return;
        }
        List<CashAccount> accounts = cashInfoRsp.account;
        if (accounts != null) {
            for (CashAccount account : accounts) {
                if (account != null) {
                    if (account.type == CashService.ZHI_FU_BAO && mType == CashService.ZHI_FU_BAO) {
                        mCashAccount = account;
                        mBinding.tvCashAccount.setText(String.format("%s (%s)", account.name, account.account));
                    } else if (account.type == CashService.BANK && mType == CashService.BANK) {
                        mCashAccount = account;
                        mBinding.tvCashAccount.setText(String.format("%s (%s)", account.bank, account.account));
                    }
                }
            }
        }
        mBinding.tvBindAccount.setText(mCashAccount == null ? "绑定" : "换绑");
        mScoreInfo = cashInfoRsp.score_info;
        if (mScoreInfo != null) {
            mBinding.etCashScore.setHint(String.format("本次最多可提现%s积分", mScoreInfo.score));
            mBinding.tvCanCashTips.setText(String.format("可提现%s积分=￥%s", mScoreInfo.score, mScoreInfo.score / 10));
            mBinding.tvCashMax.setText(String.format("每日提现上线%s积分", mScoreInfo.max));

        }
        if (!cashInfoRsp.hasPayword()) {
            new MessageDialog.Builder()
                    .title("支付密码")
                    .message("为了您的资金安全，请设置支付密码")
                    .setCancelButton("取消", new OnNoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View view) {
                            finish();
                        }
                    })
                    .setSureButton("确认", v ->
                            CashActivity.this.startActivity(new Intent(CashActivity.this, SetPayPwdActivity.class)))
                    .build()
                    .show(CashActivity.this);
        }
    }

    private void setReceipt(String taxStr) {
        float tax = DigitUtils.parseFloat(taxStr, 0);
        float receipt = DigitUtils.parseFloat(mReceipt, 0);
        String delta = receipt >= tax ? taxStr : mReceipt;
        mBinding.tvReceiptValue.setText(String.format("发票存量可抵扣%s元", delta));
    }

    @Override
    protected void onDestroy() {
        mBinding.tvSendSmsCode.removeCallbacks(mSmsCodeCountRunnable);
        super.onDestroy();
    }

    private boolean isZhiFuBaoAccount() {
        return mType == CashService.ZHI_FU_BAO;
    }

    private boolean isBankAccount() {
        return mType == CashService.BANK;
    }
}
