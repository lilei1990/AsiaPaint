package com.asia.paint.biz.find.post;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.PostService;
import com.asia.paint.base.network.bean.Post;
import com.asia.paint.base.network.bean.PostComment;
import com.asia.paint.base.network.bean.PostRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class PostViewModel extends BaseViewModel {

	private CallbackDate<PostRsp> mPostRsp = new CallbackDate<>();
	private CallbackDate<Post> mPostDetailRsp = new CallbackDate<>();
	private CallbackDate<PostComment> mCommentListRsp = new CallbackDate<>();
	private CallbackDate<Boolean> mLikeRsp = new CallbackDate<>();
	private CallbackDate<Boolean> mFollowRsp = new CallbackDate<>();
	private CallbackDate<Boolean> mPostCommentRsp = new CallbackDate<>();
	private CallbackDate<Boolean> mPublishPostRsp = new CallbackDate<>();

	public CallbackDate<PostRsp> loadPost(int page) {
		NetworkFactory.createService(PostService.class)
				.loadPost(page)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<PostRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(PostRsp response) {
						mPostRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mPostRsp.setData(null);
					}

				});
		return mPostRsp;
	}

	public CallbackDate<PostRsp> loadMyPost(int page) {
		NetworkFactory.createService(PostService.class)
				.loadMyPost(page)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<PostRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(PostRsp response) {
						mPostRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mPostRsp.setData(null);
					}
				});
		return mPostRsp;
	}

	public CallbackDate<PostRsp> loadFollowPost(int page) {
		NetworkFactory.createService(PostService.class)
				.loadFollowPost(page)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<PostRsp>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(PostRsp response) {
						mPostRsp.setData(response);
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						mPostRsp.setData(null);
					}

				});
		return mPostRsp;
	}

	public CallbackDate<Boolean> likePost(int pid) {
		NetworkFactory.createService(PostService.class)
				.likePost(pid)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mLikeRsp.setData(true);
					}

				});
		return mLikeRsp;
	}

	public CallbackDate<Boolean> followPost(int pid) {
		NetworkFactory.createService(PostService.class)
				.followPost(pid)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mFollowRsp.setData(true);
					}

				});
		return mFollowRsp;
	}

	public CallbackDate<Post> queryPostDetail(int pid) {
		NetworkFactory.createService(PostService.class)
				.queryPostDetail(pid)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<Post>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(Post response) {
						mPostDetailRsp.setData(response);
					}

				});
		return mPostDetailRsp;
	}

	public CallbackDate<PostComment> queryCommentList(int pid) {
		NetworkFactory.createService(PostService.class)
				.queryCommentList(pid)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<PostComment>(false) {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(PostComment response) {
						mCommentListRsp.setData(response);
					}

				});
		return mCommentListRsp;
	}

	public CallbackDate<Boolean> posterComment(int pid, String comment) {
		NetworkFactory.createService(PostService.class)
				.posterComment(pid, comment)
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mPostCommentRsp.setData(true);
					}

				});
		return mPostCommentRsp;
	}

	public CallbackDate<Boolean> publishPost(String content, List<String> images) {
		StringBuilder builder = new StringBuilder();
		if (images != null) {
			for (String url : images) {
				builder.append(url).append(",");
			}
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		NetworkFactory.createService(PostService.class)
				.publishPost(content, builder.toString())
				.compose(new NetworkObservableTransformer<>())
				.subscribe(new DefaultNetworkObserver<String>() {
					@Override
					public void onSubscribe(Disposable d) {
						addDisposable(d);
					}

					@Override
					public void onResponse(String response) {
						mPublishPostRsp.setData(true);
					}

				});
		return mPublishPostRsp;
	}

}
