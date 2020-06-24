package com.asia.paint.biz.shop.goods;

import com.asia.paint.base.model.AddCartViewModel;
import com.asia.paint.base.network.api.ShopService;
import com.asia.paint.base.network.bean.GoodsRsp;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-18.
 */
public class GoodsViewModel extends AddCartViewModel {

    public enum Sort {
        //降序
        DESC("desc"),
        //升序
        ASC("asc");

        private String value;

        Sort(String value) {
            this.value = value;
        }
    }

    private CallbackDate<GoodsRsp> mGoodsRsp = new CallbackDate<>();

    private ShopBannerRsp.CategoryBean mCategory;

    public List<String> getGoodsCategory() {
        List<String> category = new ArrayList<>();
        category.add("销量");
        category.add("价格");
        category.add("新品");
        category.add("推荐");
        return category;
    }

    public CallbackDate<GoodsRsp> loadGoodsByTag(@NonNull String tag, Sort sort) {
        switch (tag) {
            case "销量":
                loadGoodsViaSales();
                break;
            case "价格":
                loadGoodsByPrice(sort);
                break;
            case "新品":
                loadGoodsByNew();
                break;
            case "推荐":
                loadGoodsByRecommend();
                break;
        }
        return mGoodsRsp;
    }

    /**
     * 销量
     */
    private void loadGoodsViaSales() {

        loadShopGoods(getCurPage(), "sales", Sort.DESC.value)
                .subscribe(new DefaultNetworkObserver<GoodsRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(GoodsRsp goodsRsp) {
                        mGoodsRsp.setData(goodsRsp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mGoodsRsp.setData(null);
                    }
                });
    }

    /**
     * 价格
     */
    private void loadGoodsByPrice(Sort sort) {
        if (sort == null) {
            sort = Sort.DESC;
        }
        loadShopGoods(getCurPage(), "price", sort.value)
                .subscribe(new DefaultNetworkObserver<GoodsRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(GoodsRsp goodsRsp) {
                        mGoodsRsp.setData(goodsRsp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mGoodsRsp.setData(null);
                    }

                });
    }

    private void loadGoodsByRecommend() {

        loadShopGoods(getCurPage(), "is_best", Sort.DESC.value)
                .subscribe(new DefaultNetworkObserver<GoodsRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(GoodsRsp goodsRsp) {
                        mGoodsRsp.setData(goodsRsp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mGoodsRsp.setData(null);
                    }

                });
    }

    private void loadGoodsByNew() {

        loadShopGoods(getCurPage(), "goods_id", Sort.DESC.value)
                .subscribe(new DefaultNetworkObserver<GoodsRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(GoodsRsp goodsRsp) {
                        mGoodsRsp.setData(goodsRsp);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mGoodsRsp.setData(null);
                    }

                });
    }

    private Observable<BaseRsp<GoodsRsp>> loadShopGoods(int page, String order, String sort) {

        Integer ids = mCategory != null ? mCategory.id : null;
        return NetworkFactory.createService(ShopService.class)
                .loadShopGoods(page, order, sort, "", null, ids, null)
                .compose(new NetworkObservableTransformer<>());
    }

    public ShopBannerRsp.CategoryBean getCategory() {
        return mCategory;
    }

    public void setCategory(ShopBannerRsp.CategoryBean category) {
        mCategory = category;
    }
}
