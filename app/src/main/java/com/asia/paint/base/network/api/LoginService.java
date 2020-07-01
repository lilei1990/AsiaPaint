package com.asia.paint.base.network.api;


import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CodeBean;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-10.
 */
@NetworkUrl(Constants.URL)
public interface LoginService {

    @FormUrlEncoded
    @POST("api/sms/send")
    Observable<BaseRsp<String>> getSmsCode(@Field("mobile") String mobile, @Field("event") String event);

    @FormUrlEncoded
    @POST("api/user/resetpwd")
    Observable<BaseRsp<String>> resetPwd(@Field("mobile") String mobile, @Field("newpassword") String password, @Field("captcha") String smsCode);

    @FormUrlEncoded
    @POST("api/user/mobilelogin")
    Observable<BaseRsp<LoginRsp>> loginViaPhone(@Field("mobile") String mobile, @Field("captcha") String smsCode, @Field("code") String code,
            @Field("openid") String openid);

    @FormUrlEncoded
    @POST("api/user/login")
    Observable<BaseRsp<LoginRsp>> loginViaPwd(@Field("mobile") String mobile, @Field("password") String password, @Field("code") String code);

    @FormUrlEncoded
    @POST("api/index/third")
    Observable<BaseRsp<UserInfo>> loginViaWeiXin(@Field("openid") String openid);

    @FormUrlEncoded
    @POST("api/user/bind_wx")
    Observable<BaseRsp<String>> bindWeiXin(@Field("token") String token, @Field("openid") String openid);

    @FormUrlEncoded
    @POST("api/user/register")
    Observable<BaseRsp<String>> register(@Field("mobile") String mobile, @Field("password") String password, @Field("openid") String openid);

    @FormUrlEncoded
    @POST("api/user/changemobile")
    Observable<BaseRsp<String>> bindNewPhone(@Field("mobile") String mobile, @Field("captcha") String smsCode);

    @FormUrlEncoded
    @POST("api/sms/deluser")
    Observable<BaseRsp<String>> unsubscribeAccount(@Field("mobile") String mobile, @Field("captcha") String smsCode);

    //获取传递的推荐码
    @FormUrlEncoded
    @POST("api/index/qrcode")
    Observable<BaseRsp<CodeBean>> getCode(@Field("url") String url);
}
