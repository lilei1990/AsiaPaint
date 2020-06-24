package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.ArticleDataRsp;
import com.asia.paint.base.network.bean.GetUserPost;
import com.asia.paint.base.network.bean.IndexNewsDetailRsp;
import com.asia.paint.base.network.bean.MineDataRsp;
import com.asia.paint.base.network.bean.RechargeDetailRsp;
import com.asia.paint.base.network.bean.UserDetail;
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
public interface UserService {

	/**
	 * 家装宝典详情
	 */
	@FormUrlEncoded
	@POST("api/index/news_detail")
	Observable<BaseRsp<IndexNewsDetailRsp>> loadIndexNewsDetail(@Field("id") String id);

	/**
	 * 职位列表
	 */
	@POST("api/seller/getuserpost")
	Observable<BaseListRespose<GetUserPost>> loadGetuserpost();

	@FormUrlEncoded
	@POST("api/index/article")
	Observable<BaseRsp<ArticleDataRsp>> loadArticleData(@Field("title") String title);

	@POST("api/user/index")
	Observable<BaseRsp<MineDataRsp>> loadMineData();

	@POST("api/user/detail")
	Observable<BaseRsp<UserDetail>> loadUserInfoDetail();

	@FormUrlEncoded
	@POST("api/user/profile")
	Observable<BaseRsp<String>> editUserInfo(@Field("nickname") String nickname, @Field("sex") int sex,
											 @Field("birthday") String birthday, @Field("avatar") String avatar);

	@FormUrlEncoded
	@POST("api/money/my_money")
	Observable<BaseRsp<RechargeDetailRsp>> queryMoneyDetail(@Field("p") int p);
}
