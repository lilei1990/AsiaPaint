package com.asia.paint.biz.main;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.CartService;
import com.asia.paint.base.network.api.ShopService;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-20.
 */
public class MainViewModel extends BaseViewModel {
	private CallbackDate<Boolean> mAddCartSuccess = new CallbackDate<>();
	private CallbackDate<Boolean> mDelCartSuccess = new CallbackDate<>();
	private CallbackDate<Boolean> mDelAllCartSuccess = new CallbackDate<>();
	private CallbackDate<Boolean> mUpdateCartSuccess = new CallbackDate<>();
	private CallbackDate<ShopGoodsDetailRsp> mGoodsDetailRsp = new CallbackDate<>();


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

	public CallbackDate<Boolean> addCart(int spec_id, int count) {
		NetworkFactory.createService(CartService.class)
				.addCart(spec_id, count)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mAddCartSuccess.setData(true);
						AsiaPaintApplication.queryCartCount();
					}

				});
		return mAddCartSuccess;
	}

	/**
	 * 删除某种商品（所有数量）
	 */
	public CallbackDate<Boolean> deleteCart(String rec_id) {
		NetworkFactory.createService(CartService.class)
				.deleteCart(rec_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelCartSuccess.setData(true);
					}

				});
		return mDelCartSuccess;
	}

	/**
	 * 清空购物车
	 */
	public CallbackDate<Boolean> deleteAllCart() {
		NetworkFactory.createService(CartService.class)
				.deleteAllCart()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelAllCartSuccess.setData(true);
					}

				});
		return mDelAllCartSuccess;
	}

}
