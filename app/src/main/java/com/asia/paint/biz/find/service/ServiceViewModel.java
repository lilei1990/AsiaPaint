package com.asia.paint.biz.find.service;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ServiceService;
import com.asia.paint.base.network.bean.ScheduleServiceRsp;
import com.asia.paint.base.network.bean.ServiceRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class ServiceViewModel extends BaseViewModel {

    private CallbackDate<ServiceRsp> mServiceRsp = new CallbackDate<>();
    private CallbackDate<ScheduleServiceRsp> mScheduleServiceRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mScheduleRsp = new CallbackDate<>();

    public CallbackDate<ServiceRsp> loadService(double point_lng, double point_lat
            , String order, String keyword) {
        NetworkFactory.createService(ServiceService.class)
                .loadService(point_lng, point_lat, order, keyword, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<ServiceRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(ServiceRsp response) {
                        mServiceRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        AppUtils.showMessage(e.getMessage());
                        mServiceRsp.setData(null);
                    }
                });
        return mServiceRsp;
    }


    public CallbackDate<Boolean> scheduleService(int sid, String name
            , String tel, String service_time, String desc) {
        NetworkFactory.createService(ServiceService.class)
                .scheduleService(sid, name, tel, service_time, desc)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mScheduleRsp.setData(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mScheduleRsp.setData(false);
                        AppUtils.showMessage(e.getMessage());
                    }
                });
        return mScheduleRsp;
    }

    public CallbackDate<ScheduleServiceRsp> loadScheduleService(String status, int page) {
        NetworkFactory.createService(ServiceService.class)
                .loadScheduleService(status, page)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<ScheduleServiceRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(ScheduleServiceRsp response) {
                        mScheduleServiceRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mScheduleServiceRsp.setData(null);
                    }
                });
        return mScheduleServiceRsp;
    }

    public CallbackDate<Boolean> cancelScheduleService(int id) {
        NetworkFactory.createService(ServiceService.class)
                .cancelScheduleService(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mScheduleRsp.setData(true);
                    }

                });
        return mScheduleRsp;
    }
}
