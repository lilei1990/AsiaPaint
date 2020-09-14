package com.asia.paint.biz.cart;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.CartGoods;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.base.widgets.CountView;
import com.asia.paint.utils.utils.PriceUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-10.
 */
public class ShopCartAdapter extends BaseGlideAdapter<CartGoods> implements CountView.CountViewCallback {

    private ShopCartCallback mCallback;
    private CartFragment.Mode mMode;


    private List<CartGoods> mSelectedCardGoods = new ArrayList<>();

    public ShopCartAdapter() {
        super(R.layout.item_shop_cart, new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, CartGoods cartGoods) {

        CheckBox checkBox = helper.getView(R.id.cb_check);
        checkBox.setChecked(isCheck(cartGoods));
        checkBox.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                if (mMode == CartFragment.Mode.CART) {
                    if (mCallback != null) {
                        mCallback.onCheck(!isChecked, cartGoods);
                    }
                } else {
                    if (mSelectedCardGoods.contains(cartGoods)) {
                        mSelectedCardGoods.remove(cartGoods);
                    } else {
                        mSelectedCardGoods.add(cartGoods);
                    }
                    notifyDataSetChanged();
                    if (mCallback != null) {
                        mCallback.onEditCheckUpdate();
                    }
                }
            }

            @Override
            public boolean changeBySelf() {
                return false;
            }

        });

        helper.loadImage(R.id.iv_goods_icon, cartGoods.goods_image);
        helper.setText(R.id.tv_goods_name, cartGoods.goods_name+"\n");
        helper.setText(R.id.tv_goods_spec, String.format("规格：%s", cartGoods.specification));
        helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(cartGoods.price));
        CountView countView = helper.getView(R.id.view_count);
        countView.setEnable(mMode == CartFragment.Mode.CART);
        countView.setMinCount(1);
        countView.setSpecId(cartGoods.spec_id);
        countView.setRecId(cartGoods.rec_id);
        countView.setCallback(this);
        countView.setCount(cartGoods.quantity);
    }

    public void setAllCheck(boolean checkAll) {
        mSelectedCardGoods.clear();
        if (checkAll) {
            mSelectedCardGoods.addAll(getData());
        }
        notifyDataSetChanged();
    }

    public void setCallBack(ShopCartCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onUpdate() {
        if (mCallback != null) {
            mCallback.onUpdate();
        }
    }

    @Override
    public void onChange(int count) {

    }

    public CartFragment.Mode getMode() {
        return mMode;
    }

    public void setMode(CartFragment.Mode mode) {
        mMode = mode;
        mSelectedCardGoods.clear();
        notifyDataSetChanged();
    }


    public List<CartGoods> getSelectedCardGoods() {
        return new ArrayList<>(mSelectedCardGoods);
    }

    public interface ShopCartCallback {

        void onUpdate();

        void onCheck(boolean isChecked,CartGoods cartGoods);

        void onEditCheckUpdate();
    }


    public int getCheckCount() {
        int count = 0;
        List<CartGoods> datas = getData();
        for (CartGoods cartGoods : datas) {
            if (cartGoods != null && cartGoods.isCheck()) {
                count += cartGoods.quantity;
            }
        }
        return count;
    }

    private boolean isCheck(CartGoods cartGoods) {
        return cartGoods != null && (mMode == CartFragment.Mode.CART ? cartGoods.isCheck() : mSelectedCardGoods.contains(cartGoods));
    }

    public boolean isAllCheck() {
        List<CartGoods> datas = getData();
        for (CartGoods cartGoods : datas) {
            if (!isCheck(cartGoods)) {
                return false;
            }
        }
        return true;
    }
}
