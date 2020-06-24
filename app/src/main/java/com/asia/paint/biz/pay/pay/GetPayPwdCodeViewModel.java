package com.asia.paint.biz.pay.pay;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.LoginService;
import com.asia.paint.base.network.api.PayService;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-28.
 */
public class GetPayPwdCodeViewModel extends BaseViewModel {

    private CallbackDate<Boolean> mResetPayPwdResult = new CallbackDate<>();

    public void requestSmsCode(String phone) {
        NetworkFactory.createService(LoginService.class)
                .getSmsCode(phone, "payword")
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

    public CallbackDate<Boolean> resetPayPwd(String captcha, String payword) {
        NetworkFactory.createService(PayService.class)
                .resetPayPwd(captcha, payword)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mResetPayPwdResult.setData(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mResetPayPwdResult.setData(false);
                    }
                });
        return mResetPayPwdResult;
    }
}
