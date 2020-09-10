package com.asia.paint.biz.mine.money;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.ApplyTaskRsp;
import com.asia.paint.base.network.bean.Coupon;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.utils.utils.DigitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分销任务优惠券列表适配器
 *
 * @author tangkun
 */
public class DistributionTaskCouponsAdapter extends BaseGlideAdapter<ApplyTaskRsp.BonusBean> {

	public static final int TYPE_ORDER = 999;

	private List<Coupon> mCoupons = new ArrayList<>();

	public DistributionTaskCouponsAdapter() {
		super(R.layout.item_distribution_tasks_coupons);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, ApplyTaskRsp.BonusBean coupon) {
		if (coupon != null) {
			helper.setText(R.id.tv_coupon_value, coupon.money);
			helper.setText(R.id.tv_coupon_name, coupon.name);
			String limitMoney = DigitUtils.parseFloat(coupon.min_money) > 0 ? String.format("满%s可用", coupon.min_money) : "";
			helper.setText(R.id.tv_limit_money, limitMoney);
			if (coupon.type == 2 && !TextUtils.isEmpty(coupon.strtime) && !TextUtils.isEmpty(coupon.endtime)) {
				helper.setText(R.id.tv_coupon_period, String.format("%s-%s", coupon.strtime, coupon.endtime));
			} else {
				helper.setText(R.id.tv_coupon_period, "永久有效");
			}
			helper.setText(R.id.tv_coupon_limit, coupon.group == 1 ? "全部商品可用" : "部分商品可用");
			helper.setText(R.id.tv_coupon_type, "通用票");
		}
	}
}
