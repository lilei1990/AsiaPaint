package com.asia.paint.biz.find.post;

import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Post;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.MagicImage;
import com.asia.paint.base.widgets.PostHeaderView;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class PostAdapter extends BaseGlideAdapter<Post> {
    public PostAdapter() {
        super(R.layout.item_post);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Post post) {
        if (post != null) {
            PostHeaderView postHeader = helper.getView(R.id.view_post_header);
            if (post._user != null) {
                postHeader.setAvatar(post._user.avatar);
                postHeader.setUserName(post._user.nickname);
            }
            postHeader.setTime(post.add_time);
            postHeader.setContent(post.content);
            MagicImage magicImage = helper.getView(R.id.view_magic_image);
            magicImage.setImageUrls(post.images);
            TextView tvLike = helper.getView(R.id.tv_post_like);
            tvLike.setSelected(post.isPraise());
            tvLike.setText(String.valueOf(post.praise));
            helper.addOnClickListener(R.id.tv_post_like);
            helper.setText(R.id.tv_post_comment, String.valueOf(post.comment));
        }
    }
}
