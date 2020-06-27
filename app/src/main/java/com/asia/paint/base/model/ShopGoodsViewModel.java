package com.asia.paint.base.model;

import android.content.Context;
import android.util.Log;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ShopService;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.network.bean.PromotionGroupPintuan;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.base.network.core.DefaultNetworkObserverList;
import com.asia.paint.biz.shop.detail.GoodsSpecDialog;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.CallbackDateList;
import com.asia.paint.utils.callback.PairCallbackDate;
import com.asia.paint.utils.utils.AppUtils;
import com.smarttop.library.utils.LogUtil;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-21.
 */
public class ShopGoodsViewModel extends BaseViewModel {

	private AddCartViewModel mAddCartViewModel = new AddCartViewModel();

	private CallbackDate<ShopGoodsDetailRsp> mGoodsDetailRsp = new CallbackDate<>();
	private CallbackDate<PromotionComposeRsp> mPromotionComposeRsp = new CallbackDate<>();
	private CallbackDateList<PromotionGroupPintuan> mPromotionGroupPintuanRsp = new CallbackDateList<>();


	public CallbackDate<ShopGoodsDetailRsp> loadSpikeGoodsDetail(int goods_id, int spike_id) {
		NetworkFactory.createService(ShopService.class)
				.loadSpikeGoodsDetail(goods_id, spike_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ShopGoodsDetailRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(ShopGoodsDetailRsp response) {
						mGoodsDetailRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mGoodsDetailRsp;
	}

	public CallbackDate<ShopGoodsDetailRsp> loadShopGoodsDetail(int goods_id) {
		NetworkFactory.createService(ShopService.class)
				.loadShopGoodsDetail(goods_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ShopGoodsDetailRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(ShopGoodsDetailRsp response) {
						mGoodsDetailRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mGoodsDetailRsp;
	}

	public CallbackDateList<PromotionGroupPintuan> loadPromotionGroupPintuan(int groupbuy_id) {
		NetworkFactory.createService(ShopService.class)
				.loadPromotionGroupPintuan(groupbuy_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserverList<PromotionGroupPintuan>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(List<PromotionGroupPintuan> response) {
						mPromotionGroupPintuanRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						LogUtil.e("拼团：", e.getMessage());
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mPromotionGroupPintuanRsp;
	}

	public CallbackDate<ShopGoodsDetailRsp> loadPromotionGroupDetail(Integer goods_id, int groupbuy_id) {
		NetworkFactory.createService(ShopService.class)
				.loadGroupDetail(goods_id, groupbuy_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ShopGoodsDetailRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(ShopGoodsDetailRsp response) {
						mGoodsDetailRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mGoodsDetailRsp;
	}

	public CallbackDate<PromotionComposeRsp> loadPromotionCompose(int goods_id) {
		NetworkFactory.createService(ShopService.class)
				.loadPromotionCompose(goods_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<PromotionComposeRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(PromotionComposeRsp response) {
						mPromotionComposeRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mPromotionComposeRsp;
	}

	public PairCallbackDate<Specs, Integer> showGoodsSpecDialog(Context context, Specs specs, int count, GoodsSpecDialog.Type type) {
		ShopGoodsDetailRsp data = mGoodsDetailRsp.getData();
		if (data == null) {
			return new PairCallbackDate<>();
		}
		return mAddCartViewModel.showGoodsSpecDialog(context, 0, 0, specs, count, data.result, type);
	}


	@Override
	protected void onCleared() {
		super.onCleared();
		mAddCartViewModel.onClear();
	}
}
