package com.asia.paint.biz.order;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.CreateOrderRsp;
import com.asia.paint.base.network.bean.OrderCommentRsp;
import com.asia.paint.base.network.bean.OrderInfoRsp;
import com.asia.paint.base.network.bean.OrderMineRsp;
import com.asia.paint.base.network.bean.OrderReturnDetailRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class OrderViewModel extends BaseViewModel {

	private CallbackDate<OrderInfoRsp> mOrderInfo = new CallbackDate<>();
	private CallbackDate<CreateOrderRsp> mCreateOrder = new CallbackDate<>();
	private CallbackDate<OrderMineRsp> mOrderMine = new CallbackDate<>();
	private CallbackDate<Boolean> mCancelOrder = new CallbackDate<>();
	private CallbackDate<OrderReturnDetailRsp> mOrderDetail = new CallbackDate<>();
	private CallbackDate<Boolean> mDelOrder = new CallbackDate<>();
	private CallbackDate<Boolean> mReceiveOrder = new CallbackDate<>();
	private CallbackDate<OrderCommentRsp> mOrderCommentOrder = new CallbackDate<>();

	public CallbackDate<OrderInfoRsp> queryOrderInfo(int type, Integer addressId) {
		NetworkFactory.createService(OrderService.class)
				.queryOrderInfo(type, addressId)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<OrderInfoRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(OrderInfoRsp response) {
						mOrderInfo.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mOrderInfo;
	}

	public CallbackDate<CreateOrderRsp> createOrder(int flow_type, int address_id,
													int bonus_id, String description, int score, int receiptId) {
		NetworkFactory.createService(OrderService.class)
				.createOrder(flow_type, address_id, bonus_id, description, score, receiptId)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<CreateOrderRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(CreateOrderRsp response) {
						mCreateOrder.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mCreateOrder;
	}

	public CallbackDate<CreateOrderRsp> createOrder(int flow_type, int address_id,
													int bonus_id, String description, int score) {
		NetworkFactory.createService(OrderService.class)
				.createOrder(flow_type, address_id, bonus_id, description, score)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<CreateOrderRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(CreateOrderRsp response) {
						mCreateOrder.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						AppUtils.showMessage(e.getMessage());
					}
				});
		return mCreateOrder;
	}

	public CallbackDate<OrderMineRsp> loadMyOrder(int order_status, int page) {
		NetworkFactory.createService(OrderService.class)
				.loadMyOrder(order_status, page)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<OrderMineRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(OrderMineRsp response) {
						mOrderMine.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mOrderMine.setData(null);
					}
				});
		return mOrderMine;
	}

	public CallbackDate<OrderReturnDetailRsp> queryOrderDetail(int order_id) {
		NetworkFactory.createService(OrderService.class)
				.queryOrderDetail(order_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<OrderReturnDetailRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(OrderReturnDetailRsp response) {
						mOrderDetail.setData(response);
					}
				});
		return mOrderDetail;
	}

	public CallbackDate<Boolean> cancelOrder(int order_id) {
		NetworkFactory.createService(OrderService.class)
				.cancelOrder(order_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(true) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mCancelOrder.setData(true);
					}
				});
		return mCancelOrder;
	}

	public CallbackDate<Boolean> delOrder(int order_id) {
		NetworkFactory.createService(OrderService.class)
				.delOrder(order_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(true) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelOrder.setData(true);
					}
				});
		return mDelOrder;
	}

	public CallbackDate<Boolean> orderIsReceive(int order_id) {
		NetworkFactory.createService(OrderService.class)
				.orderIsReceive(order_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mReceiveOrder.setData(true);
					}
				});
		return mReceiveOrder;
	}


	public CallbackDate<OrderCommentRsp> commentOrder() {
		NetworkFactory.createService(OrderService.class)
				.commentOrder(getCurPage(), 0)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<OrderCommentRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(OrderCommentRsp response) {
						mOrderCommentOrder.setData(response);
					}
				});
		return mOrderCommentOrder;
	}

	public CallbackDate<Boolean> directBuy(int spec_id, int count) {
		NetworkFactory.createService(OrderService.class)
				.directBuy(spec_id, count)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelOrder.setData(true);
					}
				});
		return mDelOrder;
	}

	public CallbackDate<Boolean> promotionBuy(int flow_type, int id) {
		NetworkFactory.createService(OrderService.class)
				.promotionBuy(flow_type, id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelOrder.setData(true);
					}
				});
		return mDelOrder;
	}

	public CallbackDate<Boolean> promotionBuy(int flow_type, int id, int quantity) {
		NetworkFactory.createService(OrderService.class)
				.promotionBuy(flow_type, id, quantity)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelOrder.setData(true);
					}
				});
		return mDelOrder;
	}

	public CallbackDate<Boolean> promotionBuy(int flow_type, int id, int quantity, int spec_id) {
		NetworkFactory.createService(OrderService.class)
				.promotionBuy(flow_type, id, quantity, spec_id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mDelOrder.setData(true);
					}
				});
		return mDelOrder;
	}
}
