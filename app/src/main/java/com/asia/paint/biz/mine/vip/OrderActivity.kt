package com.asia.paint.biz.mine.vip

import com.asia.paint.android.R
import com.asia.paint.android.databinding.ActivityOrderCheckoutBinding
import com.asia.paint.base.container.BaseTitleActivity

/**
 * 提交订单
 *
 * @author by chenhong14 on 2019-11-10.
 */
class OrderActivity : BaseTitleActivity<ActivityOrderCheckoutBinding?>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_order_checkout
    }

    override fun middleTitle(): String {
        return "提交订单"
    }
}