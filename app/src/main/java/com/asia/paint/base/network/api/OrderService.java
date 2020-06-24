package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.CreateOrderRsp;
import com.asia.paint.base.network.bean.MyPinTuan;
import com.asia.paint.base.network.bean.OrderCommentRsp;
import com.asia.paint.base.network.bean.OrderInfoRsp;
import com.asia.paint.base.network.bean.OrderMineRsp;
import com.asia.paint.base.network.bean.OrderReturnDetailRsp;
import com.asia.paint.base.network.bean.PinTuanDetail;
import com.asia.paint.base.network.core.BaseListRespose;
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
public interface OrderService {
	/**
	 * 【0:购物车,1:组合购,2:团购,3:秒杀,4:分销任务,5:直接购买】,其他的后续约束
	 */
	int CART = 0;
	int PROMOTION = 1;
	int GROUP = 2;
	int SPIKE = 3;
	int APPLY_TASK = 4;
	int BUY = 5;

	/**
	 * 查询下单信息
	 */
	@FormUrlEncoded
	@POST("api/order/index")
	Observable<BaseRsp<OrderInfoRsp>> queryOrderInfo(@Field("flow_type") int flow_type, @Field("address_id") Integer address_id);

	@FormUrlEncoded
	@POST("api/order/done")
	Observable<BaseRsp<CreateOrderRsp>> createOrder(@Field("flow_type") int flow_type, @Field("address_id") int address_id,
													@Field("bonus_id") int bonus_id, @Field("description") String description, @Field("score") int score,
													@Field("receipt") int receipt);

	@FormUrlEncoded
	@POST("api/order/done")
	Observable<BaseRsp<CreateOrderRsp>> createOrder(@Field("flow_type") int flow_type, @Field("address_id") int address_id,
													@Field("bonus_id") int bonus_id, @Field("description") String description, @Field("score") int score);


	int ORDER_ALL = 0;
	int ORDER_PAY = 10;
	int ORDER_DELIVERY = 20;
	int ORDER_RECEIVE = 30;
	int ORDER_DONE = 40;
	int ORDER_CANCEL = 11;
	//没有带评价的状态。评价单独一个接口
	int ORDER_COMMENT = -1000;

	/**
	 * 订单列表【10:待支付，20:待发货(已支付)，30:待收货,40:已完成,11已取消，0全部】
	 * 拉取我的订单
	 */
	@FormUrlEncoded
	@POST("api/order/my_order")
	Observable<BaseRsp<OrderMineRsp>> loadMyOrder(@Field("order_status") int order_status, @Field("p") int page);


	@FormUrlEncoded
	@POST("api/order/cancel")
	Observable<BaseRsp<String>> cancelOrder(@Field("order_id") int order_id);

	/**
	 * 目前仅支持取消订单才能删除
	 */
	@FormUrlEncoded
	@POST("api/order/delete")
	Observable<BaseRsp<String>> delOrder(@Field("order_id") int order_id);

	/**
	 * 订单详情
	 */
	@FormUrlEncoded
	@POST("api/order/detail")
	Observable<BaseRsp<OrderReturnDetailRsp>> queryOrderDetail(@Field("order_id") int order_id);

	/**
	 * 确认收货
	 */
	@FormUrlEncoded
	@POST("api/order/receive")
	Observable<BaseRsp<String>> orderIsReceive(@Field("order_id") int order_id);


	/**
	 * 评价状态【0:未评价，1:已评价】
	 */
	@FormUrlEncoded
	@POST("api/order/goods_comment")
	Observable<BaseRsp<OrderCommentRsp>> commentOrder(@Field("p") int page, @Field("comment_status") int comment_status);

	/**
	 * 直接购买
	 */
	@FormUrlEncoded
	@POST("api/order/buy")
	Observable<BaseRsp<String>> directBuy(@Field("spec_id") int spec_id, @Field("quantity") int quantity);

	/**
	 * 分销任务
	 */
	@FormUrlEncoded
	@POST("api/promotion/buy")
	Observable<BaseRsp<String>> promotionBuy(@Field("flow_type") int flow_type, @Field("id") int id);

	/**
	 * 组合购
	 */
	@FormUrlEncoded
	@POST("api/promotion/buy")
	Observable<BaseRsp<String>> promotionBuy(@Field("flow_type") int flow_type, @Field("id") int id, @Field("quantity") int quantity);

	/**
	 * 团购
	 */
	@FormUrlEncoded
	@POST("api/promotion/buy")
	Observable<BaseRsp<String>> promotionBuy(@Field("flow_type") int flow_type, @Field("id") int id, @Field("quantity") int quantity, @Field("spec_id") int spec_id);


	int PINTUAN_ALL = 0;
	int PINTUAN_UNDERWAY = 3;
	int PINTUAN_SUCCESS = 1;
	int PINTUAN_FAILED = 2;

	/**
	 * 我的拼团列表
	 */
	@FormUrlEncoded
	@POST("api/promotion/my_pintuan")
	Observable<BaseListRespose<MyPinTuan>> loadMyPintuan(@Field("p") int page, @Field("status") int status);

	/**
	 * 拼团详情
	 */
	@FormUrlEncoded
	@POST("api/promotion/pintuan_detail")
	Observable<BaseRsp<PinTuanDetail>> loadPintuanDetail(@Field("log_id") int log_id);
}
