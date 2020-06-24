package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.StaffInfoRsp;
import com.asia.paint.base.network.bean.StaffRsp;
import com.asia.paint.base.network.bean.StaffSaleDataRsp;
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
public interface StaffService {

    int TYPE_STAFF = 1;
    int TYPE_SUB_STAFF = 2;

    //状态【1一级，2二级】
    @FormUrlEncoded
    @POST("api/seller/my_user")
    Observable<BaseRsp<StaffRsp>> loadStaff(@Field("status") int status);


    @FormUrlEncoded
    @POST("api/seller/user_detail")
    Observable<BaseRsp<StaffInfoRsp>> queryStaffDetail(@Field("p") int page, @Field("user_id") int id);

    @FormUrlEncoded
    @POST("api/seller/sale_data")
    Observable<BaseRsp<StaffSaleDataRsp>> queryStaffSaleData(@Field("uid") Integer id, @Field("strtime") String startTime, @Field("endtime") String endTime);
}
