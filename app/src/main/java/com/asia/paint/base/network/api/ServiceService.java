package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.ScheduleServiceRsp;
import com.asia.paint.base.network.bean.ServiceRsp;
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
public interface ServiceService {

    /**
     * @param point_lng 经度
     * @param point_lat 纬度
     * @param order     排序方式【目前页面无选项】
     * @param keyword   区域选择
     */
    @FormUrlEncoded
    @POST("api/poster/shop_list")
    Observable<BaseRsp<ServiceRsp>> loadService(@Field("point_lng") double point_lng, @Field("point_lat") double point_lat
            , @Field("order") String order, @Field("keyword") String keyword, @Field("p") int page);


    @FormUrlEncoded
    @POST("api/poster/add_subscribe")
    Observable<BaseRsp<String>> scheduleService(@Field("sid") int sid, @Field("name") String name
            , @Field("tel") String tel, @Field("service_time") String service_time, @Field("desc") String desc);

    int SCHEDULE_CHECK = 1;
    int SCHEDULE_SURE = 2;
    int SCHEDULE_DENY = 3;
    int SCHEDULE_CANCEL = 4;

    /**
     * 状态【1待审核，2已确定，3拒绝，4取消】
     */
    @FormUrlEncoded
    @POST("api/poster/my_subscribe")
    Observable<BaseRsp<ScheduleServiceRsp>> loadScheduleService(@Field("status") String status, @Field("p") int page);

    @FormUrlEncoded
    @POST("api/poster/subscribe_cancel")
    Observable<BaseRsp<String>> cancelScheduleService(@Field("id") int id);

}
