package com.asia.paint.biz.update;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.LoginService;
import com.asia.paint.base.network.api.UpdateService;
import com.asia.paint.base.network.api.ZoneService;
import com.asia.paint.base.network.bean.DecorationRsp;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.UpdateRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2020/6/25.
 */

public class UpdateViewModle extends BaseViewModel{
    private CallbackDate<UpdateRsp> updateCallback = new CallbackDate<>();

    public CallbackDate<UpdateRsp> update() {
        NetworkFactory.createService(UpdateService.class)
                .UpdateVersion()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<UpdateRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(UpdateRsp response) {
                        updateCallback.setData(response);
                    }
                });
        return updateCallback;
    }
}
