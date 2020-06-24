package com.asia.paint.biz.mine.coupon;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.CouponService;
import com.asia.paint.base.network.bean.CouponRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class CouponViewModel extends BaseViewModel {
    private CallbackDate<CouponRsp> mCouponRsp = new CallbackDate<>();
    private CallbackDate<CouponRsp> mAllCouponRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mGetCouponRsp = new CallbackDate<>();

    public CallbackDate<CouponRsp> queryCoupon(int type) {
        NetworkFactory.createService(CouponService.class)
                .queryCoupon(getCurPage(), type)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CouponRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CouponRsp response) {
                        mCouponRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mCouponRsp.setData(null);
                    }
                });
        return mCouponRsp;
    }

    public CallbackDate<CouponRsp> loadCoupon(Integer type) {
        NetworkFactory.createService(CouponService.class)
                .loadCoupon(getCurPage(), type)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<CouponRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(CouponRsp response) {
                        mAllCouponRsp.setData(response);
                    }
                });
        return mAllCouponRsp;
    }

    public CallbackDate<Boolean> getCoupon(Integer id) {
        NetworkFactory.createService(CouponService.class)
                .getCoupon(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mGetCouponRsp.setData(true);
                    }
                });
        return mGetCouponRsp;
    }

}
