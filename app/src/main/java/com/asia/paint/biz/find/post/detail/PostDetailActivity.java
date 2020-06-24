package com.asia.paint.biz.find.post.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.Post;
import com.asia.paint.base.network.bean.PostComment;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.widgets.PostHeaderView;
import com.asia.paint.base.widgets.SendMessagePopupWindow;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.find.post.PostCommentAdapter;
import com.asia.paint.biz.find.post.PostViewModel;
import com.asia.paint.databinding.ActivityPostDetailBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.KeyBoardUtils;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class PostDetailActivity extends BaseTitleActivity<ActivityPostDetailBinding> {

	private static final String KEY_POST_ID = "KEY_POST_ID";
	private PostViewModel mPostViewModel;
	private PostDetailAdapter mPostDetailAdapter;
	private PostCommentAdapter mPostCommentAdapter;
	private PostHeaderView mPostHeaderView;
	private View mPostFooterView;
	private TextView tvPostLike, tvPostComment;
	private int mPostId;
	private Post mPost;

	public static void start(Context context, int postId) {
		Intent intent = new Intent(context, PostDetailActivity.class);
		intent.putExtra(KEY_POST_ID, postId);
		context.startActivity(intent);
	}

	@Override
	protected void intentValue(Intent intent) {
		mPostId = intent.getIntExtra(KEY_POST_ID, 0);
	}

	@Override
	protected String middleTitle() {
		return "";
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_post_detail;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPostViewModel = getViewModel(PostViewModel.class);
		mBinding.rvPostDetail.setLayoutManager(new LinearLayoutManager(this));
		mBinding.rvPostDetail.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
		mPostDetailAdapter = new PostDetailAdapter();
		mPostHeaderView = new PostHeaderView(this);
		mPostDetailAdapter.addHeaderView(mPostHeaderView);
		mPostFooterView = LayoutInflater.from(this).inflate(R.layout.layout_post_footer, null);
		RecyclerView rvPostDetailComment = mPostFooterView.findViewById(R.id.rv_post_comment);
		tvPostLike = mPostFooterView.findViewById(R.id.tv_post_like);
		tvPostComment = mPostFooterView.findViewById(R.id.tv_post_comment);
		mPostDetailAdapter.addFooterView(mPostFooterView);
		mBinding.rvPostDetail.setAdapter(mPostDetailAdapter);

		rvPostDetailComment.setLayoutManager(new LinearLayoutManager(this));
		rvPostDetailComment.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
		mPostCommentAdapter = new PostCommentAdapter();
		rvPostDetailComment.setAdapter(mPostCommentAdapter);

		mPostViewModel.queryPostDetail(mPostId).setCallback(this::updatePost);
		mPostViewModel.queryCommentList(mPostId).setCallback(this::updateCommentList);
		//评论
		tvPostComment.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				postComment();
			}
		});
		//评论按钮
		mBinding.etComment.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				postComment();
			}
		});
		//点赞按钮
		mBinding.tvPostLike.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				postPraise();
			}
		});
		//点赞
		tvPostLike.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				postPraise();
			}
		});
	}

	private void postPraise() {
		mPostViewModel.likePost(mPostId).setCallback(result -> {
			if (mPost != null) {
				mPost.reversePraise();
				mBinding.tvPostLike.setSelected(mPost.isPraise());
				mBinding.tvPostLike.setText(String.valueOf(mPost.praise));
				tvPostLike.setSelected(mPost.isPraise());
				tvPostLike.setText(String.valueOf(mPost.praise));
			}
		});
	}

	private void postComment() {
		SendMessagePopupWindow bottomPopupOption = new SendMessagePopupWindow(PostDetailActivity.this);
		bottomPopupOption.showPopupWindow();
		bottomPopupOption.setOnClickListener(content -> {
			mPostViewModel.posterComment(mPostId, content).setCallback(result -> {
				mPostViewModel.queryCommentList(mPostId).setCallback(PostDetailActivity.this::updateCommentList);
			});
		});
	}

	private void updatePost(Post post) {
		if (post == null) {
			return;
		}
		mPost = post;
		if (post.isCare()) {
			setCancelText();
		} else {
			setFollowText();
		}
		//列表中点赞
		tvPostLike.setSelected(post.isPraise());
		tvPostLike.setText(String.valueOf(post.praise));
		//评论数量
		tvPostComment.setText(post.comment + "");
		//底部点赞
		mBinding.tvPostLike.setSelected(post.isPraise());
		mBinding.tvPostLike.setText(String.valueOf(post.praise));

		if (post._user.id == AsiaPaintApplication.getUserInfo().user_id) {
			mBaseBinding.tvRightText.setVisibility(View.GONE);
		} else {
			mBaseBinding.tvRightText.setVisibility(View.VISIBLE);
		}
		Post.User user = post._user;
		if (user != null) {
			mPostHeaderView.setAvatar(user.avatar);
			mPostHeaderView.setUserName(user.nickname);
		}
		mPostHeaderView.setTime(post.add_time);
		mPostHeaderView.setContent(post.content);
		mPostDetailAdapter.updateData(post.images);
	}

	private void updateCommentList(PostComment post) {
		if (post.result != null)
			mPostCommentAdapter.updateData(post.result);
	}

	private void setCancelText() {
		mBaseBinding.tvRightText.setText("取消关注");
		mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_333333));
		mBaseBinding.tvRightText.setCompoundDrawables(null, null, null, null);
		mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mPostViewModel.followPost(mPostId).setCallback(result -> setFollowText());
			}
		});
	}

	private void setFollowText() {
		mBaseBinding.tvRightText.setText("关注");
		mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_1054CB));
		Drawable followDrawable = getDrawable(R.mipmap.ic_post_follow);
		if (followDrawable != null) {
			// 设置边界，不然图片不显示
			followDrawable.setBounds(0, 0, followDrawable.getMinimumWidth(),
					followDrawable.getMinimumHeight());
		}
		mBaseBinding.tvRightText.setCompoundDrawables(followDrawable, null, null, null);
		mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View view) {
				mPostViewModel.followPost(mPostId).setCallback(result -> setCancelText());
			}
		});
	}
}
