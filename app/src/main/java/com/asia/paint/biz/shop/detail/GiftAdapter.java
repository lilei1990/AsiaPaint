package com.asia.paint.biz.shop.detail;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.Gift;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * 商品详情拼团购列表适配器
 *
 * @author tangkun
 */
public class GiftAdapter extends BaseGlideAdapter<Gift> {
	public GiftAdapter() {
		super(R.layout.item_complimentary);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, Gift item) {

		if (item != null) {
			if (item.default_image != null && item.default_image.size() > 0) {
				helper.loadRoundedCornersImage(R.id.iv_icon, item.default_image.get(0), 4);
			}
			helper.setText(R.id.tv_name, item.goods_name);
			helper.setText(R.id.tv_price, PriceUtils.getPrice(item.price));
			helper.setText(R.id.tv_count, item.number + "");
		}
	}
}
