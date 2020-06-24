package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.SellerInfoRsp;
import com.asia.paint.base.network.bean.UserDetail;
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
public interface SellerService {

	int TASK_ALL = 0;
	int TASK_TODO = 1;
	int TASK_DONE = 2;
	int TASK_OVERDUE = 3;//已结束过期

	int TYPE_ADD_NEW = 1;//拉新
	int TYPE_SALE = 2;//销售

	@POST("api/SELLER/index")
	Observable<BaseRsp<SellerInfoRsp>> loadSellerInfo();

	@POST("api/SELLER/detail")
	Observable<BaseRsp<UserDetail>> loadSellerInfoDetail();

	@FormUrlEncoded
	@POST("api/SELLER/apply")
	Observable<BaseRsp<String>> applySeller(@Field("address") String address, @Field("address_name") String address_name, @Field("idcard") String idCard, @Field("name") String name, @Field("post") String post);
}
