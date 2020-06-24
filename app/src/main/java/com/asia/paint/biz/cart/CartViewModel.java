package com.asia.paint.biz.cart;

import android.util.Log;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.CartService;
import com.asia.paint.base.network.bean.CartGoodsRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-19.
 */
public class CartViewModel extends BaseViewModel {

    private static final String TAG = "hongc";

    private CallbackDate<CartGoodsRsp> mCartGoodsRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mAddCartSuccess = new CallbackDate<>();
    private CallbackDate<Boolean> mDelCartSuccess = new CallbackDate<>();
    private CallbackDate<Boolean> mDelAllCartSuccess = new CallbackDate<>();
    private CallbackDate<Boolean> mUpdateCartSuccess = new CallbackDate<>();
    private CallbackDate<Boolean> mCheckAllSuccess = new CallbackDate<>();
    private CallbackDate<Boolean> mCheckSuccess = new CallbackDate<>();

    public void test() {
        addCart(796, 1);
        // loadCartGoods();
        // deleteCart(1);
        // updateCart(796, 1);

    }

    public CallbackDate<CartGoodsRsp> loadCartGoods() {
        Log.i(TAG, "loadCartGoods: ");
        NetworkFactory.createService(CartService.class)
                .loadCartGoods()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CartGoodsRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CartGoodsRsp response) {
                        mCartGoodsRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mCartGoodsRsp.setData(null);
                    }
                });
        return mCartGoodsRsp;
    }

    public CallbackDate<Boolean> addCart(int spec_id, int count) {
        Log.i(TAG, "addCart: ");
        NetworkFactory.createService(CartService.class)
                .addCart(spec_id, count)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddCartSuccess.setData(true);
                        AsiaPaintApplication.queryCartCount();
                    }

                });
        return mAddCartSuccess;
    }

    /**
     * 删除某种商品（所有数量）
     */
    public CallbackDate<Boolean> deleteCart(String rec_id) {
        NetworkFactory.createService(CartService.class)
                .deleteCart(rec_id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelCartSuccess.setData(true);
                    }

                });
        return mDelCartSuccess;
    }

    /**
     * 清空购物车
     */
    public CallbackDate<Boolean> deleteAllCart() {
        NetworkFactory.createService(CartService.class)
                .deleteAllCart()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mDelAllCartSuccess.setData(true);
                    }

                });
        return mDelAllCartSuccess;
    }

    public CallbackDate<Boolean> updateCart(int rec_id, int count) {
        NetworkFactory.createService(CartService.class)
                .updateCart(rec_id, count)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mUpdateCartSuccess.setData(true);
                        AsiaPaintApplication.queryCartCount();
                    }

                });
        return mUpdateCartSuccess;
    }

    public CallbackDate<Boolean> checkToCart(int rec_id, int status) {
        Log.i(TAG, "checkToCart: ");
        NetworkFactory.createService(CartService.class)
                .checkToCart(rec_id, status)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse: ");
                        mCheckSuccess.setData(true);

                    }

                });
        return mCheckSuccess;
    }

    public CallbackDate<Boolean> checkAllToCart(int status) {
        NetworkFactory.createService(CartService.class)
                .checkAllToCart(status)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mCheckAllSuccess.setData(true);
                    }

                });
        return mCheckAllSuccess;
    }

}
