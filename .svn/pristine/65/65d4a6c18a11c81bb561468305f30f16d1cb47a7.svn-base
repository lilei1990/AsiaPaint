package com.asia.paint.base.network.api;


import com.asia.paint.base.network.bean.WeiXinInfo;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author by chenhong14 on 2019-11-10.
 */
@NetworkUrl("https://api.weixin.qq.com")
public interface WeiXinService {


    @GET("sns/oauth2/access_token")
    Observable<WeiXinInfo> queryWeiXinInfo(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code
            , @Query("grant_type") String grant_type);

    @GET("sns/oauth2/refresh_token")
    Observable<WeiXinInfo> refreshWeiXinToken(@Query("appid") String appid, @Query("refresh_token") String refresh_token,@Query("grant_type") String grant_type);
}
