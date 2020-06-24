package com.asia.paint.base.network.api;


import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CommentRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-10.
 */
@NetworkUrl(Constants.URL)
public interface CommentService {

    int ALL = 0;
    int IMG = 1;
    int NEW = 2;

    /**
     * 评论[0,全部，1有图，2最新]
     */
    @FormUrlEncoded
    @POST("api/goods/comment")
    Observable<BaseRsp<CommentRsp>> loadGoodsComment(@Field("goods_id") int goods_id, @Field("status") int status, @Field("p") int page);

    /**
     * 评价商品
     * 是否匿名【1是，0否】
     */
    @FormUrlEncoded
    @POST("api/order/add_comment")
    Observable<BaseRsp<String>> addComment(@Field("rec_id") int rec_id, @Field("comment") String comment,
            @Field("score") float score, @Field("images") String images, @Field("video") String video,
            @Field("is_hide") int is_hide);
}
