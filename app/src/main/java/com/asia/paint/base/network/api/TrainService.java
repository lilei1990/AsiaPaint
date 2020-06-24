package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.TrainCategoryRsp;
import com.asia.paint.base.network.bean.TrainDetail;
import com.asia.paint.base.network.bean.TrainRsp;
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
public interface TrainService {


    @POST("api/SELLER/train_cate")
    Observable<BaseRsp<TrainCategoryRsp>> loadTrainCategory();


    @FormUrlEncoded
    @POST("api/SELLER/train")
    Observable<BaseRsp<TrainRsp>> queryTrain(@Field("cate_id") int cateId, @Field("keyword") String keyword, @Field("p") int p);

    @FormUrlEncoded
    @POST("api/SELLER/detail_train")
    Observable<BaseRsp<TrainDetail>> queryTrainDetail(@Field("id") int id);

}
