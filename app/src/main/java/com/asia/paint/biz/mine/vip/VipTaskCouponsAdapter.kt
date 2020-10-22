package com.asia.paint.biz.mine.vip

import android.text.TextUtils
import com.asia.paint.android.R
import com.asia.paint.base.network.data.Bonu
import com.asia.paint.base.recyclerview.BaseGlideAdapter
import com.asia.paint.base.recyclerview.GlideViewHolder
import com.asia.paint.utils.utils.DigitUtils

/**
 * 分销任务优惠券列表适配器
 *
 * @author tangkun
 */
class VipTaskCouponsAdapter(layoutResId: Int) : BaseGlideAdapter<Bonu?>(layoutResId) {
    override fun convert(helper: GlideViewHolder, coupon: Bonu?) {
        if (coupon != null) {
            helper.setText(R.id.tv_coupon_value, coupon.money)
            helper.setText(R.id.tv_coupon_name, coupon.name)
            val limitMoney = if (DigitUtils.parseFloat(coupon.min_money) > 0) String.format("满%s可用", coupon.min_money) else ""
            helper.setText(R.id.tv_limit_money, limitMoney)
            if (coupon.type == 2 && !TextUtils.isEmpty(coupon.strtime) && !TextUtils.isEmpty(coupon.endtime)) {
                helper.setText(R.id.tv_coupon_period, String.format("%s-%s", coupon.strtime, coupon.endtime))
            } else {
                helper.setText(R.id.tv_coupon_period, "永久有效")
            }
            helper.setText(R.id.tv_coupon_limit, if (coupon.group == 1) "全部商品可用" else "部分商品可用")
            helper.setText(R.id.tv_coupon_type, "通用票")
        }
    }
}