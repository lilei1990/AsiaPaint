package com.asia.paint.biz.shop.detail;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * 商品详情组合购列表自列表适配器
 *
 * @author tangkun
 */
public class CombinationShoppingItemAdapter extends BaseGlideAdapter<PromotionComposeRsp.ResultBean.GoodsBean> {
	private String marketPrice;
	private String price;

	public CombinationShoppingItemAdapter(String marketPrice, String price) {
		super(R.layout.item_combination_shopping_goods);
		this.marketPrice = marketPrice;
		this.price = price;
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, PromotionComposeRsp.ResultBean.GoodsBean item) {
		if (item != null) {
			if (helper.getLayoutPosition() == (getItemCount() - 1)) {
				helper.setGone(R.id.iv_add, false);
				helper.setGone(R.id.ll_total_price, true);
				helper.setText(R.id.tv_price_discount, PriceUtils.getPrice(price));
				helper.setText(R.id.tv_price, "原价" + PriceUtils.getPrice(marketPrice));
				((TextView) helper.itemView.findViewById(R.id.tv_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间加横线
				((TextView) helper.itemView.findViewById(R.id.tv_price)).getPaint().setAntiAlias(true);//抗锯齿
			} else {
				helper.setGone(R.id.iv_add, true);
				helper.setGone(R.id.ll_total_price, false);
			}
			if (item.default_image != null && item.default_image.size() > 0) {
				helper.loadRoundedCornersImage(R.id.iv_icon, item.default_image.get(0), 4);
			}
			helper.setText(R.id.tv_name, item.goods_name);
			helper.setText(R.id.tv_count, "X" + item.num);
		}
	}
}
