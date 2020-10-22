package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.data.VipGoodTask;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-19.
 */
@NetworkUrl(Constants.URL)
public interface VipService {

    /**
     * VIP任务
     */
    @POST("api/vip/apply_vip_task")
    Observable<BaseRsp<VipGoodTask>> loadApplyVipTask();


}
