package com.asia.paint.biz.mine.settings.unsubscribe;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.widgets.MessageDialog;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.login.LoginViewModel;
import com.asia.paint.biz.login.SmsCode;
import com.asia.paint.databinding.ActivityForgetPasswordBinding;
import com.asia.paint.databinding.ActivityUnsubscribeAccountBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-03.
 */
public class UnsubscribeAccountActivity extends BaseActivity<ActivityUnsubscribeAccountBinding> {
    private static final int MAX_COUNT = 100;
    private static final int MIN_COUNT = 0;
    private int count = MAX_COUNT;
    private SmsCode mCurSmsCodeStatus;
    private Runnable mSmsCodeCountRunnable = new Runnable() {
        @Override
        public void run() {

            if (count == MIN_COUNT) {
                setSmsCodeStatus(mBinding.etPhone.canNext() ? SmsCode.ENABLE : SmsCode.DISABLE);
                count = MAX_COUNT;
            } else {
                setSmsCodeStatus(SmsCode.COUNT);
                count--;
            }
        }
    };

    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(LoginViewModel.class);
        mBinding.etPhone.mBinding.etPhone.setHint("输入手机号");
        mBinding.etPhone.setOnChangeCallback(canNext -> {
            if (mCurSmsCodeStatus != SmsCode.COUNT) {
                setSmsCodeStatus(canNext ? SmsCode.ENABLE : SmsCode.DISABLE);
            }
            updateNextButton();
        });
        setSmsCodeStatus(SmsCode.DISABLE);
        mBinding.etSmsCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mBinding.tvSendSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                count = MAX_COUNT;
                setSmsCodeStatus(SmsCode.COUNT);
                mViewModel.requestSmsCode(getPhone(),"deluser");
            }
        });
        mBinding.etSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateNextButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.tvNoSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MessageDialog dialog = new MessageDialog.Builder()
                        .title("收不到验证码？")
                        .message("① 请检查是否输入正确的手机号\n② 请检查手机是否停机\n③ 请使用其他账号登录\n④ 请联系官方客服")
                        .setSureButton("我知道了", null)
                        .build();
                dialog.show(UnsubscribeAccountActivity.this);
            }
        });

        mBinding.btnNext.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String phone = getPhone();
                String smsCode = getSmsCode();
                if (TextUtils.isEmpty(phone)) {
                    AppUtils.showMessage("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    AppUtils.showMessage("请输入验证码");
                    return;
                }
                new MessageDialog.Builder()
                        .title("确认注销账户吗？")
                        .message("账户注销后，账户及其相关数据将被删除，此行为不可逆，请慎重考虑。")
                        .setCancelButton("取消", null)
                        .setSureButton("确认", new OnNoDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View view) {
                                mViewModel.unsubscribeAccount(phone, smsCode).setCallback(result -> {
                                    if (result) {
                                        AsiaPaintApplication.exitToLogin();
                                    }
                                });
                            }
                        }).build()
                        .show(UnsubscribeAccountActivity.this);
            }
        });
    }

    private String getPhone() {
        return mBinding.etPhone.mBinding.etPhone.getText().toString().trim();
    }

    private String getSmsCode() {
        return mBinding.etSmsCode.getText().toString().trim();
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

    private void updateNextButton() {
        String smsCode = mBinding.etSmsCode.getText().toString();
        boolean enable = mBinding.etPhone.canNext() && (!TextUtils.isEmpty(smsCode) && smsCode.length() >= 4 && smsCode.length() <= 6);
        mBinding.btnNext.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        mBinding.tvSendSmsCode.removeCallbacks(mSmsCodeCountRunnable);
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unsubscribe_account;
    }

    @Override
    protected String getMiddleTitle() {
        return "注销账户";
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }
}
