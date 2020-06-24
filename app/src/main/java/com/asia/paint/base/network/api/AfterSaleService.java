package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.AfterSaleListRsp;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.network.bean.ReturnDetail;
import com.asia.paint.base.network.bean.ZoneRsp;
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
public interface AfterSaleService {

    /**
     * 状态 1申请2通过 -1拒绝，11取消 ,不传为all
     */
    @FormUrlEncoded
    @POST("api/order/return_list")
    Observable<BaseRsp<AfterSaleListRsp>> loadReturnList(@Field("p") int page, @Field("status") Integer status);

    @FormUrlEncoded
    @POST("api/order/return_goods_detail")
    Observable<BaseRsp<ReturnDetail>> queryReturnDetail(@Field("rec_id") int rec_id);

    /**
     * 售后操作
     * express_company,物流公司 type=1 or 3，status=3传递
     * express_sn,物流编号 type=1 or 3，status=3传递
     */
    @FormUrlEncoded
    @POST("api/order/operation")
    Observable<BaseRsp<String>> afterSaleOperation(@Field("rec_id") int rec_id, @Field("status") int status, @Field("express_company") String express_company
            , @Field("express_sn") String express_sn);

    /**
     * 【1退货退款2仅退款3换货】
     * num ,数量，最大值=goods_numbers
     * money,金额，最大值=back_price
     */
    @FormUrlEncoded
    @POST("api/order/add_return_goods")
    Observable<BaseRsp<String>> applyAfterSale(@Field("rec_id") int rec_id,@Field("type") int type, @Field("reson") String reason
            , @Field("num") int num, @Field("money") String money, @Field("desc") String desc, @Field("images") String images);


    @FormUrlEncoded
    @POST("api/order/express_company")
    Observable<BaseRsp<String>> deliveryCompany();

}
