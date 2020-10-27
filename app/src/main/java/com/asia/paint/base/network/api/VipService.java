package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.BaseListRsp;
import com.asia.paint.base.network.bean.GoodsRsp;
import com.asia.paint.base.network.bean.ShopBannerRsp;
import com.asia.paint.base.network.core.BaseListRespose;
import com.asia.paint.base.network.data.VipCategory;
import com.asia.paint.base.network.data.VipGoodTask;
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
public interface VipService {

    /**
     * VIP任务
     */
    @POST("api/vip/apply_vip_task")
    Observable<BaseRsp<VipGoodTask>> loadApplyVipTask();
    /**
     *     2.5 商品分类
     */

    @POST("api/index/category")
    Observable<BaseListRespose<VipCategory>> loadCategory();

    /**
     * vip物品列表
     * @param page 页码
     * @param order 方式【goods_id：默认，sales销量，price价格，推荐：is_best】
     * @param sort 排序【asc升，desc：降】
     * @param keyword 搜索
     * @param cate_id 物品分类
     * @param ids 废弃不用
     * @param pagesize 页数量
     * @return
     */
    @FormUrlEncoded
    @POST("api/vip/goods")
    Observable<BaseRsp<GoodsRsp>> loadVipShopGoods(@Field("p") int page, @Field("order") String order, @Field("sort") String sort,
                                                   @Field("keyword") String keyword, @Field("cate_id") Integer cate_id, @Field("ids") Integer ids, @Field("pagesize") Integer pagesize);
}
