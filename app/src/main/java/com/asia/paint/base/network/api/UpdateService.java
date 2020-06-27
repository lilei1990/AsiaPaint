package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.TrainCategoryRsp;
import com.asia.paint.base.network.bean.TrainDetail;
import com.asia.paint.base.network.bean.TrainRsp;
import com.asia.paint.base.network.bean.UpdateRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2020/6/25.
 */
@NetworkUrl(Constants.URL)
public interface UpdateService {


    @POST("api/index/getversion")
    Observable<BaseRsp<UpdateRsp>> UpdateVersion();


}