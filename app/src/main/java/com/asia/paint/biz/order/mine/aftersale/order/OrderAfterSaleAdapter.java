package com.asia.paint.biz.order.mine.aftersale.order;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.AfterSaleGoods;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-08.
 */
public class OrderAfterSaleAdapter extends BaseGlideAdapter<AfterSaleGoods> {
    public OrderAfterSaleAdapter() {
        super(R.layout.item_after_sale);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, AfterSaleGoods goods) {

        if (goods != null) {
            helper.setText(R.id.tv_date, goods.goods_name);
            helper.loadRoundedCornersImage(R.id.iv_icon, goods.goods_image, 4);
            helper.setText(R.id.tv_price, PriceUtils.getPrice(goods.money));
            helper.setText(R.id.tv_name, PriceUtils.getPrice(goods.goods_name));
            helper.setText(R.id.tv_goods_num, String.valueOf(goods.num));
            helper.setText(R.id.tv_goods_spec, String.valueOf(goods.specification));
            helper.setText(R.id.tv_status, String.valueOf(goods.status_text));
            helper.setGone(R.id.tv_cancel, goods.status == 1);
            helper.addOnClickListener(R.id.tv_view_detail, R.id.tv_cancel);
        }

    }
}
