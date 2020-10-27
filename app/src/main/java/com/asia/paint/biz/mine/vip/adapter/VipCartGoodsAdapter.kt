package com.asia.paint.biz.mine.vip.adapter

import android.content.Context
import com.asia.paint.android.R
import com.asia.paint.base.network.bean.CartGoods
import com.asia.paint.base.recyclerview.BaseGlideAdapter
import com.asia.paint.base.recyclerview.GlideViewHolder
import com.asia.paint.base.widgets.CountView
import com.asia.paint.biz.cart.CartFragment
import com.asia.paint.biz.mine.vip.CartList
import com.asia.paint.utils.utils.PriceUtils

/**
 * 作者 : LiLei
 * 时间 : 2020/10/27.
 * 邮箱 :416587959@qq.com
 * 描述 :Vip购物车点击弹出的列表
 */
class VipCartGoodsAdapter(data: ArrayList<CartList>, activity: Context) : BaseGlideAdapter<CartList>(R.layout.item_shop_cart, data) {
    private val mMode: CartFragment.Mode? = null
    override fun convert(helper: GlideViewHolder, cartGoods: CartList) {
//        val checkBox = helper.getView<CheckBox>(R.id.cb_check)
//        checkBox.isChecked = true
//        checkBox.setListener(object : OnCheckChangeListener {
//            override fun onChange(isChecked: Boolean) {
//                if (mMode == CartFragment.Mode.CART) {
//                    if (mCallback != null) {
//                        mCallback.onCheck(!isChecked, cartGoods)
//                    }
//                } else {
//                    if (mSelectedCardGoods.contains(cartGoods)) {
//                        mSelectedCardGoods.remove(cartGoods)
//                    } else {
//                        mSelectedCardGoods.add(cartGoods)
//                    }
//                    notifyDataSetChanged()
//                    if (mCallback != null) {
//                        mCallback.onEditCheckUpdate()
//                    }
//                }
//            }
//
//            override fun changeBySelf(): Boolean {
//                return false
//            }
//        })

        helper.loadImage(R.id.iv_goods_icon, "cartGoods.goods_image")
        helper.setText(R.id.tv_goods_name, "cartGoods.goods_name" + "\n")
        helper.setText(R.id.tv_goods_spec, String.format("规格：%s", "cartGoods.specification"))
        helper.setText(R.id.tv_goods_price, PriceUtils.getPrice("100"))
        val countView = helper.getView<CountView>(R.id.view_count)
        countView.setEnable(mMode == CartFragment.Mode.CART)
        countView.setMinCount(1)
        countView.specId = 111
        countView.setRecId(111)
//        countView.setCallback(this)
        countView.count = 1
    }


}