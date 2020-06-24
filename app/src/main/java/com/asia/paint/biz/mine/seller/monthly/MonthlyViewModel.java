package com.asia.paint.biz.mine.seller.monthly;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.MonthlyService;
import com.asia.paint.base.network.bean.MonthlyDetail;
import com.asia.paint.base.network.bean.MonthlyRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-12.
 */
public class MonthlyViewModel extends BaseViewModel {

    public CallbackDate<MonthlyRsp> mMonthlyRsp = new CallbackDate<>();
    public CallbackDate<MonthlyDetail> mMonthlyDetailRsp = new CallbackDate<>();


    public CallbackDate<MonthlyRsp> loadMonthly(String keyword) {
        NetworkFactory.createService(MonthlyService.class)
                .loadMonthly(keyword, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MonthlyRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MonthlyRsp response) {
                        mMonthlyRsp.setData(response);
                    }
                });
        return mMonthlyRsp;
    }


    public CallbackDate<MonthlyDetail> queryTrainDetail(int id) {
        NetworkFactory.createService(MonthlyService.class)
                .queryMonthlyDetail(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MonthlyDetail>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MonthlyDetail response) {
                        mMonthlyDetailRsp.setData(response);
                    }
                });
        return mMonthlyDetailRsp;
    }
}
