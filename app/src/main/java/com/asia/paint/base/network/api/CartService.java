package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CartCountRsp;
import com.asia.paint.base.network.bean.CartGoodsRsp;
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
public interface CartService {

    @POST("api/cart/index")
    Observable<BaseRsp<CartGoodsRsp>> loadCartGoods();


    @FormUrlEncoded
    @POST("api/cart/add")
    Observable<BaseRsp<String>> addCart(@Field("spec_id") int spec_id, @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("api/cart/del")
    Observable<BaseRsp<String>> deleteCart(@Field("rec_id") String rec_id);

    @POST("api/cart/dels")
    Observable<BaseRsp<String>> deleteAllCart();

    @FormUrlEncoded
    @POST("api/cart/update")
    Observable<BaseRsp<String>> updateCart(@Field("rec_id") int rec_id, @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("api/cart/is_check")
    Observable<BaseRsp<String>> checkToCart(@Field("rec_id") int rec_id, @Field("status") int status);

    @FormUrlEncoded
    @POST("api/cart/all_check")
    Observable<BaseRsp<String>> checkAllToCart(@Field("status") int status);

    @POST("api/cart/cartnum")
    Observable<BaseRsp<CartCountRsp>> queryCartCount();
}
