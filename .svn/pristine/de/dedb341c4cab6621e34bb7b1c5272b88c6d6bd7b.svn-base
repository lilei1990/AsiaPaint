package com.asia.paint.biz.mine.seller.score;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ScoreService;
import com.asia.paint.base.network.bean.ScoreRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-15.
 */
public class ScoreViewModel extends BaseViewModel {
    private CallbackDate<ScoreRsp> mScoreRsp = new CallbackDate<>();

    public CallbackDate<String> addTestScore() {
        NetworkFactory.createService(ScoreService.class)
                .addTestScore()
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        // mGoodsDetailRsp.setData(response);
                    }

                });
        return null;
    }

    public CallbackDate<ScoreRsp> queryScoreDetail() {
        NetworkFactory.createService(ScoreService.class)
                .queryScoreDetail(getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<ScoreRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(ScoreRsp response) {
                        mScoreRsp.setData(response);
                    }

                });
        return mScoreRsp;
    }
}
