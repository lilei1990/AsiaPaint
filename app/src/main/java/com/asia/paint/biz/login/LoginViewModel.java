package com.asia.paint.biz.login;

import android.util.Log;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.LoginService;
import com.asia.paint.base.network.api.WeiXinService;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.network.bean.WeiXinInfo;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<LoginRsp> mLoginRsp = new MutableLiveData<>();
    public CallbackDate<WeiXinInfo> mWeiXinInfoRsp = new CallbackDate<>();
    public CallbackDate<UserInfo> mWeiXinLoginRsp = new CallbackDate<>();
    public CallbackDate<Boolean> mBindNewPhone = new CallbackDate<>();
    public CallbackDate<Boolean> mUnsubcribeAccount = new CallbackDate<>();

    public void requestSmsCode(String phone) {
        NetworkFactory.createService(LoginService.class)
                .getSmsCode(phone, "mobilelogin")
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

    public void requestSmsCode(String phone, String type) {
        NetworkFactory.createService(LoginService.class)
                .getSmsCode(phone, type)
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

    public void loginViaPhone(String phone, String smsCode, String code, String openid) {
        NetworkFactory.createService(LoginService.class)
                .loginViaPhone(phone, smsCode, code, openid)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<LoginRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(LoginRsp response) {
                        mLoginRsp.setValue(response);
                    }
                });
    }

    public void loginViaPwd(String phone, String pwd, String code) {
        NetworkFactory.createService(LoginService.class)
                .loginViaPwd(phone, pwd, code)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<LoginRsp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(LoginRsp response) {
                        mLoginRsp.setValue(response);
                    }
                });
    }

    public CallbackDate<UserInfo> loginViaWeiXin(String openid) {
        NetworkFactory.createService(LoginService.class)
                .loginViaWeiXin(openid)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<UserInfo>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(UserInfo response) {
                        mWeiXinLoginRsp.setData(response);
                    }
                });
        return mWeiXinLoginRsp;
    }

    public void bindWeiXin(String token, String openid) {
        NetworkFactory.createService(LoginService.class)
                .bindWeiXin(token, openid)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        // mLoginRsp.setValue(response);
                    }
                });
    }

    public CallbackDate<WeiXinInfo> queryWeiXinInfo(String code) {
        NetworkFactory.createService(WeiXinService.class)
                .queryWeiXinInfo(Constants.WEI_XIN_APP_ID, Constants.WEI_XIN_APP_SECRET, code, "authorization_code")
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new Observer<WeiXinInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(WeiXinInfo weiXinInfo) {
                        mWeiXinInfoRsp.setData(weiXinInfo);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mWeiXinInfoRsp;
    }

    public CallbackDate<WeiXinInfo> refreshWeiXinToken(String refreshToken) {
        NetworkFactory.createService(WeiXinService.class)
                .refreshWeiXinToken(Constants.WEI_XIN_APP_ID, refreshToken, "refresh_token")
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new Observer<WeiXinInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(WeiXinInfo weiXinInfo) {
                        mWeiXinInfoRsp.setData(weiXinInfo);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mWeiXinInfoRsp;
    }

    public CallbackDate<Boolean> bindNewPhone(String phone, String smsCode) {
        NetworkFactory.createService(LoginService.class)
                .bindNewPhone(phone, smsCode)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mBindNewPhone.setData(true);
                    }
                });
        return mBindNewPhone;
    }

    public CallbackDate<Boolean> unsubscribeAccount(String phone, String smsCode) {
        NetworkFactory.createService(LoginService.class)
                .unsubscribeAccount(phone, smsCode)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mUnsubcribeAccount.setData(true);
                    }
                });
        return mUnsubcribeAccount;
    }
}
