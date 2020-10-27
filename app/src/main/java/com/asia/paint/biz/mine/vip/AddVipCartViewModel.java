package com.asia.paint.biz.mine.vip;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.model.AddCartViewModel;
import com.asia.paint.base.network.api.CartService;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.CartCountRsp;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.mine.vip.Dialog.VipGoodsSpecDialog;
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.PairCallbackDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import io.reactivex.disposables.Disposable;


/**
 * @author by chenhong14 on 2019-11-20.
 */
public class AddVipCartViewModel extends BaseViewModel {

    private CallbackDate<Boolean> mAddCartSuccess = new CallbackDate<>();
    private CallbackDate<Boolean> mBuySuccess = new CallbackDate<>();
    private CallbackDate<CartCountRsp> mCartCountSuccess = new CallbackDate<>();
    private PairCallbackDate<Specs, Integer> mSpecResult = new PairCallbackDate<>();
    private MutableLiveData<Long> mVipCart = new MutableLiveData<Long>();;
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
            return ;
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
                    addCart(spec.spec_id, count);
                }
                dialog.dismiss();
            }

            @Override
            public void onBuy(Specs spec, int count) {
                dialog.dismiss();
                if (spec != null && count > 0) {
                    if (id == 0) {
                        directBuy(spec.spec_id, count).setCallback(new OnChangeCallback<Boolean>() {
                            @Override
                            public void onChange(Boolean result) {
                                OrderCheckoutActivity.start(context, OrderService.BUY, spec.spec_id, count);
                            }
                        });
                    } else if (mType == OrderService.GROUP) {
                        OrderCheckoutActivity.start(context, mType, spec.spec_id, count, id);
                    } else if (mType == OrderService.SPIKE) {
                        OrderCheckoutActivity.start(context, mType, spec.spec_id, count, id);
                    }
                }
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
     * @param spec_id
     * @param count
     */
    public void addCart(int spec_id, int count) {
        mVipCart.postValue(1123L+1);
    }

    public LiveData<Long> getVipCart() {

        return mVipCart;
    }

}
