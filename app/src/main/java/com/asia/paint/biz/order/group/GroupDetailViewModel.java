package com.asia.paint.biz.order.group;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.PinTuanDetail;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author tangkun
 */
public class GroupDetailViewModel extends BaseViewModel {

    private CallbackDate<PinTuanDetail> mFavoritesResult = new CallbackDate<>();

    public CallbackDate<PinTuanDetail> loadPintuanDetail(int log_id) {
        NetworkFactory.createService(OrderService.class)
                .loadPintuanDetail(log_id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<PinTuanDetail>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(PinTuanDetail response) {
                        mFavoritesResult.setData(response);
                    }

                });
        return mFavoritesResult;
    }
}
