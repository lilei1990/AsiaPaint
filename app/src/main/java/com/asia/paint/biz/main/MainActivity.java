package com.asia.paint.biz.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityMainBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.model.CollectViewModel;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.util.ClipboardUtils;
import com.asia.paint.base.widgets.TabHost;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.cart.CartFragment;
import com.asia.paint.biz.decoration.DecorationFragment;
import com.asia.paint.biz.find.FindFragment;
import com.asia.paint.biz.mine.index.MineFragment;
import com.asia.paint.biz.mine.seller.store.ClipBoardDialog;
import com.asia.paint.biz.shop.index.ShopFragment;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CommonUtil;
import com.smarttop.library.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnChangeCallback<Integer> {

	public enum Tab {
		SHOP(0), FIND(1), ZONE(2), CART(3), MINE(4);

		public int getValue() {
			return value;
		}

		private int value;

		Tab(int value) {
			this.value = value;
		}
	}

	private static final String KEY_TAB_INDEX = "key_tab_index";
	private MainViewModel mViewModel;
	private CollectViewModel mCollectViewModel;
	private int mTabIndex = Tab.SHOP.value;
	public static boolean CHANGESTATUS=false;//是否修改了状态

	public static void start(Context context, int tabIndex) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(KEY_TAB_INDEX, tabIndex);
		context.startActivity(intent);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			mTabIndex = intent.getIntExtra(KEY_TAB_INDEX, Tab.SHOP.value);
			setHostTab(mTabIndex);
		}
	}

	@Override
	protected void intentValue(Intent intent) {
		super.intentValue(intent);
		mTabIndex = intent.getIntExtra(KEY_TAB_INDEX, Tab.SHOP.value);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//添加购物车监听
		AsiaPaintApplication.addCartCountCallback(this);
		mViewModel = getViewModel(MainViewModel.class);
		mCollectViewModel = getViewModel(CollectViewModel.class);
		mBinding.viewTabHost.setup(this, getHostTabs());
		setHostTab(mTabIndex);
	}

	@Override
	protected void onResume() {
		super.onResume();
		AsiaPaintApplication.queryCartCount();
		initClipBoard();
	}

	/**
	 * 处理剪贴板口令商品
	 */
	private void initClipBoard() {
		String clipboardStr = ClipboardUtils.getClipContent(MainActivity.this);
		if (!TextUtils.isEmpty(clipboardStr)) {
			String clipboardArray[] = clipboardStr.split("\\u0024");
			String goodId = null;
			try {
				LogUtil.e("clipboardArray[1]", clipboardArray[1]);
				goodId = clipboardArray[1].replaceAll("\\u005Basia-paints]", "");
			} catch (Exception e) {

			}
			//商品ID
			if (!TextUtils.isEmpty(goodId) && clipboardStr.contains("[asia-paints]")) {
				ClipboardUtils.clearClipboard(MainActivity.this);
				mViewModel.loadShopGoodsDetail((Integer.valueOf(goodId))).setCallback(result -> {
					Goods goods = result.result;
					ClipBoardDialog dialog = ClipBoardDialog.createInstance(goods);
					dialog.show(MainActivity.this);
				});
			}
		}
	}

	@Override
	public void onChange(Integer count) {
		String text = count > 0 ? String.valueOf(count) : null;
		mBinding.viewTabHost.setRedDot(text, Tab.CART.value);
	}


	public MainViewModel getMainViewModel() {
		return mViewModel;
	}

	public CollectViewModel getCollectViewModel() {
		return mCollectViewModel;
	}

	private List<TabHost.HostTab> getHostTabs() {
		List<TabHost.HostTab> hostTabs = new ArrayList<>();
		TabHost.HostTab shopTab = new TabHost.HostTab(R.string.shop, R.drawable.host_tab_shop_selector, ShopFragment.class, null);
		TabHost.HostTab findTab = new TabHost.HostTab(R.string.find, R.drawable.host_tab_find_selector, DecorationFragment.class, null);
		TabHost.HostTab zoneTab = new TabHost.HostTab(R.string.zone, R.drawable.host_tab_zone_selector, FindFragment.class, null);
		TabHost.HostTab cartTab = new TabHost.HostTab(R.string.cart, R.drawable.host_tab_cart_selector, CartFragment.class, null);
		TabHost.HostTab mineTab = new TabHost.HostTab(R.string.mine, R.drawable.host_tab_mine_selector, MineFragment.class, null);
		hostTabs.add(shopTab);
		hostTabs.add(findTab);
		hostTabs.add(zoneTab);
		hostTabs.add(cartTab);
		hostTabs.add(mineTab);
		return hostTabs;
	}

	public void setHostTab(Tab tab) {
		setHostTab(tab.value);
	}

	public void setHostTab(int index) {
		mTabIndex = index;
		mBinding.viewTabHost.setTab(mTabIndex);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void onBackPressed() {
		if (CommonUtil.isDoubleClick()) {
			super.onBackPressed();
		} else {
			AppUtils.showMessage("再按一次返回键关闭程序");
		}
	}
}
