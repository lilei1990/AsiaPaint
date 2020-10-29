package com.asia.paint.biz.mine.vip;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.bean.Address;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.Specs;
import com.asia.paint.biz.mine.vip.Dialog.VipGoodsSpecDialog;
import com.asia.paint.biz.mine.vip.data.CartList;

import java.util.ArrayList;

/**
 * 作者 : LiLei
 * 时间 : 2020/10/27.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
public class VipGoodViewModel extends BaseViewModel {

    private MutableLiveData<ArrayList<CartList>> mVipCart = new MutableLiveData<ArrayList<CartList>>();
    private MutableLiveData<Boolean> mSheetIsShow = new MutableLiveData<Boolean>(false);
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
                .setGoodsName(goods.goods_name)
                .build();
        dialog.setOnClickGoodsSpecListener(new VipGoodsSpecDialog.OnClickGoodsSpecListener() {
            @Override
            public void onAddCart(CartList cartList) {
                if (cartList.spec != null && cartList.count > 0) {
                    addCart(cartList);
                }
                dialog.dismiss();
            }
        });
        dialog.show(context);

    }


    /**
     * 添加到购物车
     */
    ArrayList<CartList> mCartLists = new ArrayList<>();

    public void addCart(CartList cartList) {
        int goods_id = cartList.spec.goods_id;
        int spec_id = cartList.spec.spec_id;
        int count = cartList.count;
        for (CartList list : mCartLists) {
            //如果商品和规格一样只是添加数量
            if (goods_id == list.spec.goods_id) {
                if (spec_id == list.spec.spec_id) {
                    list.count += count;
                    notification();
                    return;
                }
            }
        }
        mCartLists.add(cartList);
        mVipCart.postValue(mCartLists);
    }

    /**
     * 强制更新数据
     */
    public void notification() {
        ArrayList<CartList> tem = new ArrayList<>();
        tem.addAll(mCartLists);
        mVipCart.postValue(mCartLists);
    }
    public void setSheetIsShow(Boolean sheetIsShow) {
        mSheetIsShow.postValue(sheetIsShow);
    }

    public Boolean getSheetIsShow() {
        return mSheetIsShow.getValue();
    }

    public MutableLiveData<ArrayList<CartList>> getVipCart() {

        return mVipCart;
    }


}
