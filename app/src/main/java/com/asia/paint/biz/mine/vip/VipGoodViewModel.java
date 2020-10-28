package com.asia.paint.biz.mine.vip;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.mine.vip.Dialog.VipGoodsSpecDialog;
import com.asia.paint.biz.mine.vip.data.CartList;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.PairCallbackDate;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : LiLei
 * 时间 : 2020/10/27.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
public class VipGoodViewModel extends BaseViewModel {

    private CallbackDate<Boolean> mBuySuccess = new CallbackDate<>();
    private PairCallbackDate<Specs, Integer> mSpecResult = new PairCallbackDate<>();
    private MutableLiveData<ArrayList<CartList>> mVipCart = new MutableLiveData<ArrayList<CartList>>();
    ;

    /**
     * @param context
     * @param mType
     * @param id
     * @param selectedSpec
     * @param count
     * @param goods
     * @param type
     * @return
     */
    public void showVipGoodsSpecDialog(Context context, int mType, int id, Specs selectedSpec, int count, Goods goods, VipGoodsSpecDialog.Type type) {
        if (context == null || goods == null || goods._specs == null) {
            return;
        }
        String iconUrl = "";
        if (goods.default_image != null && !goods.default_image.isEmpty()) {
            iconUrl = goods.default_image.get(0);
        }
        VipGoodsSpecDialog dialog = new VipGoodsSpecDialog.Builder()
                .setType(type)
                .setIconUrl(iconUrl)
                .setSpec(selectedSpec)
                .setCount(count)
                .setSpecList(goods._specs)
                .build();
        dialog.setOnClickGoodsSpecListener(new VipGoodsSpecDialog.OnClickGoodsSpecListener() {
            @Override
            public void onAddCart(Specs spec, int count) {
                if (spec != null && count > 0) {
                    addCart(spec, count);
                }
                dialog.dismiss();
            }

            @Override
            public void onBuy(Specs spec, int count) {
                dialog.dismiss();
            }

            @Override
            public void onDismiss(Specs spec, int count) {
                mSpecResult.setData(spec, count);
            }
        });
        dialog.show(context);

    }

    public CallbackDate<Boolean> directBuy(int spec_id, int count) {
        NetworkFactory.createService(OrderService.class)
                .directBuy(spec_id, count)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mBuySuccess.setData(true);
                    }

                });
        return mBuySuccess;
    }

    /**
     * 添加到购物车
     */
    ArrayList<CartList> objects = new ArrayList<>();

    public void addCart(Specs spec, int count) {
        CartList cartList = new CartList();
        cartList.spec = spec;
        cartList.count = count;
        objects.add(cartList);
        mVipCart.postValue(objects);
    }

    public MutableLiveData<ArrayList<CartList>> getVipCart() {

        return mVipCart;
    }


}
