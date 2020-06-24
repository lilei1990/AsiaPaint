package com.asia.paint.biz.mine.settings.password;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.LoginService;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-08.
 */
public class NewPwdViewModel extends BaseViewModel {

    public void requestSmsCode() {
        UserInfo userInfo = AsiaPaintApplication.getUserInfo();
        NetworkFactory.createService(LoginService.class)
                .getSmsCode(userInfo.mobile, "resetpwd")
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

    public void resetPwd(String pwd, String smsCode) {
        UserInfo userInfo = AsiaPaintApplication.getUserInfo();
        NetworkFactory.createService(LoginService.class)
                .resetPwd(userInfo.mobile, pwd, smsCode)
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
