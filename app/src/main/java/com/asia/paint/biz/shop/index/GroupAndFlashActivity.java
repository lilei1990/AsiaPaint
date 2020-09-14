package com.asia.paint.biz.shop.index;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityOrderMineBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.biz.shop.flash.FlashGoodsFragment;

/**
 * 专区拼团/秒杀专区团购
 *
 * @author tangkun
 */
public class GroupAndFlashActivity extends BaseActivity<ActivityOrderMineBinding> {

	private ShopBannerRsp.CategoryBean mCategory;
	private static final String KEY_CATEGORY = "key_category";

	public static void start(Context context, ShopBannerRsp.CategoryBean category) {
		Intent intent = new Intent(context, GroupAndFlashActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(KEY_CATEGORY, category);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mCategory = intent.getExtras().getParcelable(KEY_CATEGORY);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_group_and_flash;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		if (mCategory.name.equals("秒杀")) {
			return "秒杀专区";
		} else {
			return "拼团专区";
		}
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.id_content, FlashGoodsFragment.create(mCategory));
		transaction.commitAllowingStateLoss();
	}
}

