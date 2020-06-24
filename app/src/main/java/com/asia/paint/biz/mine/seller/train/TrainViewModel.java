package com.asia.paint.biz.mine.seller.train;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.TrainService;
import com.asia.paint.base.network.bean.TrainCategoryRsp;
import com.asia.paint.base.network.bean.TrainDetail;
import com.asia.paint.base.network.bean.TrainRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-12.
 */
public class TrainViewModel extends BaseViewModel {

    public CallbackDate<TrainCategoryRsp> mTrainCateRsp = new CallbackDate<>();
    public CallbackDate<TrainRsp> mTrainRsp = new CallbackDate<>();
    public CallbackDate<TrainDetail> mTrainDetailRsp = new CallbackDate<>();


    public CallbackDate<TrainCategoryRsp> loadTrainCategory() {
        NetworkFactory.createService(TrainService.class)
                .loadTrainCategory()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<TrainCategoryRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TrainCategoryRsp response) {
                        mTrainCateRsp.setData(response);
                    }
                });
        return mTrainCateRsp;
    }


    public CallbackDate<TrainRsp> queryTrain(int cateId, String keyword) {
        NetworkFactory.createService(TrainService.class)
                .queryTrain(cateId, keyword, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<TrainRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TrainRsp response) {
                        mTrainRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mTrainRsp.setData(null);
                    }
                });
        return mTrainRsp;
    }

    public CallbackDate<TrainDetail> queryTrainDetail(int id) {
        NetworkFactory.createService(TrainService.class)
                .queryTrainDetail(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<TrainDetail>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(TrainDetail response) {
                        mTrainDetailRsp.setData(response);
                    }
                });
        return mTrainDetailRsp;
    }
}
