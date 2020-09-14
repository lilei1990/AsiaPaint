package com.asia.paint.biz.shop.goods;

import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.network.bean.GoodsRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.PriceUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-05.
 */
public class GoodsAdapter extends BaseGlideAdapter<Goods> {

    private OnClickChildListener mChildListener;

    public GoodsAdapter(@Nullable List<Goods> data) {
        super(R.layout.item_goods, data);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Goods goods) {

        String iconUrl = "";
        if (goods.default_image != null && goods.default_image.size() > 0) {
            iconUrl = goods.default_image.get(0);
        }
        helper.loadImage(R.id.iv_goods_icon, iconUrl);
        helper.setText(R.id.tv_goods_name, goods.goods_name);
        String sellCount = String.format("已售卖 %s", goods.sales);
        helper.setText(R.id.tv_sell_count, sellCount);
        helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(goods.price));
        View ivCollect = helper.getView(R.id.iv_goods_collect);
        ivCollect.setSelected(goods.isCollect());
        ivCollect.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mChildListener != null) {
                    mChildListener.onCollect(goods);
                }
            }
        });
        helper.setOnClickListener(R.id.btn_add_cart, v -> {
            if (mChildListener != null) {
                mChildListener.onAddCart(goods);
            }
        });
    }

    public void setOnChildLisenter(OnClickChildListener childLisenter) {
        mChildListener = childLisenter;
    }

    public interface OnClickChildListener {
        void onAddCart(Goods goods);

        void onCollect(Goods goods);
    }
}
