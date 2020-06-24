package com.asia.paint.biz.mine.money.recharge;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.MoneyService;
import com.asia.paint.base.network.bean.PayOrderInfo;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2020-01-02.
 */
public class RechargeViewModel extends BaseViewModel {

    private CallbackDate<PayOrderInfo> mWeiXinRsp = new CallbackDate<>();
    private CallbackDate<String> mZhiFuBaoRsp = new CallbackDate<>();

    public CallbackDate<PayOrderInfo> rechargeWeiXin(String money) {
        NetworkFactory.createService(MoneyService.class)
                .rechargeWeiXin(MoneyService.PAY_WEI_XIN, money)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<PayOrderInfo>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(PayOrderInfo response) {
                        mWeiXinRsp.setData(response);
                    }

                });
        return mWeiXinRsp;
    }

    public CallbackDate<String> rechargeByZhiFuBao(String money) {
        NetworkFactory.createService(MoneyService.class)
                .rechargeByZhiFuBao(MoneyService.PAY_ZHI_FU_BAO, money)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mZhiFuBaoRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        AppUtils.showMessage(e.getMessage());
                    }
                });
        return mZhiFuBaoRsp;
    }
}
