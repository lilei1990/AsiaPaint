package com.asia.paint.biz.login.reset;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.LoginService;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class ResetPwdViewModel extends BaseViewModel {


    public MutableLiveData<Boolean> resetSuccess = new MutableLiveData<>(false);

    public void resetPwd(String username, String pwd, String smsCode) {
        NetworkFactory.createService(LoginService.class)
                .resetPwd(username, pwd, smsCode)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        resetSuccess.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        resetSuccess.setValue(false);
                    }
                });
    }
}
