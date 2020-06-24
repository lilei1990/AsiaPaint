package com.asia.paint.biz.zone;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ZoneService;
import com.asia.paint.base.network.bean.ZoneRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-08.
 */
public class ZoneViewModel extends BaseViewModel {

    private CallbackDate<ZoneRsp> mZoneRsp = new CallbackDate<>();

    public CallbackDate<ZoneRsp> loadZoneInfo(int page) {
        NetworkFactory.createService(ZoneService.class)
                .loadZoneInfo(page)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<ZoneRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(ZoneRsp response) {
                        mZoneRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mZoneRsp.setData(null);
                    }
                });
        return mZoneRsp;
    }
}
