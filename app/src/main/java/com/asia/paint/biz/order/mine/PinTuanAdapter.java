package com.asia.paint.biz.order.mine;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.MyPinTuan;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * 拼团列表适配器
 *
 * @author tangkun
 */
public class PinTuanAdapter extends BaseGlideAdapter<MyPinTuan> {

	private OnChangeCallback<MyPinTuan> mOnChangeCallback;

	public PinTuanAdapter() {
		super(R.layout.item_pintuan_mine);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, MyPinTuan item) {
		if (item != null) {
			//【1成功，2失败，3进行中】
			switch (item.status) {
				case 1:
					helper.setText(R.id.tv_status, "成功");
					break;
				case 2:
					helper.setText(R.id.tv_status, "失败");
					break;
				case 3:
					helper.setText(R.id.tv_status, "进行中");
					break;
			}
			if (item.default_image != null && item.default_image.size() > 0) {
				helper.loadRoundedCornersImage(R.id.iv_goods_img, item.default_image.get(0), 4);
			}
			helper.setText(R.id.tv_goods_name, item.goods_name);
			helper.setText(R.id.tv_pintuan_number, item.number + "人拼");
			helper.setText(R.id.tv_price, PriceUtils.getPrice(item.price));
			helper.addOnClickListener(R.id.tv_detai_pintuan, R.id.tv_detai_order);
		}
	}
}
