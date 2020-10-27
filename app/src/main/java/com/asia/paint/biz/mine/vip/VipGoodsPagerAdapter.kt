package com.asia.paint.biz.mine.vip

import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.asia.paint.base.network.bean.ShopBannerRsp
import com.asia.paint.base.network.data.VipCategory
import com.asia.paint.biz.mine.vip.flash.FlashVipGoodsFragment
import com.asia.paint.biz.mine.vip.flash.VipGoodsFragment
import com.asia.paint.biz.shop.goods.GoodsFragment
import java.util.*

/**
 * 作者 : LiLei
 * 时间 : 2020/10/22.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */

class VipGoodsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mCategory: ArrayList<VipCategory> = ArrayList()
    override fun getItem(position: Int): Fragment {
        val categoryBean = mCategory[position]
//        return if (TextUtils.equals(categoryBean.name, CATEGORY_FLASH_SALE) || TextUtils.equals(categoryBean.name, CATEGORY_GROUP)) {
//            FlashVipGoodsFragment.create(categoryBean)
//        } else VipGoodsFragment.create(categoryBean)
        return VipGoodsFragment.create(categoryBean)
    }

    override fun getCount(): Int {
        return mCategory.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val category = mCategory[position]
        return if (category != null) category.name else ""
    }

    fun update(categorys: ArrayList<VipCategory>) {
        mCategory.clear()
        if (categorys != null) {
            mCategory.addAll(categorys)
        }
        notifyDataSetChanged()
    }
}