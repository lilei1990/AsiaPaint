package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.RichTextRsp;
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
public interface OtherService {

    String USER_ITEMS = "用户协议";
    String ABOUT_ASIA = "关于亚士漆";
    String PRIVACY_POLICY = "隐私政策";
    String SELL_RULE = "分销规则";
    String SCORE_RULE = "积分说明";
    String TAX_RULE = "税费说明";
    String CASH_ITEMS = "提现协议";
    String PINTUAN_RULE = "拼团规则";

    @FormUrlEncoded
    @POST("api/index/article")
    Observable<BaseRsp<RichTextRsp>> loadRichText(@Field("title") String title);

}
