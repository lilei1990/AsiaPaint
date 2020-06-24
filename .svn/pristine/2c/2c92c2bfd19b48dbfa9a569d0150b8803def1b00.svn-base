package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.FavoritesRsp;
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
public interface CollectService {


    @FormUrlEncoded
    @POST("api/user/add_collect")
    Observable<BaseRsp<String>> addCollect(@Field("goods_id") int goods_id);

    @FormUrlEncoded
    @POST("api/user/my_collect")
    Observable<BaseRsp<FavoritesRsp>> queryCollect(@Field("p") int page);

    @FormUrlEncoded
    @POST("api/user/del_collect")
    Observable<BaseRsp<String>> delCollect(@Field("goods_id") int goods_id);

    @FormUrlEncoded
    @POST("api/user/del_collect")
    Observable<BaseRsp<String>> delCollect(@Field("goods_id") String goods_id);

}
