package com.asia.paint.biz.order.mine;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityMyPintuanBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OrderService;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的拼团
 *
 * @author tangun
 */
public class MyPinTuanActivity extends BaseActivity<ActivityMyPintuanBinding> {
	private static final String KEY_PINTUAN_TYPE_FRAGMENT = "KEY_PINTUAN_TYPE_FRAGMENT";
	public static final Map<Integer, String> PINTUAN_TYPE = new LinkedHashMap<>();

	static {
		PINTUAN_TYPE.put(OrderService.PINTUAN_ALL, "全部");
		PINTUAN_TYPE.put(OrderService.PINTUAN_UNDERWAY, "进行中");
		PINTUAN_TYPE.put(OrderService.PINTUAN_SUCCESS, "成功");
		PINTUAN_TYPE.put(OrderService.PINTUAN_FAILED, "失败");
	}

	private int mType;

	public static void start(Context context, int type) {
		Intent intent = new Intent(context, MyPinTuanActivity.class);
		intent.putExtra(KEY_PINTUAN_TYPE_FRAGMENT, type);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mType = intent.getIntExtra(KEY_PINTUAN_TYPE_FRAGMENT, OrderService.PINTUAN_ALL);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_my_pintuan;
	}

	@Override
	protected boolean showTitleBar() {
		return true;
	}

	@Override
	protected String getMiddleTitle() {
		return "我的拼团";
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding.viewPager.setAdapter(new OrderPagerAdapter(getSupportFragmentManager()));
		mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
		setCurrentPage();
	}

	private void setCurrentPage() {

		try {
			Integer[] indexPage = PINTUAN_TYPE.keySet().toArray(new Integer[]{});
			List<Integer> integers = Arrays.asList(indexPage);
			int index = integers.indexOf(mType);
			mBinding.viewPager.setCurrentItem(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class OrderPagerAdapter extends FragmentStatePagerAdapter {

		public OrderPagerAdapter(@NonNull FragmentManager fm) {
			super(fm);
		}

		@NonNull
		@Override
		public Fragment getItem(int position) {
			return PinTuanFragment.createInstance(PINTUAN_TYPE.keySet().toArray(new Integer[]{})[position]);
		}

		@Override
		public int getCount() {
			return PINTUAN_TYPE.size();
		}

		@Nullable
		@Override
		public CharSequence getPageTitle(int position) {
			return PINTUAN_TYPE.values().toArray(new String[]{})[position];
		}

		@NonNull
		@Override
		public Object instantiateItem(@NonNull ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}
	}
}

