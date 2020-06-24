package com.asia.paint.biz.order.mine;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.MyPinTuan;
import com.asia.paint.base.network.core.DefaultNetworkObserverList;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDateList;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author tangkun
 */
public class PinTuanViewModel extends BaseViewModel {

	private CallbackDateList<MyPinTuan> mMyPinTuanRsp = new CallbackDateList<>();

	public CallbackDateList<MyPinTuan> loadMyPintuan(int page, int status) {
		NetworkFactory.createService(OrderService.class)
				.loadMyPintuan(page, status)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserverList<MyPinTuan>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(List<MyPinTuan> response) {
						mMyPinTuanRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mMyPinTuanRsp.setData(null);
					}
				});
		return mMyPinTuanRsp;
	}
}
