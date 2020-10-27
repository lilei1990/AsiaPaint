package com.asia.paint.biz.mine.vip

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asia.paint.android.R
import com.asia.paint.android.databinding.ActivityVipGoodBinding
import com.asia.paint.base.container.BaseTitleActivity
import com.asia.paint.base.network.api.OrderService
import com.asia.paint.base.network.api.VipService
import com.asia.paint.base.network.core.DefaultNetworkObserverList
import com.asia.paint.base.network.data.VipCategory
import com.asia.paint.base.recyclerview.DefaultItemDecoration
import com.asia.paint.biz.mine.vip.adapter.VipCartGoodsAdapter
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity
import com.asia.paint.network.NetworkFactory
import com.asia.paint.network.NetworkObservableTransformer
import com.asia.paint.utils.callback.OnChangeCallback
import com.asia.paint.utils.utils.AppUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_vip_goods_cart.view.*

class VipGoodActivity : BaseTitleActivity<ActivityVipGoodBinding>(), OnChangeCallback<Int> {
    //    private val CATEGORY_FLASH_SALE = "秒杀"
//    private val CATEGORY_GROUP = "火爆拼团"
    private val CATEGORY_ALL = "全部"
    lateinit var mGoodsPagerAdapter: VipGoodsPagerAdapter

    lateinit var mVipGoodsAdapter: VipCartGoodsAdapter
    lateinit var viewModel: VipGoodViewModel
    //存购物车数据
    private lateinit var mVipCartList: ArrayList<CartList>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(VipGoodViewModel::class.java)

        mGoodsPagerAdapter = VipGoodsPagerAdapter(supportFragmentManager)
        mBinding.viewPager.setAdapter(mGoodsPagerAdapter)
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

        //去结算
        mBinding.tvCheckout.setOnClickListener { v ->
            OrderCheckoutActivity.start(mContext, OrderService.VIP_CART)
        }
        //购物车
        val cartView = LayoutInflater.from(mContext).inflate(R.layout.dialog_vip_goods_cart, mBinding.bottomsheet, false)
        val rvCartList = cartView.rv_cart_list
        rvCartList.setLayoutManager(LinearLayoutManager(mContext, RecyclerView.VERTICAL, false))
        rvCartList.addItemDecoration( DefaultItemDecoration(0, 0, 0, 1));
        mVipGoodsAdapter = VipCartGoodsAdapter(ArrayList(), mContext)
        rvCartList.setAdapter(mVipGoodsAdapter)
        val specView = LayoutInflater.from(mContext).inflate(R.layout.dialog_vip_goods_spec, mBinding.bottomsheet, false)
        mBinding.tvGoCart.setOnClickListener { v ->
            TODO("二次点击关闭的功能有待开发")
            //弹出View  bottomSheet即是要弹出的view
            mBinding.bottomsheet.showWithSheetView(cartView);

        }
        //关闭弹窗
        cartView.iv_close.setOnClickListener { v ->
            mBinding.bottomsheet.dismissSheet();
        }
        //购物车显示
        viewModel.vipCart?.observe(this, Observer { t ->
            mVipCartList = t
            var count: Int = 0
            for (cartList in t) {
                count += cartList.count
            }
            mVipGoodsAdapter.replaceData(mVipCartList)
            setCartCount(count)
        })
        loadCategory()
    }

    private fun updateCategory(shopBannerRsp: ArrayList<VipCategory>) {

        addLocalCategory(shopBannerRsp)
        mGoodsPagerAdapter.update(shopBannerRsp)
    }

    private fun addLocalCategory(categoryBeanList: ArrayList<VipCategory>) {
        if (categoryBeanList == null) {
            return
        }
//        val categoryGroup = VipCategory(0,0,"",CATEGORY_GROUP,0,0,"")
//        categoryBeanList.add(0, categoryGroup)
//        val categoryFlashSale = VipCategory(0,0,"",CATEGORY_FLASH_SALE,0,0,"")
//        categoryBeanList.add(0, categoryFlashSale)
        val categoryAll = VipCategory(0, 0, "", CATEGORY_ALL, 0, 0, "")
        categoryBeanList.add(0, categoryAll)
    }

    fun loadCategory() {
        NetworkFactory.createService(VipService::class.java)
                .loadCategory()
                .compose(NetworkObservableTransformer())
                .subscribe(object : DefaultNetworkObserverList<VipCategory>(false) {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        AppUtils.showMessage(e.message)
                    }


                    override fun onResponse(response: MutableList<VipCategory>?) {
                        updateCategory((response as ArrayList<VipCategory>?)!!)
                    }


                })

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_vip_good
    }

    override fun middleTitle(): String {
        return "VIP商品采购"
    }

    fun setCartCount(count: Int) {
        mBinding.tvRedDot.visibility = if (count > 0) View.VISIBLE else View.GONE
        mBinding.tvRedDot.text = count.toString()
    }

    override fun onChange(count: Int) {
        setCartCount(count)
    }

}
