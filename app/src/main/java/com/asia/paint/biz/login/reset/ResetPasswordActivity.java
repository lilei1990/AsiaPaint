package com.asia.paint.biz.login.reset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.databinding.ActivityResetPasswordBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-03.
 */
public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding> {

    private static final String KEY_PHONE = "KEY_PHONE";
    private static final String KEY_SMS_CODE = "KEY_SMS_CODE";
    private String mPhone;
    private String mSmsCode;
    private ResetPwdViewModel mViewModel;

    public static void start(Activity activity, String phone, String smsCode) {
        Intent intent = new Intent(activity, ResetPasswordActivity.class);
        intent.putExtra(KEY_PHONE, phone);
        intent.putExtra(KEY_SMS_CODE, smsCode);
        activity.startActivityForResult(intent, 9999);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(ResetPwdViewModel.class);
        Intent intent = getIntent();
        if (intent != null) {
            mPhone = intent.getStringExtra(KEY_PHONE);
            mSmsCode = intent.getStringExtra(KEY_SMS_CODE);
        }
        mBinding.tvPhone.setText(mPhone);
        mBinding.etPassword.setOnChangeCallback(canNext -> mBinding.btnSave.setEnabled(canNext));
        mBinding.btnSave.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String phone = mBinding.etPassword.mBinding.etPwd.getText().toString().trim();
                mViewModel.resetPwd(mPhone, phone, mSmsCode);
            }
        });
        mBaseBinding.ivBack.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                Boolean success = mViewModel.resetSuccess.getValue();
                if (success != null && success) {
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
        mViewModel.resetSuccess.observe(this, aBoolean -> {
            Boolean success = mViewModel.resetSuccess.getValue();
            if (success != null && success) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected String getMiddleTitle() {
        return "重新设置密码";
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }
}
