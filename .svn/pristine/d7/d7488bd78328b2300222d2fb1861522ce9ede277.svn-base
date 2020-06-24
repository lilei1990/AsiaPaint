package com.asia.paint.biz.shop.detail;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.PromotionGroupPintuan;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

/**
 * 商品详情拼团购列表适配器
 *
 * @author tangkun
 */
public class PinTuanAdapter extends BaseGlideAdapter<PromotionGroupPintuan> {
	public PinTuanAdapter() {
		super(R.layout.item_pintuan);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, PromotionGroupPintuan item) {
		if (item != null) {
			if (helper.getLayoutPosition() == (getItemCount() - 1)) {
				helper.setGone(R.id.divider, false);
			} else {
				helper.setGone(R.id.divider, true);
			}
			helper.loadCircleImage(R.id.iv_image, item.avatar);
			helper.setText(R.id.tv_name, item.nickname);
			helper.setText(R.id.tv_lack_number, "还差" + item.need + "人成团");
			helper.setText(R.id.tv_limit_time, "剩余" + item.showHour + ":" + item.showMinute + ":" + item.showSecond);
			helper.addOnClickListener(R.id.tv_join);
		}
	}
}
