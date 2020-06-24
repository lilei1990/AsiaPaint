package com.asia.paint.biz.shop.index;

import android.content.Context;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.api.ShopService;
import com.asia.paint.base.network.bean.FlashGoodsRsp;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.IndexBaseRsp;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.biz.shop.detail.GoodsSpecDialog;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.PairCallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-17.
 */
public class ShopViewModel extends BaseViewModel {

	public CallbackDate<ShopBannerRsp> mShopBannerRsp = new CallbackDate<>();
	private CallbackDate<FlashGoodsRsp> mFlashGoodsRsp = new CallbackDate<>();
	private CallbackDate<PromotionComposeRsp> mPromotionComposeRsp = new CallbackDate<>();
	private PairCallbackDate<Specs, Integer> mSpecResult = new PairCallbackDate<>();
	private CallbackDate<ShopGoodsDetailRsp> mGoodsDetailRsp = new CallbackDate<>();
	private CallbackDate<IndexBaseRsp> mIndexBaseRsp = new CallbackDate<>();

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

	public CallbackDate<ShopGoodsDetailRsp> loadGroupDetail(Integer goods_id, int groupbuy_id) {
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
//                    addCart(spec.spec_id, count);
				}
				dialog.dismiss();
			}

			@Override
			public void onBuy(Specs spec, int count) {
				dialog.dismiss();
				if (spec != null && count > 0) {
					if (id == 0) {
						OrderCheckoutActivity.start(context, OrderService.BUY, spec.spec_id, count);
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

	public CallbackDate<PromotionComposeRsp> loadPromotionCompose() {
		NetworkFactory.createService(ShopService.class)
				.loadPromotionCompose()
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

	public CallbackDate<IndexBaseRsp> loadIndexBase() {
		NetworkFactory.createService(ShopService.class)
				.loadIndexBase()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<IndexBaseRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(IndexBaseRsp response) {
						mIndexBaseRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mIndexBaseRsp;
	}

	public CallbackDate<ShopBannerRsp> loadBanner() {
		NetworkFactory.createService(ShopService.class)
				.loadBanner()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<ShopBannerRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(ShopBannerRsp response) {
						mShopBannerRsp.setData(response);
					}
				});
		return mShopBannerRsp;
	}


	public CallbackDate<ShopBannerRsp> loadShopGoodsCategory() {
		NetworkFactory.createService(ShopService.class)
				.loadShopGoodsCategory(1)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<LoginRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(LoginRsp response) {
					}
				});
		return mShopBannerRsp;

	}

	public CallbackDate<ShopBannerRsp> loadShopGoodsExhibit() {
		NetworkFactory.createService(ShopService.class)
				.loadShopGoodsExhibit()
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<LoginRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(LoginRsp response) {
					}
				});
		return mShopBannerRsp;

	}

	public CallbackDate<FlashGoodsRsp> loadFlashSaleGoods() {
		NetworkFactory.createService(ShopService.class)
				.loadFlashSaleGoods(getCurPage())
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<FlashGoodsRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(FlashGoodsRsp response) {
						mFlashGoodsRsp.setData(response);
					}
				});
		return mFlashGoodsRsp;
	}

	public CallbackDate<FlashGoodsRsp> loadGroupByGoods() {
		NetworkFactory.createService(ShopService.class)
				.loadGroupByGoods(getCurPage())
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<FlashGoodsRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(FlashGoodsRsp response) {
						mFlashGoodsRsp.setData(response);
					}
				});
		return mFlashGoodsRsp;
	}
}
