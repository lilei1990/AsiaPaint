package com.asia.paint.biz.shop.flash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseListFragment;
import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.FlashGoods;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.biz.shop.detail.GoodsDetailActivity;
import com.asia.paint.biz.shop.detail.GoodsSpecDialog;
import com.asia.paint.biz.shop.index.ShopViewModel;
import com.asia.paint.utils.utils.DateUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author by chenhong14 on 2020-03-01.
 */
public class FlashGoodsFragment extends BaseListFragment {
	public static final String CATEGORY_FLASH_SALE = "秒杀";
	public static final String CATEGORY_GROUP = "火爆拼团";
	private static final String KEY_CATEGORY = "key_category";
	private ShopViewModel mGoodsViewModel;
	private ShopBannerRsp.CategoryBean mCategory;
	private List<FlashGoods> mDataList = null;

	public static FlashGoodsFragment create(ShopBannerRsp.CategoryBean category) {
		FlashGoodsFragment flashGoodsFragment = new FlashGoodsFragment();
		if (category != null) {
			Bundle bundle = new Bundle();
			bundle.putParcelable(KEY_CATEGORY, category);
			flashGoodsFragment.setArguments(bundle);
		}
		return flashGoodsFragment;
	}

	@Override
	protected void initView() {
		mCategory = getArguments().getParcelable(KEY_CATEGORY);
		super.initView();

		mAdapter.setOnItemClickListener((adapter, view, position) -> {
			if (mCategory != null) {
				FlashGoods goods = (FlashGoods) mAdapter.getData(position);
				if (mCategory.name.equals(CATEGORY_FLASH_SALE)) {
					GoodsDetailActivity.startSpike(getActivity(), goods.goods_id, goods.spike_id);
				} else if (mCategory.name.equals(CATEGORY_GROUP)) {
					GoodsDetailActivity.startGroup(getActivity(), goods.goods_id, goods.groupbuy_id);
				}
			}
		});
		mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			if (view.getId() == R.id.btn_add_cart) {
				if (mCategory != null) {
					FlashGoods goods = (FlashGoods) mAdapter.getData(position);
					if (mCategory.name.equals(CATEGORY_FLASH_SALE)) {//秒杀
						mGoodsViewModel.loadSpikeGoodsDetail(goods.goods_id, goods.spike_id).setCallback(result -> {
							ShopGoodsDetailRsp mShopGoodsDetailRsp = result;
							mGoodsViewModel.showGoodsSpecDialog(getContext(), OrderService.SPIKE, goods.spike_id, mShopGoodsDetailRsp._specs, mShopGoodsDetailRsp.goods_number, mShopGoodsDetailRsp.result, GoodsSpecDialog.Type.BUY);
						});
					} else if (mCategory.name.equals(CATEGORY_GROUP)) {//拼团
						mGoodsViewModel.loadGroupDetail(goods.goods_id, goods.groupbuy_id).setCallback(result -> {
							ShopGoodsDetailRsp mShopGoodsDetailRsp = result;
							mGoodsViewModel.showGoodsSpecDialog(getContext(), OrderService.GROUP, goods.groupbuy_id, mShopGoodsDetailRsp._specs, mShopGoodsDetailRsp.goods_number, mShopGoodsDetailRsp.result, GoodsSpecDialog.Type.BUY);
						});
					}
				}
			}
		});
	}

	@Override
	public BaseGlideAdapter getBaseAdapter() {
		return new FlashGoodsAdapter(mCategory.name);
	}

	@Override
	public BaseViewModel getViewModel() {
		mGoodsViewModel = getViewModel(ShopViewModel.class);
		return mGoodsViewModel;
	}

	@Override
	public void loadDate() {
		if (mCategory != null) {
			if (mCategory.name.equals(CATEGORY_FLASH_SALE)) {
				mGoodsViewModel.loadFlashSaleGoods().setCallback(result -> {
					mGoodsViewModel.updateListData(mAdapter, result);
					mDataList = (List<FlashGoods>) mAdapter.getData();
					mTimer = new Timer();
					mTimerTask = new MyTimerTask();
					mTimerTask.run();
					mTimer.schedule(mTimerTask, 0, 1000);
				});
			} else if (mCategory.name.equals(CATEGORY_GROUP)) {
				mGoodsViewModel.loadGroupByGoods().setCallback(result -> {
					mGoodsViewModel.updateListData(mAdapter, result);
					mDataList = (List<FlashGoods>) mAdapter.getData();
					mTimer = new Timer();
					mTimerTask = new MyTimerTask();
					mTimerTask.run();
					mTimer.schedule(mTimerTask, 0, 1000);
				});
			}
		}
	}

	private MyTimerTask mTimerTask;
	private Timer mTimer;

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			if (mDataList.isEmpty()) {
				return;
			}
			for (int i = 0; i < mDataList.size(); i++) {
				if (!TextUtils.isEmpty(mDataList.get(i).endtime)) {
					long diffent = DateUtils.getMillisecond(DateUtils.getCurrentDate(), mDataList.get(i).endtime);
					if (diffent > 0) {//当前时间小于截止时间
						setTimeShow(diffent, i);
					} else {
						mDataList.get(i).showHour = "00";
						mDataList.get(i).showMinute = "00";
						mDataList.get(i).showSecond = "00";
					}
				}
			}
			mHandler.sendEmptyMessage(1);
		}
	}

	private void setTimeShow(long useTime, int i) {
		useTime = useTime / 1000;
		int hour = (int) (useTime / 3600);
		int min = (int) (useTime / 60 % 60);
		int second = (int) (useTime % 60);
		int day = (int) (useTime / 3600 / 24);
		String mDay, mHour, mMin, mSecond;//天，小时，分钟，秒
		second--;
		if (second < 0) {
			min--;
			second = 59;
			if (min < 0) {
				min = 59;
				hour--;
			}
		}
		if (hour < 10) {
			mHour = "0" + hour;
		} else {
			mHour = "" + hour;
		}
		if (min < 10) {
			mMin = "0" + min;
		} else {
			mMin = "" + min;
		}
		if (second < 10) {
			mSecond = "0" + second;
		} else {
			mSecond = "" + second;
		}
		mDataList.get(i).showHour = mHour;
		mDataList.get(i).showMinute = mMin;
		mDataList.get(i).showSecond = mSecond;
	}

	private Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					mAdapter.notifyDataSetChanged();
					break;
			}
		}
	};

	@Override
	public void onDestroy() {
		mHandler.removeMessages(1);
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
		}
		super.onDestroy();
	}
}
