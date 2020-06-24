package com.asia.paint.base.widgets.show;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Goods;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

import androidx.annotation.NonNull;

public class GoodsShowAdapter extends BaseGlideAdapter<Goods> {

    public GoodsShowAdapter() {
        super(R.layout.item_goods_show);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Goods goods) {
        if (goods != null) {
            String iconUrl = "";
            if (goods.default_image != null && goods.default_image.size() > 0) {
                iconUrl = goods.default_image.get(0);
            }
            helper.loadImage(R.id.iv_goods_icon, iconUrl);
            helper.setText(R.id.price, PriceUtils.getPrice(goods.price));
            helper.setText(R.id.tv_goods_name, goods.goods_name + "\n");
        }
    }
}