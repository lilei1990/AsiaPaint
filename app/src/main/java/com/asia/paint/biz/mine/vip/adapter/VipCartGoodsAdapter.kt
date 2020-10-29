package com.asia.paint.biz.mine.vip.adapter

import com.asia.paint.android.R
import com.asia.paint.base.recyclerview.BaseGlideAdapter
import com.asia.paint.base.recyclerview.GlideViewHolder
import com.asia.paint.biz.mine.vip.VipGoodViewModel
import com.asia.paint.biz.mine.vip.data.CartList
import com.asia.paint.biz.mine.vip.widget.CountViewVip
import com.asia.paint.biz.mine.vip.widget.CountViewVip.CountViewCallback
import com.asia.paint.utils.utils.PriceUtils

/**
 * 作者 : LiLei
 * 时间 : 2020/10/27.
 * 邮箱 :416587959@qq.com
 * 描述 :Vip购物车点击弹出的列表
 */
class VipCartGoodsAdapter(data: ArrayList<CartList>,  vipGoodViewModel: VipGoodViewModel) : BaseGlideAdapter<CartList>(R.layout.item_shop_cart_vip, data){
    private val vipGoodViewModel:VipGoodViewModel = vipGoodViewModel
    override fun convert(helper: GlideViewHolder, cartGoods: CartList) {

        helper.loadImage(R.id.iv_goods_icon, cartGoods.iconUrl)
        helper.setText(R.id.tv_goods_name,  cartGoods.goodName + "\n")
        helper.setText(R.id.tv_goods_spec, String.format("规格：%s", cartGoods.spec.spec_1))
        helper.setText(R.id.tv_goods_price, PriceUtils.getPrice(cartGoods.spec.price))
        val countView = helper.getView<CountViewVip>(R.id.view_count)
        countView.setEnable(true)
        countView.setMinCount(1)
        countView.specId = 111
        countView.setRecId(111)
        countView.count = cartGoods.count


        countView.setCallback((object : CountViewCallback {

            override fun onChange(count: Int) {
                cartGoods.count=count
                vipGoodViewModel.notification()
            }

            override fun onUpdate() {
            }

        }))


    }



}