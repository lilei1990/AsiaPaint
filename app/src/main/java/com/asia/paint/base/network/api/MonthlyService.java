package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.MonthlyDetail;
import com.asia.paint.base.network.bean.MonthlyRsp;
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
public interface MonthlyService {


    @FormUrlEncoded
    @POST("api/SELLER/monthly")
    Observable<BaseRsp<MonthlyRsp>> loadMonthly(@Field("keyword") String keyword, @Field("p") int page);


    @FormUrlEncoded
    @POST("api/SELLER/detail_monthly")
    Observable<BaseRsp<MonthlyDetail>> queryMonthlyDetail(@Field("id") int id);
}
