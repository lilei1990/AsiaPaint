package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CashAccountRsp;
import com.asia.paint.base.network.bean.CashInfoRsp;
import com.asia.paint.base.network.bean.CashRecordRsp;
import com.asia.paint.base.network.bean.TaxRsp;
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
public interface CashService {

    int ZHI_FU_BAO = 1;
    int BANK = 2;

    @FormUrlEncoded
    @POST("api/seller/add_account")
    Observable<BaseRsp<String>> addCashAccount(@Field("type") int type, @Field("account") String account,
            @Field("name") String name, @Field("bank") String bank, @Field("bank_name") String bank_name,@Field("idcard") String idcard,@Field("tel") String tel);

    @FormUrlEncoded
    @POST("api/seller/edit_account")
    Observable<BaseRsp<String>> editCashAccount(@Field("id") int id, @Field("type") int type, @Field("account") String account,
            @Field("name") String name, @Field("bank") String bank, @Field("bank_name") String bank_name,@Field("idcard") String idcard,@Field("tel") String tel);

    @FormUrlEncoded
    @POST("api/sms/check")
    Observable<BaseRsp<String>> checkSmsCode(@Field("mobile") String mobile, @Field("event") String event, @Field("captcha") String captcha);

    @FormUrlEncoded
    @POST("api/seller/add_tixian_log")
    Observable<BaseRsp<String>> cash(@Field("score") String score, @Field("type") int type, @Field("aid") int account,
            @Field("mode") int mode, @Field("payword") String payword, @Field("company") String company);

    @FormUrlEncoded
    @POST("api/seller/up_tixian_img")
    Observable<BaseRsp<String>> uploadReceipt(@Field("id") int id, @Field("invoice_type") int invoice_type, @Field("invoice_price") String invoice_price,
            @Field("express_sn") String express_sn, @Field("image") String image);


    /**
     * 获取提现信息
     */
    @FormUrlEncoded
    @POST("api/seller/detail_tixian")
    Observable<BaseRsp<String>> cashDetail(@Field("id") int id);

    /**
     * 获取提现信息
     */
    @FormUrlEncoded
    @POST("api/seller/my_tixian_log")
    Observable<BaseRsp<CashRecordRsp>> cashRecord(@Field("p") int page);

    /**
     * 获取提现信息
     */
    @FormUrlEncoded
    @POST("api/seller/del_account")
    Observable<BaseRsp<String>> delCashAccount(@Field("id") int id);

    /**
     * 获取提现信息
     */
    @FormUrlEncoded
    @POST("api/seller/tixian")
    Observable<BaseRsp<CashInfoRsp>> queryCashInfo(@Field("type") int type);

    /**
     * 获取提现信息
     */
    @POST("api/seller/my_account")
    Observable<BaseRsp<CashAccountRsp>> queryMyAccount();

    /**
     * 获取提现信息
     */
    @FormUrlEncoded
    @POST("api/seller/getpoint")
    Observable<BaseRsp<TaxRsp>> getTax(@Field("money") String money);

}
