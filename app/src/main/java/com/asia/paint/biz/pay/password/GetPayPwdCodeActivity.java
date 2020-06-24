package com.asia.paint.biz.pay.password;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.pay.pay.GetPayPwdCodeViewModel;
import com.asia.paint.databinding.ActivityGetPayPwdCodeBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.smarttop.library.utils.LogUtil;

/**
 * @author tangkun
 */
public class GetPayPwdCodeActivity extends BaseActivity<ActivityGetPayPwdCodeBinding> {

	public static final String ACTION_SET_PAY_PWD = "ACTION_SET_PAY_PWD";
	public static final String REQUEST_CODE_NEW_PASSWORD = "REQUEST_CODE_NEW_PASSWORD";
	private String newPassword = null;
	private GetPayPwdCodeViewModel mGetPayPwdCodeViewModel;


	public static void start(Activity activity, String newPassword) {
		Intent intent = new Intent(activity, GetPayPwdCodeActivity.class);
		intent.putExtra(REQUEST_CODE_NEW_PASSWORD, newPassword);
		activity.startActivity(intent);
	}


	@Override
	protected int getLayoutId() {
		return R.layout.activity_get_pay_pwd_code;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "手机验证码验证";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newPassword = getIntent().getStringExtra(REQUEST_CODE_NEW_PASSWORD);
		String mobilePwd = AsiaPaintApplication.getUserInfo().mobile.substring(0, 3) + "****" + AsiaPaintApplication.getUserInfo().mobile.substring(7, 11);
		mBinding.tvTelTips.setText("已发送验证码至手机" + mobilePwd + "，请输入");
		mGetPayPwdCodeViewModel = getViewModel(GetPayPwdCodeViewModel.class);
		mGetPayPwdCodeViewModel.requestSmsCode(AsiaPaintApplication.getUserInfo().mobile);
		mBinding.tvSure.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				//TODO 修改支付密码
				LogUtil.e("code", mBinding.viewCode.getPassword());
				LogUtil.e("newPassword", newPassword);
				mGetPayPwdCodeViewModel.resetPayPwd(mBinding.viewCode.getPassword(), newPassword).setCallback(result -> {
					if (result) {
						finish();
						sendResult();
					}
				});
			}
		});
		mBinding.viewCode.setOnPasswordChangedListener(() -> {
			if (mBinding.viewCode.isValidPassword()) {
				mBinding.tvSure.setBackgroundResource(R.drawable.bg_common_gradient);
				mBinding.tvSure.setVisibility(View.VISIBLE);
				mBinding.tvSure.setEnabled(true);
			}
		});
	}

	private void sendResult() {
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(GetPayPwdCodeActivity.this);
		Intent intent = new Intent(ACTION_SET_PAY_PWD);
		localBroadcastManager.sendBroadcast(intent);
	}
}
