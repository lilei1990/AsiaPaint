package com.asia.paint.base.network.core;

import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.network.NetworkObserver;
import com.asia.paint.utils.utils.AppUtils;

import java.util.List;

import retrofit2.HttpException;

public abstract class DefaultNetworkObserverList<T> extends NetworkObserver<BaseListRespose<T>> {


    private boolean mShowMsg;

    public DefaultNetworkObserverList() {
        this(true);
    }

    public DefaultNetworkObserverList(boolean showMsg) {
        mShowMsg = showMsg;
    }

    @Override
    public void onNext(BaseListRespose<T> baseRsp) {
        onComplete(baseRsp);
        if (baseRsp == null) {
            onError(new Throwable("网络异常"));
            return;
        }
        int code = baseRsp.getCode();
        if (code == NetworkCode.NO_LOGIN.getCode()) {
            showMessage(baseRsp.getMsg());
            AsiaPaintApplication.exitToLogin();
        } else if (code == NetworkCode.SUCCESS.getCode()) {
            showMessage(baseRsp.getMsg());
            onResponse(baseRsp.getData());
        } else {
            onError(new Throwable(baseRsp.getMsg()));
        }

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException && ((HttpException) e).code() == NetworkCode.NO_LOGIN.getCode()) {
            AsiaPaintApplication.exitToLogin();
        }
        showMessage(e.getMessage());
    }

    public void onComplete(BaseListRespose<T> baseRsp) {

    }

    private void showMessage(String msg) {
        if (mShowMsg) {
            AppUtils.showMessage(msg);
        }
    }

    public abstract void onResponse(List<T> response);
}
