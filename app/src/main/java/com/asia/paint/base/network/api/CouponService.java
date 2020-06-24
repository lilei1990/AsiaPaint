package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CouponRsp;
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
public interface CouponService {
    /**
     * 状态【1未使用，2过期，3已用过，0全部】
     */
    int TYPE_ALL = 0;
    int TYPE_USABLE = 1;
    int TYPE_OVERDUE = 2;
    int TYPE_USED = 3;
    int TYPE_GET = 1000;

    @FormUrlEncoded
    @POST("api/coupon/my_coupon")
    Observable<BaseRsp<CouponRsp>> queryCoupon(@Field("p") int page, @Field("status") int type);

    @FormUrlEncoded
    @POST("api/coupon/del_coupon")
    Observable<BaseRsp<String>> delCollect(@Field("bonus_id") String bonus_id);

    //等级【1-3级】暂无效果后续处理！
    @FormUrlEncoded
    @POST("api/coupon/index")
    Observable<BaseRsp<CouponRsp>> loadCoupon(@Field("p") int page, @Field("status") Integer type);

    @FormUrlEncoded
    @POST("api/coupon/add_coupon")
    Observable<BaseRsp<String>> getCoupon(@Field("type_id") int type_id);

}
