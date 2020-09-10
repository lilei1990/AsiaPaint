package com.asia.paint.biz.mine.seller.staff.detail;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-30.
 */
public class StaffDealGoodsAdapter extends BaseGlideAdapter<OrderDetail.Goods> {
    public StaffDealGoodsAdapter() {
        super(R.layout.item_staff_deal_goods);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, OrderDetail.Goods goods) {
        if (goods != null) {
            helper.loadImage(R.id.iv_goods_icon, goods.goods_image);
            helper.setText(R.id.tv_goods_name, goods.goods_name);
            helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(goods.goods_price));
            helper.setText(R.id.tv_goods_count, String.format("x %s", goods.goods_numbers));
        }
    }
}
