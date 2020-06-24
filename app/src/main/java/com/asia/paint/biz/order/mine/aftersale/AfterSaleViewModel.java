package com.asia.paint.biz.order.mine.aftersale;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.AfterSaleService;
import com.asia.paint.base.network.bean.AfterSaleListRsp;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.network.bean.ReturnDetail;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.CommonUtil;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public class AfterSaleViewModel extends BaseViewModel {


    private CallbackDate<AfterSaleListRsp> mAfterSaleRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mApplyAfterSaleRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mAfterSaleOpRsp = new CallbackDate<>();
    private CallbackDate<ReturnDetail> mAfterSaleDetailRsp = new CallbackDate<>();

    public CallbackDate<AfterSaleListRsp> loadReturnList() {
        NetworkFactory.createService(AfterSaleService.class)
                .loadReturnList(getCurPage(), null)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<AfterSaleListRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(AfterSaleListRsp response) {
                        mAfterSaleRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAfterSaleRsp.setData(null);
                    }
                });
        return mAfterSaleRsp;
    }

    public CallbackDate<Boolean> applyAfterSale(int recId, int type, String reason
            , int num, String money, String desc, List<String> imageUrl) {
        String images = CommonUtil.list2str(imageUrl);
        NetworkFactory.createService(AfterSaleService.class)
                .applyAfterSale(recId, type, reason, num, money, desc, images)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mApplyAfterSaleRsp.setData(true);
                    }

                });
        return mApplyAfterSaleRsp;
    }

    public CallbackDate<ReturnDetail> queryReturnDetail(int rec_id) {
        NetworkFactory.createService(AfterSaleService.class)
                .queryReturnDetail(rec_id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<ReturnDetail>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(ReturnDetail response) {
                        mAfterSaleDetailRsp.setData(response);
                    }

                });
        return mAfterSaleDetailRsp;
    }

    public CallbackDate<Boolean> afterSaleOperation(int rec_id, int status, String express_company
            , String express_sn) {
        NetworkFactory.createService(AfterSaleService.class)
                .afterSaleOperation(rec_id, status, express_company, express_sn)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAfterSaleOpRsp.setData(true);
                    }

                });
        return mAfterSaleOpRsp;
    }

}
