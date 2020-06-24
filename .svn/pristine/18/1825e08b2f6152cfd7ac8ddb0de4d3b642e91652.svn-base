package com.asia.paint.base.widgets.show;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.ShopService;
import com.asia.paint.base.network.bean.GoodsRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-14.
 */
public class GoodsShowViewModel extends BaseViewModel {
    private CallbackDate<GoodsRsp> mGoodsRsp = new CallbackDate<>();


    public CallbackDate<GoodsRsp> loadShowGoods(int page, int cate_id) {
        NetworkFactory.createService(ShopService.class)
                .loadShopGoods(page, "is_best", "desc", "", cate_id, null, 6)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<GoodsRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(GoodsRsp goodsRsp) {
                        mGoodsRsp.setData(goodsRsp);
                    }

                });
        return mGoodsRsp;
    }
}
