package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.MessageRsp;
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
public interface MessageService {


    @FormUrlEncoded
    @POST("api/index/my_msg")
    Observable<BaseRsp<MessageRsp>> queryMessage(@Field("p") int page);

    @FormUrlEncoded
    @POST("api/index/detail_msg")
    Observable<BaseRsp<String>> queryMessageDetail(@Field("id") int id);

    @FormUrlEncoded
    @POST("api/index/del_msg")
    Observable<BaseRsp<String>> delMessage(@Field("id") int id);
}
