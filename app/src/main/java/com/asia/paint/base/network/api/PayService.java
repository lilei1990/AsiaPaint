package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.PayOrderInfo;
import com.asia.paint.base.network.bean.YinlianBean;
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
public interface PayService {


    @FormUrlEncoded
    @POST("api/pay/balance")
    Observable<BaseRsp<String>> payViaBalance(@Field("order_id") int order_id, @Field("payword") String payword);


    @FormUrlEncoded
    @POST("api/user/resetpaypwd")
    Observable<BaseRsp<String>> resetPayPwd(@Field("captcha") String captcha, @Field("newpassword") String newpassword);

    @FormUrlEncoded
    @POST("api/user/edit_paypwd")
    Observable<BaseRsp<String>> editPayPwd(@Field("oldpassword") String oldpassword, @Field("newpassword") String newpassword);


    int PAY_ZHI_FU_BAO = 1;
    int PAY_WEI_XIN = 2;
    int PAY_BANK = 3;

    /**
     * 支付类型【1支付宝2微信3银联】
     */
    @FormUrlEncoded
    @POST("api/pay/orderinfo")
    Observable<BaseRsp<PayOrderInfo>> queryPayOrderInfo(@Field("order_id") int order_id, @Field("type") int type);

    /**
     * 获取支付宝订单
     */
    @FormUrlEncoded
    @POST("api/pay/orderinfo")
    Observable<BaseRsp<String>> queryZhiFuBaoOrderInfo(@Field("order_id") int order_id, @Field("type") int type);


    /**
     * 银联获取订单
     */
    @FormUrlEncoded
    @POST("api/pay/orderinfo")
    Observable<BaseRsp<YinlianBean>> queryYinlianOrderInfo(@Field("order_id") int order_id, @Field("type") int type);
}
