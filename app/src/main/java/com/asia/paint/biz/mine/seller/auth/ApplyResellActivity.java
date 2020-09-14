package com.asia.paint.biz.mine.seller.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityApplyResellBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.biz.mine.seller.goals.WebActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.chinapay.mobilepayment.activity.WebViewActivity;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

/**
 * @author by chenhong14 on 2019-11-14.
 */
public class ApplyResellActivity extends BaseActivity<ActivityApplyResellBinding> {

	private boolean isChecked = false;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_apply_resell;
	}


	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "申请分销合伙人";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding.ivRule.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				isChecked = !isChecked;
				if (isChecked) {
					mBinding.ivRule.setImageResource(R.mipmap.ic_checkbox_selected);
				} else {
					mBinding.ivRule.setImageResource(R.mipmap.ic_checkbox_normal);
				}
			}
		});
		mBinding.btnApply.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				if (!isChecked) {
					ToastUtils.showShortToast(ApplyResellActivity.this, "请先同意条款");
					return;
				}
				AuthRealNameActivity.start(ApplyResellActivity.this);
			}
		});
		mBinding.tvSellRule.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				WebActivity.start(ApplyResellActivity.this, OtherService.SELL_RULE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == AuthRealNameActivity.REQUEST_CODE_APPLY && resultCode == RESULT_OK) {
			finish();
		}
	}
}
