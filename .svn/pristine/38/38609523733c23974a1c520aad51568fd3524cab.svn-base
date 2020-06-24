package com.asia.paint.biz.decoration;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ZoneService;
import com.asia.paint.base.network.bean.DecorationRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author tangkun
 */
public class DecorationViewModel extends BaseViewModel {

	private CallbackDate<DecorationRsp> mDecorationRsp = new CallbackDate<>();

	public CallbackDate<DecorationRsp> loadNewsInfo(int page) {
		NetworkFactory.createService(ZoneService.class)
				.loadNewsInfo(page)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<DecorationRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(DecorationRsp response) {
						mDecorationRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mDecorationRsp.setData(null);
					}
				});
		return mDecorationRsp;
	}
}
