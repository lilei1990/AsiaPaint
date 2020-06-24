package com.asia.paint.biz.order.checkout;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.CartGoods;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CountView;
import com.asia.paint.biz.shop.detail.GoodsItemGiftAdapter;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class OrderCartAdapter extends BaseGlideAdapter<CartGoods> {
	public OrderCartAdapter() {
		super(R.layout.item_order_cart);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, CartGoods cart) {
		if (cart != null) {
			helper.loadImage(R.id.iv_goods_icon, cart.goods_image);
			helper.setText(R.id.tv_goods_name, cart.goods_name);
			helper.setText(R.id.tv_goods_spec, String.format("规格分类：%s", cart.specification));
			helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(cart.price));
			CountView countView = helper.getView(R.id.view_count);
			countView.setCount(cart.quantity);
			countView.setEnable(false);

			//礼品
			RecyclerView recyclerView = helper.itemView.findViewById(R.id.rv_order_gift);
			if (cart._gift != null && cart._gift.size() > 0) {
				GoodsItemGiftAdapter mGoodsItemGiftAdapter = new GoodsItemGiftAdapter();
				mGoodsItemGiftAdapter.updateData(cart._gift);
				recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
				recyclerView.setAdapter(mGoodsItemGiftAdapter);
				recyclerView.setVisibility(View.VISIBLE);
			} else {
				recyclerView.setVisibility(View.GONE);
			}
		}
	}
}
