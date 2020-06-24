package com.asia.paint.base.model;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.CollectService;
import com.asia.paint.base.network.bean.FavoritesRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-21.
 */
public class CollectViewModel extends BaseViewModel {

    private CallbackDate<Boolean> mAddCollectResult = new CallbackDate<>();
    private CallbackDate<Boolean> mDelCollectResult = new CallbackDate<>();
    private CallbackDate<FavoritesRsp> mFavoritesResult = new CallbackDate<>();

    public CallbackDate<Boolean> addCollect(int goods_id) {
        NetworkFactory.createService(CollectService.class)
                .addCollect(goods_id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddCollectResult.setData(true);
                    }

                });
        return mAddCollectResult;
    }

    public CallbackDate<FavoritesRsp> queryCollect() {
        NetworkFactory.createService(CollectService.class)
                .queryCollect(getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<FavoritesRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(FavoritesRsp response) {
                        mFavoritesResult.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFavoritesResult.setData(null);
                    }
                });
        return mFavoritesResult;
    }

    public CallbackDate<Boolean> delCollect(int goods_id) {
        NetworkFactory.createService(CollectService.class)
                .delCollect(goods_id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelCollectResult.setData(true);
                    }

                });
        return mDelCollectResult;
    }

    public CallbackDate<Boolean> delCollect(String goods_id) {
        NetworkFactory.createService(CollectService.class)
                .delCollect(goods_id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelCollectResult.setData(true);
                    }

                });
        return mDelCollectResult;
    }
}
