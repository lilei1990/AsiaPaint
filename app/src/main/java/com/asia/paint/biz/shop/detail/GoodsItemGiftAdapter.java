package com.asia.paint.biz.shop.detail;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Gift;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

/**
 * 提交订单商品列表中礼物列表适配器
 *
 * @author tangkun
 */
public class GoodsItemGiftAdapter extends BaseGlideAdapter<Gift> {

	public GoodsItemGiftAdapter() {
		super(R.layout.item_goods_item_gift);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, Gift item) {
		if (item != null) {
			helper.setText(R.id.tv_name, item.goods_name);
			helper.setText(R.id.tv_count, "x" + item.number);
		}
	}
}
