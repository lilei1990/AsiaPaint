package com.asia.paint.biz.mine.settings.password;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityNewPasswordBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.biz.login.SmsCode;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-08.
 */
public class NewPwdActivity extends BaseActivity<ActivityNewPasswordBinding> {

    private static final int MAX_COUNT = 100;
    private static final int MIN_COUNT = 0;
    private int count = MAX_COUNT;
    private SmsCode mCurSmsCodeStatus;
    private Runnable mSmsCodeCountRunnable = new Runnable() {
        @Override
        public void run() {

            if (count == MIN_COUNT) {
                count = MAX_COUNT;
                setSmsCodeStatus(isValidPwd() ? SmsCode.ENABLE : SmsCode.DISABLE);
            } else {
                setSmsCodeStatus(SmsCode.COUNT);
                count--;
            }
        }
    };

    private NewPwdViewModel mNewPwdViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_password;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "设置登录新密码";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewPwdViewModel = getViewModel(NewPwdViewModel.class);
        mBinding.etNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSmsCodeStatus(isValidPwd() ? SmsCode.ENABLE : SmsCode.DISABLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setSmsCodeStatus(SmsCode.DISABLE);
        mBinding.etSmsCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mBinding.tvSendSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                count = MAX_COUNT;
                setSmsCodeStatus(SmsCode.COUNT);
                mNewPwdViewModel.requestSmsCode();
            }
        });
        mBinding.btnDone.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String newPwd = mBinding.etNewPwd.getText().toString().trim();
                if (TextUtils.isEmpty(newPwd)) {
                    AppUtils.showMessage("请输入密码");
                    return;
                }
                String smsCode = mBinding.etSmsCode.getText().toString().trim();
                if (TextUtils.isEmpty(smsCode)) {
                    AppUtils.showMessage("请输入验证码");
                    return;
                }
                mNewPwdViewModel.resetPwd(newPwd, smsCode);
            }
        });
    }

    private boolean isValidPwd() {
        return mBinding.etNewPwd.getText().length() >= 6;
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
