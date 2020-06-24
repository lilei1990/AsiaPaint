package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.ScoreRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-19.
 */
@NetworkUrl(Constants.URL)
public interface ScoreService {


    @POST("api/seller/add_score")
    Observable<BaseRsp<String>> addTestScore();

    @FormUrlEncoded
    @POST("api/seller/my_score")
    Observable<BaseRsp<ScoreRsp>> queryScoreDetail(@Field("p") int page);
}
