package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.PayOrderInfo;
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
public interface MoneyService {

    int PAY_ZHI_FU_BAO = 1;
    int PAY_WEI_XIN = 2;
    int PAY_BANK = 3;

    @FormUrlEncoded
    @POST("api/money/add_money")
    Observable<BaseRsp<PayOrderInfo>> rechargeWeiXin(@Field("type") int type, @Field("money") String money);

    @FormUrlEncoded
    @POST("api/money/add_money")
    Observable<BaseRsp<String>> rechargeByZhiFuBao(@Field("type") int type, @Field("money") String money);

}
