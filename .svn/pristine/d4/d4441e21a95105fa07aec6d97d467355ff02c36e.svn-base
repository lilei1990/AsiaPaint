package com.asia.paint.biz.decoration;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.UserService;
import com.asia.paint.base.network.bean.IndexNewsDetailRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

public class DecorationDetailViewModel extends BaseViewModel {

	private CallbackDate<IndexNewsDetailRsp> mIndexNewsDetailRspRsp = new CallbackDate<>();

	public CallbackDate<IndexNewsDetailRsp> loadIndexNewsDetail(String id) {
		NetworkFactory.createService(UserService.class)
				.loadIndexNewsDetail(id)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<IndexNewsDetailRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(IndexNewsDetailRsp response) {
						mIndexNewsDetailRspRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mIndexNewsDetailRspRsp.setData(null);
					}
				});
		return mIndexNewsDetailRspRsp;
	}
}