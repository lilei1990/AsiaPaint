package com.asia.paint.biz.pay;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.PayService;
import com.asia.paint.base.network.api.UserService;
import com.asia.paint.base.network.bean.MineDataRsp;
import com.asia.paint.base.network.bean.PayOrderInfo;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-28.
 */
public class PayViewModel extends BaseViewModel {

    private CallbackDate<Boolean> mPayResult = new CallbackDate<>();
    private CallbackDate<Boolean> mResetPayPwdResult = new CallbackDate<>();
    private CallbackDate<Boolean> mEditPayPwdResult = new CallbackDate<>();
    private CallbackDate<MineDataRsp> mMineDataResult = new CallbackDate<>();
    private CallbackDate<PayOrderInfo> mPayOrderInfoRsp = new CallbackDate<>();
    private CallbackDate<String> mZhiFubaoOrderInfoRsp = new CallbackDate<>();

    public CallbackDate<Boolean> payViaBalance(int order_id, String payword) {
        NetworkFactory.createService(PayService.class)
                .payViaBalance(order_id, payword)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(true) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mPayResult.setData(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mPayResult.setData(false);
                    }
                });
        return mPayResult;
    }

    public CallbackDate<MineDataRsp> loadMineData() {
        NetworkFactory.createService(UserService.class)
                .loadMineData()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MineDataRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MineDataRsp response) {
                        mMineDataResult.setData(response);
                    }

                });
        return mMineDataResult;
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


    public CallbackDate<Boolean> editPayPwd(String captcha, String payword) {
        NetworkFactory.createService(PayService.class)
                .editPayPwd(captcha, payword)
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
                });
        return mResetPayPwdResult;
    }

    public CallbackDate<PayOrderInfo> queryPayOrderInfo(int orderId, int type) {
        NetworkFactory.createService(PayService.class)
                .queryPayOrderInfo(orderId, type)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<PayOrderInfo>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(PayOrderInfo response) {
                        mPayOrderInfoRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mPayOrderInfoRsp.setData(null);
                        AppUtils.showMessage(e.getMessage());
                    }
                });
        return mPayOrderInfoRsp;
    }

    public CallbackDate<String> queryZhiFuBaoOrderInfo(int orderId) {
        int type = PayService.PAY_ZHI_FU_BAO;
        NetworkFactory.createService(PayService.class)
                .queryZhiFuBaoOrderInfo(orderId, type)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mZhiFubaoOrderInfoRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        AppUtils.showMessage(e.getMessage());
                    }
                });
        return mZhiFubaoOrderInfoRsp;
    }
}
