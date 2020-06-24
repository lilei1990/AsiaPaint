package com.asia.paint.biz.login.forget;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.LoginService;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class ForgetPwdViewModel extends BaseViewModel {



    public void requestSmsCode(String phone) {
        NetworkFactory.createService(LoginService.class)
                .getSmsCode(phone, "resetpwd")
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });
    }
}
