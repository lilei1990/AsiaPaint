package com.asia.paint.biz.pay.password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.biz.pay.PayViewModel;
import com.asia.paint.databinding.ActivitySetPwdBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-12-02.
 */
public class SetPayPwdActivity extends BaseActivity<ActivitySetPwdBinding> {

	public static final int REQUEST_CODE_SET_PAY_PWD = 0xFD11;
	public static final String ACTION_SET_PAY_PWD = "ACTION_SET_PAY_PWD";
	private PayViewModel mPayViewModel;


	public static void start(Activity activity) {
		Intent intent = new Intent(activity, SetPayPwdActivity.class);
		activity.startActivityForResult(intent, REQUEST_CODE_SET_PAY_PWD);
	}


	@Override
	protected int getLayoutId() {
		return R.layout.activity_set_pwd;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "设置支付密码";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPayViewModel = getViewModel(PayViewModel.class);
		mBinding.tvSure.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				//TODO 无需再做两次密码判断，直接使用第一次输入的密码
//                if (checkPwd()) {
//                    //TODO 修改支付密码
//                    mPayViewModel.resetPayPwd("", mBinding.viewSurePwd.getPassword()).setCallback(result -> {
//                        if (result) {
//                            finish();
//                            sendResult();
//                        } else {
//                            reset();
//                        }
//                    });
//                } else {
//                    AppUtils.showMessage("两次密码不一致，请重新设置");
//                    reset();
//                }

				//跳转获取验证码页面
				GetPayPwdCodeActivity.start(SetPayPwdActivity.this, mBinding.viewPwd.getPassword());
				finish();
			}
		});
		mBinding.viewPwd.setOnPasswordChangedListener(() -> {
			if (mBinding.viewPwd.isValidPassword()) {
//                mBinding.viewPwd.setVisibility(View.GONE);
//                mBinding.viewSurePwd.setVisibility(View.VISIBLE);
//                mBinding.viewSurePwd.requestFocus();
//                mBinding.tvSure.setVisibility(View.VISIBLE);
//                mBinding.tvSure.setEnabled(false);

				//TODO 不用输入两次密码，第一次输入密码即执行操作
				mBinding.tvSure.setVisibility(View.VISIBLE);
				mBinding.tvSure.setEnabled(true);
			}
		});
		reset();
		mBinding.viewSurePwd.setOnPasswordChangedListener(() ->
				mBinding.tvSure.setEnabled(mBinding.viewSurePwd.isValidPassword()));
	}

	private void sendResult() {
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(SetPayPwdActivity.this);
		Intent intent = new Intent(ACTION_SET_PAY_PWD);
		localBroadcastManager.sendBroadcast(intent);
	}

	private void reset() {
		mBinding.viewPwd.setText("");
		mBinding.viewPwd.setVisibility(View.VISIBLE);
		mBinding.viewPwd.requestFocus();
		mBinding.viewSurePwd.setText("");
		mBinding.viewSurePwd.setVisibility(View.GONE);
		mBinding.tvSure.setVisibility(View.GONE);
		mBinding.tvSure.setEnabled(false);
	}

	private boolean checkPwd() {
		return TextUtils.equals(mBinding.viewPwd.getPassword(), mBinding.viewSurePwd.getPassword());
	}
}
