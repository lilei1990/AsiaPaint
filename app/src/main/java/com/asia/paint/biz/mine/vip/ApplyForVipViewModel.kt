package com.asia.paint.biz.mine.vip

import com.asia.paint.base.container.BaseViewModel
import com.asia.paint.base.network.api.UserService
import com.asia.paint.base.network.bean.GetUserPost
import com.asia.paint.base.network.core.DefaultNetworkObserverList
import com.asia.paint.network.NetworkFactory
import com.asia.paint.network.NetworkObservableTransformer
import com.asia.paint.utils.callback.CallbackDateList
import com.asia.paint.utils.utils.AppUtils
import com.smarttop.library.utils.LogUtil
import io.reactivex.disposables.Disposable

/**
 * @author by chenhong14 on 2019-11-28.
 */
class ApplyForVipViewModel : BaseViewModel() {
    private val mGetUserPostRsp = CallbackDateList<GetUserPost>()
    fun loadGetuserpost(): CallbackDateList<GetUserPost> {
        NetworkFactory.createService(UserService::class.java)
                .loadGetuserpost()
                .compose(NetworkObservableTransformer())
                .subscribe(object : DefaultNetworkObserverList<GetUserPost?>(false) {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }


                    override fun onError(e: Throwable) {
                        super.onError(e)
                        LogUtil.e("拼团：", e.message)
                        AppUtils.showMessage(e.message)
                    }

                    override fun onResponse(response: MutableList<GetUserPost?>?) {
                        mGetUserPostRsp.data = response
                    }
                })
        return mGetUserPostRsp
    }


}