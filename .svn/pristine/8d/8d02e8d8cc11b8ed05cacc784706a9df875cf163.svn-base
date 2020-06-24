package com.asia.paint.biz.mine.money;

import androidx.annotation.NonNull;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.ApplyTaskRsp;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.PriceUtils;

/**
 * 分销任务商品列表适配器
 *
 * @author tangkun
 */
public class DistributionTasksGoodsAdapter extends BaseGlideAdapter<ApplyTaskRsp.GoodsBean> {
	public DistributionTasksGoodsAdapter() {
		super(R.layout.item_distribution_tasks_goods);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, ApplyTaskRsp.GoodsBean item) {
		if (item != null) {
			if (item.default_image != null && item.default_image.size() > 0) {
				helper.loadRoundedCornersImage(R.id.iv_icon, item.default_image.get(0), 4);
			}
			helper.setText(R.id.tv_name, item.goods_name);
			helper.setText(R.id.tv_price, PriceUtils.getPrice(item.price));
			helper.setText(R.id.tv_count, "X" + item.number);
		}
	}
}
