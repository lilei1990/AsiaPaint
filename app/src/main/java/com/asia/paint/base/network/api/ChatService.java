package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CustomerServiceRsp;
import com.asia.paint.base.network.bean.TextMessageRsp;
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
public interface ChatService {

    int CHAT_TEXT = 1;
    int CHAT_IMAGE = 2;

    @FormUrlEncoded
    @POST("api/chat/send")
    Observable<BaseRsp<TextMessageRsp>> sendMsg(@Field("type") int type, @Field("content") String content, @Field("image") String image);

    @FormUrlEncoded
    @POST("api/chat/getchat")
    Observable<BaseRsp<CustomerServiceRsp>> getMsg(@Field("lasttime") Long lasttime, @Field("p") int p);

    @FormUrlEncoded
    @POST("api/chat/history")
    Observable<BaseRsp<CustomerServiceRsp>> getHistoryMsg(@Field("keyword") String keyword, @Field("p") int p);
}
