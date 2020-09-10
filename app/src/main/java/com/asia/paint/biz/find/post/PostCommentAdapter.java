package com.asia.paint.biz.find.post;

import android.view.View;

import androidx.annotation.NonNull;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.PostComment;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.PostHeaderView;

/**
 * 微博评论
 *
 * @author tangkun
 */
public class PostCommentAdapter extends BaseGlideAdapter<PostComment.ResultBean> {
	public PostCommentAdapter() {
		super(R.layout.item_post_comment);
	}

	@Override
	protected void convert(@NonNull GlideViewHolder helper, PostComment.ResultBean post) {
		if (post != null) {
			PostHeaderView postHeader = helper.getView(R.id.view_post_header);
			if (post._user != null) {
				postHeader.setAvatar(post._user.avatar);
				postHeader.setUserName(post._user.nickname);
			}
			postHeader.setTime(post.add_time);
			postHeader.setContent(post.comment);
			if (helper.getAdapterPosition() == getData().size() - 1) {
				helper.getView(R.id.view_post_divider).setVisibility(View.GONE);
			} else {
				helper.getView(R.id.view_post_divider).setVisibility(View.VISIBLE);
			}
		}
	}
}
