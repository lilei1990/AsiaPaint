package com.asia.paint.biz.mine.vip

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.asia.paint.android.R
import com.asia.paint.android.databinding.ActivityApplyForVipBinding
import com.asia.paint.base.container.BaseTitleActivity
import com.asia.paint.base.network.api.OrderService
import com.asia.paint.base.network.api.VipService
import com.asia.paint.base.network.core.DefaultNetworkObserver
import com.asia.paint.base.network.data.VipGoodTask
import com.asia.paint.base.util.ImageUtils
import com.asia.paint.biz.order.checkout.OrderCheckoutActivity
import com.asia.paint.network.NetworkFactory
import com.asia.paint.network.NetworkObservableTransformer
import com.asia.paint.utils.utils.AppUtils
import com.asia.paint.utils.utils.PriceUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_apply_for_vip.*

/**
 * vip任务购买界面
 */
class ApplyForVipActivity : BaseTitleActivity<ActivityApplyForVipBinding>() {

    lateinit var mDistributionTaskCouponsAdapter:VipTaskCouponsAdapter
    lateinit var mViewModel:ApplyForVipViewModel
    lateinit var mRes:VipGoodTask
    override fun getLayoutId(): Int {
        return R.layout.activity_apply_for_vip
    }

    override fun middleTitle(): String {
        return "VIP任务"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel(ApplyForVipViewModel::class.java)
        mDistributionTaskCouponsAdapter=VipTaskCouponsAdapter(R.layout.item_distribution_tasks_coupons)
        rv_distribution_tasks_coupons.layoutManager= LinearLayoutManager(this);
        rv_distribution_tasks_coupons.adapter = mDistributionTaskCouponsAdapter
        loadVipTest()
        tv_sure.setOnClickListener { v->
//            OrderCheckoutActivity.start(this, OrderService.APPLY_TASK, mRes.goods[0].goods_id, 1)
//            startActivity(Intent(mContext, OrderActivity::class.java))
            OrderCheckoutActivity.start(this, OrderService.APPLY_VIP_TASK, 3, 1)
        }
    }

    private fun loadVipTest() {
        NetworkFactory.createService(VipService::class.java)
                .loadApplyVipTask()
                .compose(NetworkObservableTransformer())
                .subscribe(object : DefaultNetworkObserver<VipGoodTask>(false) {
                    override fun onSubscribe(d: Disposable) {
//                        addDisposable(d)
                    }


                    override fun onError(e: Throwable) {
                        super.onError(e)
                        AppUtils.showMessage(e.message)
                    }


                    override fun onResponse(res: VipGoodTask) {
                        mRes=res
                        tv_display_info.text = res.display_info
                        tv_goods_name.text = res.goods[0].goods_name
                        tv_group_number.text = "X" + res.goods[0].number.toString()
                        tv_goods_spec.text = PriceUtils.getPrice(res.goods[0].price)
                        ImageUtils.load(iv_goods_img, res.goods[0].default_image)
//                        mDistributionTaskCouponsAdapter = DistributionTaskCouponsAdapter()

                        mDistributionTaskCouponsAdapter.updateData(res.bonus)
//                        mGetUserPostRsp.setData(response)

                    }
                })
    }
}