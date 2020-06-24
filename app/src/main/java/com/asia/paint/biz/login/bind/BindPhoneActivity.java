package com.asia.paint.biz.login.bind;

import static com.asia.paint.biz.login.LoginActivity.KEY_USER_ACCOUNT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.widgets.MessageDialog;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.login.LoginViewModel;
import com.asia.paint.biz.login.SmsCode;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.databinding.ActivityBindPhoneBinding;
import com.asia.paint.databinding.ActivityForgetPasswordBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.CacheUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * @author by chenhong14 on 2019-11-03.
 */
public class BindPhoneActivity extends BaseActivity<ActivityBindPhoneBinding> {
    private static final int MAX_COUNT = 100;
    private static final int MIN_COUNT = 0;
    private static final String KEY_OPEN_ID = "KEY_OPEN_ID";
    public static final int REQUEST_BIND_PHONE = 0x3333;
    private int count = MAX_COUNT;
    private SmsCode mCurSmsCodeStatus;
    private String mOpenId;
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
    private LoginViewModel mLoginViewModel;

    public static void start(Activity activity, String openId) {
        Intent intent = new Intent(activity, BindPhoneActivity.class);
        intent.putExtra(KEY_OPEN_ID, openId);
        activity.startActivityForResult(intent, REQUEST_BIND_PHONE);
    }

    @Override
    protected void intentValue(Intent intent) {
        mOpenId = intent.getStringExtra(KEY_OPEN_ID);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = getViewModel(LoginViewModel.class);
        mBinding.etPhone.mBinding.etPhone.setHint("输入手机号");
        mBinding.etPhone.setOnChangeCallback(new OnChangeCallback<Boolean>() {
            @Override
            public void onChange(Boolean canNext) {
                if (mCurSmsCodeStatus != SmsCode.COUNT) {
                    setSmsCodeStatus(canNext ? SmsCode.ENABLE : SmsCode.DISABLE);
                }
                updateNextButton();
            }
        });
        setSmsCodeStatus(SmsCode.DISABLE);
        mBinding.etSmsCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mBinding.tvSendSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                count = MAX_COUNT;
                setSmsCodeStatus(SmsCode.COUNT);
                mLoginViewModel.requestSmsCode(getPhone());
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
                dialog.show(BindPhoneActivity.this);
            }
        });

        mBinding.btnNext.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String smsCode = getText(mBinding.etSmsCode);
                mLoginViewModel.loginViaPhone(getPhone(), smsCode, "", mOpenId);
            }
        });

        mLoginViewModel.mLoginRsp.observe(this, new Observer<LoginRsp>() {
            @Override
            public void onChanged(LoginRsp loginRsp) {
                if (loginRsp != null) {
                    loginSuccess(loginRsp.userinfo);
                }
            }
        });
    }

    private void loginSuccess(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        CacheUtils.putTk(userInfo.token);
        AsiaPaintApplication.saveUserInfo(userInfo);
        CacheUtils.put(KEY_USER_ACCOUNT, userInfo.mobile);
        startActivity(new Intent(this, MainActivity.class));
        setResult(RESULT_OK);
        finish();
    }

    private String getPhone() {
        return mBinding.etPhone.mBinding.etPhone.getText().toString().trim();
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
        return R.layout.activity_bind_phone;
    }

    @Override
    protected String getMiddleTitle() {
        return "绑定手机号码";
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }
}
