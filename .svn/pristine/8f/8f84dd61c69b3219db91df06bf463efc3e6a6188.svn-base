package com.asia.paint.biz.mine.seller.staff;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.StaffService;
import com.asia.paint.base.network.bean.StaffInfoRsp;
import com.asia.paint.base.network.bean.StaffRsp;
import com.asia.paint.base.network.bean.StaffSaleDataRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-12.
 */
public class StaffViewModel extends BaseViewModel {

    public CallbackDate<StaffRsp> MStaffRsp = new CallbackDate<>();
    public CallbackDate<StaffInfoRsp> mStaffInfoRsp = new CallbackDate<>();
    public CallbackDate<StaffSaleDataRsp> mSaleDataRsp = new CallbackDate<>();


    public CallbackDate<StaffRsp> loadStaff(int status) {
        NetworkFactory.createService(StaffService.class)
                .loadStaff(status)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<StaffRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(StaffRsp response) {
                        MStaffRsp.setData(response);
                    }
                });
        return MStaffRsp;
    }


    public CallbackDate<StaffInfoRsp> queryStaffDetail(int id) {
        NetworkFactory.createService(StaffService.class)
                .queryStaffDetail(getCurPage(), id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<StaffInfoRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(StaffInfoRsp response) {
                        mStaffInfoRsp.setData(response);
                    }
                });
        return mStaffInfoRsp;
    }


    public CallbackDate<StaffSaleDataRsp> queryStaffSaleData(Integer id,String startTime, String endTime) {
        NetworkFactory.createService(StaffService.class)
                .queryStaffSaleData(id,startTime, endTime)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<StaffSaleDataRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(StaffSaleDataRsp response) {
                        mSaleDataRsp.setData(response);
                    }
                });
        return mSaleDataRsp;
    }
}
