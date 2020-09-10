package com.asia.paint.biz.order.mine;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * @author by chenhong14 on 2019-11-26.
 */
public class OrderMineGoodsAdapter extends BaseGlideAdapter<OrderDetail.Goods> {
	private boolean mShowDivider;
	private int mOrderStatus;

	public OrderMineGoodsAdapter(boolean showDivider) {
		super(R.layout.item_order_mine_goods);
		mShowDivider = showDivider;
	}

	public void setOrderStatus(int status) {
		mOrderStatus = status;
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, OrderDetail.Goods item) {
		if (item != null) {
			helper.loadImage(R.id.iv_goods_img, item.goods_image);
			helper.setText(R.id.tv_goods_name, item.goods_name);
			if (item.is_gift == 200) {
				//赠品
				helper.setText(R.id.tv_goods_price, "赠品");
			} else {
				//商品
				helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(item.goods_price));
			}
			helper.setText(R.id.tv_goods_spec, String.format("规格：%s", item.specification));
			helper.setText(R.id.tv_goods_count, String.format("x%s", item.goods_numbers));
			helper.setGone(R.id.tv_after_sale, showAfterSale(item));
			helper.addOnClickListener(R.id.tv_after_sale);
			helper.setVisible(R.id.view_divider, mShowDivider && helper.getAdapterPosition() != getItemCount() - 1);
		}
	}

	private boolean showAfterSale(OrderDetail.Goods item) {
		return mShowDivider && (item.status == 1
				|| mOrderStatus == OrderService.ORDER_DELIVERY
				|| mOrderStatus == OrderService.ORDER_RECEIVE
				|| mOrderStatus == OrderService.ORDER_DONE
				|| mOrderStatus == OrderService.ORDER_COMMENT
		);
	}
}
