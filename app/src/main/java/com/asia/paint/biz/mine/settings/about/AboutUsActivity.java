package com.asia.paint.biz.mine.settings.about;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.biz.mine.seller.goals.WebViewActivity;
import com.asia.paint.databinding.ActivityAboutUsBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_about_us;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "关于亚士漆";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding.itemAboutAsia.setTitle("亚士漆简介");
		mBinding.tvVersionCode.setText("V" + getAppVersionName(AboutUsActivity.this));
		mBinding.itemAboutAsia.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				WebViewActivity.start(AboutUsActivity.this, OtherService.ABOUT_ASIA);
			}
		});
		mBinding.itemCheckUpdate.setTitle("检测更新");
		mBinding.itemCheckUpdate.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				AppUtils.showMessage("已是最新版本");
			}
		});
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
}
