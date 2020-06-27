package com.asia.paint.base.model;

import android.content.Context;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.bean.CartCountRsp;
import com.asia.paint.base.network.api.CartService;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.biz.shop.detail.GoodsSpecDialog;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.PairCallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;


/**
 * @author by chenhong14 on 2019-11-20.
 */
public class AddCartViewModel extends BaseViewModel {

	private CallbackDate<Boolean> mAddCartSuccess = new CallbackDate<>();
	private CallbackDate<Boolean> mBuySuccess = new CallbackDate<>();
	private CallbackDate<CartCountRsp> mCartCountSuccess = new CallbackDate<>();
	private PairCallbackDate<Specs, Integer> mSpecResult = new PairCallbackDate<>();

	public PairCallbackDate<Specs, Integer> showGoodsSpecDialog(Context context, int mType, int id, Specs selectedSpec, int count, Goods goods, GoodsSpecDialog.Type type) {

		if (context == null || goods == null || goods._specs == null) {
			return mSpecResult;
		}
		String iconUrl = "";
		if (goods.default_image != null && !goods.default_image.isEmpty()) {
			iconUrl = goods.default_image.get(0);
		}
		GoodsSpecDialog dialog = new GoodsSpecDialog.Builder()
				.setType(type)
				.setIconUrl(iconUrl)
				.setSpec(selectedSpec)
				.setCount(count)
				.setSpecList(goods._specs)
				.build();
		dialog.setOnClickGoodsSpecListener(new GoodsSpecDialog.OnClickGoodsSpecListener() {
			@Override
			public void onAddCart(Specs spec, int count) {
				if (spec != null && count > 0) {
					addCart(spec.spec_id, count);
				}
				dialog.dismiss();
			}

			@Override
			public void onBuy(Specs spec, int count) {
				dialog.dismiss();
				if (spec != null && count > 0) {
					if (id == 0) {
						directBuy(spec.spec_id, count).setCallback(new OnChangeCallback<Boolean>() {
							@Override
							public void onChange(Boolean result) {
								OrderCheckoutActivity.start(context, OrderService.BUY, spec.spec_id, count);
							}
						});
					} else if (mType == OrderService.GROUP) {
						OrderCheckoutActivity.start(context, mType, spec.spec_id, count, id);
					} else if (mType == OrderService.SPIKE) {
						OrderCheckoutActivity.start(context, mType, spec.spec_id, count, id);
					}
				}
			}

			@Override
			public void onDismiss(Specs spec, int count) {
				mSpecResult.setData(spec, count);
			}
		});
		dialog.show(context);
		return mSpecResult;
	}

	public CallbackDate<Boolean> directBuy(int spec_id, int count) {
		NetworkFactory.createService(OrderService.class)
				.directBuy(spec_id, count)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mBuySuccess.setData(true);
					}

				});
		return mBuySuccess;
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

	public CallbackDate<CartCountRsp> queryCartCount() {
		NetworkFactory.createService(CartService.class)
				.queryCartCount()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<CartCountRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(CartCountRsp response) {
						mCartCountSuccess.setData(response);
					}

				});
		return mCartCountSuccess;
	}

}
