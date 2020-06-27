package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.Post;
import com.asia.paint.base.network.bean.PostComment;
import com.asia.paint.base.network.bean.PostRsp;
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
public interface PostService {


	@FormUrlEncoded
	@POST("api/poster/index")
	Observable<BaseRsp<PostRsp>> loadPost(@Field("p") int page);

	@FormUrlEncoded
	@POST("api/poster/my_poster")
	Observable<BaseRsp<PostRsp>> loadMyPost(@Field("p") int page);

	@FormUrlEncoded
	@POST("api/poster/my_care")
	Observable<BaseRsp<PostRsp>> loadFollowPost(@Field("p") int page);


	@FormUrlEncoded
	@POST("api/poster/add_poster")
	Observable<BaseRsp<String>> publishPost(@Field("content") String content, @Field("images") String images);

	@FormUrlEncoded
	@POST("api/poster/detail")
	Observable<BaseRsp<Post>> queryPostDetail(@Field("id") int id);

	@FormUrlEncoded
	@POST("api/poster/comment_list")
	Observable<BaseRsp<PostComment>> queryCommentList(@Field("pid") int id);

	@FormUrlEncoded
	@POST("api/poster/praise")
	Observable<BaseRsp<String>> likePost(@Field("pid") int id);

	@FormUrlEncoded
	@POST("api/poster/del_poster")
	Observable<BaseRsp<String>> delPost(@Field("id") int id);

	@FormUrlEncoded
	@POST("api/poster/care")
	Observable<BaseRsp<String>> followPost(@Field("pid") int id);

	@FormUrlEncoded
	@POST("api/poster/comment")
	Observable<BaseRsp<String>> posterComment(@Field("pid") int id, @Field("comment") String comment);

	@FormUrlEncoded
	@POST("api/poster/comment_list")
	Observable<BaseRsp<String>> loadPostComment(@Field("pid") int id, @Field("p") int page);

	/**
	 * @param id 评论id
	 */
	@FormUrlEncoded
	@POST("api/poster/comment")
	Observable<BaseRsp<String>> replyPostComment(@Field("pid") int id, @Field("comment") String comment);

}
