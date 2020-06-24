package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.AddressRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-23.
 */
@NetworkUrl(Constants.URL)
public interface AddressService {


    @FormUrlEncoded
    @POST("api/user/add_address")
    Observable<BaseRsp<String>> addAddress(@Field("consignee") String consignee, @Field("tel") String tel,
            @Field("address") String area, @Field("address_name") String address);

    @FormUrlEncoded
    @POST("api/user/edit_address")
    Observable<BaseRsp<String>> editAddress(@Field("address_id") int address_id, @Field("consignee") String consignee,
            @Field("tel") String tel, @Field("address") String address, @Field("address_name") String address_name);

    @FormUrlEncoded
    @POST("api/user/my_address")
    Observable<BaseRsp<AddressRsp>> queryAddress(@Field("p") int page);

    @FormUrlEncoded
    @POST("api/user/del_address")
    Observable<BaseRsp<String>> delAddress(@Field("address_id") int address_id);

    @FormUrlEncoded
    @POST("api/user/is_default")
    Observable<BaseRsp<String>> addDefaultAddress(@Field("address_id") int address_id);
}
