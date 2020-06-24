package com.asia.paint.base.network.api;


import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.FlashGoodsRsp;
import com.asia.paint.base.network.bean.GoodsRsp;
import com.asia.paint.base.network.bean.IndexBaseRsp;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.PromotionComposeRsp;
import com.asia.paint.base.network.bean.PromotionGroupPintuan;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.network.bean.ShopGoodsDetailRsp;
import com.asia.paint.base.network.core.BaseListRespose;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-10.
 */
@NetworkUrl(Constants.URL)
public interface ShopService {

	@FormUrlEncoded
	@POST("api/goods/index")
	Observable<BaseRsp<GoodsRsp>> loadShopGoods(@Field("p") int page, @Field("order") String order, @Field("sort") String sort,
												@Field("keyword") String keyword, @Field("cate_id") Integer cate_id, @Field("ids") Integer ids, @Field("pagesize") Integer pagesize);

	@POST("api/index/index")
	Observable<BaseRsp<ShopBannerRsp>> loadBanner();

	/**
	 * 商品详情
	 */
	@FormUrlEncoded
	@POST("api/goods/detail")
	Observable<BaseRsp<ShopGoodsDetailRsp>> loadShopGoodsDetail(@Field("goods_id") int goods_id);

	/**
	 * 拼团信息
	 */
	@FormUrlEncoded
	@POST("api/promotion/group_pintuan")
	Observable<BaseListRespose<PromotionGroupPintuan>> loadPromotionGroupPintuan(@Field("groupbuy_id") int groupbuy_id);

	/**
	 * 团购商品详情
	 */
	@FormUrlEncoded
	@POST("api/promotion/group_detail")
	Observable<BaseRsp<ShopGoodsDetailRsp>> loadGroupDetail(@Field("goods_id") Integer goods_id, @Field("groupbuy_id") int groupbuy_id);

	/**
	 * 组合列表(商品首页)
	 */
	@POST("api/promotion/compose")
	Observable<BaseRsp<PromotionComposeRsp>> loadPromotionCompose();

	/**
	 * 后台配置图片(商品首页)
	 */
	@POST("api/index/base")
	Observable<BaseRsp<IndexBaseRsp>> loadIndexBase();

	/**
	 * 组合列表(商品详情)
	 */
	@FormUrlEncoded
	@POST("api/promotion/compose")
	Observable<BaseRsp<PromotionComposeRsp>> loadPromotionCompose(@Field("goods_id") int goods_id);

	/**
	 * 秒杀
	 */
	@FormUrlEncoded
	@POST("api/promotion/spike")
	Observable<BaseRsp<FlashGoodsRsp>> loadFlashSaleGoods(@Field("p") int page);

	/**
	 * 拼团列表
	 */
	@FormUrlEncoded
	@POST("api/promotion/groupbuy")
	Observable<BaseRsp<FlashGoodsRsp>> loadGroupByGoods(@Field("p") int page);

	/**
	 * 秒杀详情
	 */
	@FormUrlEncoded
	@POST("api/promotion/spike_detail")
	Observable<BaseRsp<ShopGoodsDetailRsp>> loadSpikeGoodsDetail(@Field("goods_id") int goods_id, @Field("id") int spike_id);


	/**
	 * 商品分类
	 *
	 * @param pid 上级id
	 */
	@FormUrlEncoded
	@POST("api/index/category")
	Observable<BaseRsp<LoginRsp>> loadShopGoodsCategory(@Field("pid") int pid);

	/**
	 * 陈列模块
	 */
	@GET("api/index/exhibit")
	Observable<BaseRsp<LoginRsp>> loadShopGoodsExhibit();

}
