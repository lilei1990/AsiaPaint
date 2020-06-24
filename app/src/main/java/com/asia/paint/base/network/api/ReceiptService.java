package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.ReceiptRsp;
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
public interface ReceiptService {

    int COMPANY = 1;
    int PERSONAL = 2;

    int TYPE_ELECTRONIC = 1;
    int TYPE_PAPER = 2;

    @FormUrlEncoded
    @POST("api/user/my_receipt")
    Observable<BaseRsp<ReceiptRsp>> loadReceipt(@Field("p") int page);

    /**
     * receipt:发票类型【1电子，2纸质】
     * type:1企业2个人或政府单位
     * is_default:是否默认【1是0否】
     */
    @FormUrlEncoded
    @POST("api/user/add_receipt")
    Observable<BaseRsp<Integer>> addReceipt(@Field("receipt") int receipt, @Field("title") String title, @Field("type") int type,
            @Field("sn") String sn, @Field("bank") String bank, @Field("bank_sn") String bank_sn, @Field("address") String address,
            @Field("company_tel") String company_tel, @Field("tel") String phone, @Field("email") String email, @Field("is_default") int is_default);

    /**
     * receipt:发票类型【1电子，2纸质】
     * type:1企业2个人或政府单位
     * is_default:是否默认【1是0否】
     */
    @FormUrlEncoded
    @POST("api/user/edit_receipt")
    Observable<BaseRsp<String>> editReceipt(@Field("id") int id, @Field("receipt") int receipt, @Field("title") String title, @Field("type") int type,
            @Field("sn") String sn, @Field("bank") String bank, @Field("bank_sn") String bank_sn, @Field("address") String address,
            @Field("company_tel") String company_tel, @Field("tel") String tel, @Field("email") String email, @Field("is_default") int is_default);

    @FormUrlEncoded
    @POST("api/user/del_receipt")
    Observable<BaseRsp<String>> delReceipt(@Field("id") int id);
}
