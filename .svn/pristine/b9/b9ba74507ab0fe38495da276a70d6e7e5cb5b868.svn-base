package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.DecorationRsp;
import com.asia.paint.base.network.bean.ZoneRsp;
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
public interface ZoneService {


    @FormUrlEncoded
    @POST("api/index/color")
    Observable<BaseRsp<ZoneRsp>> loadZoneInfo(@Field("p") int page);

    @FormUrlEncoded
    @POST("api/index/news")
    Observable<BaseRsp<DecorationRsp>> loadNewsInfo(@Field("p") int page);
}
