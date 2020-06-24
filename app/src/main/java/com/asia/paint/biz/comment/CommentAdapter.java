package com.asia.paint.biz.comment;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Comment;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.MagicImage;
import com.asia.paint.utils.utils.DigitUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;

/**
 * @author by chenhong14 on 2019-11-22.
 */
public class CommentAdapter extends BaseGlideAdapter<Comment> {
    public CommentAdapter() {
        super(R.layout.item_comment, new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Comment comment) {
        if (comment != null) {
            if (comment._user != null) {
                helper.loadCircleImage(R.id.iv_avatar, comment._user.avatar);
                helper.setText(R.id.tv_user_name, comment._user.nickname);
            }
            AppCompatRatingBar ratingBar = helper.getView(R.id.rb_score);
            ratingBar.setRating(DigitUtils.parseFloat(comment.comment_score));
            helper.setText(R.id.tv_date, comment.comment_time);
            helper.setText(R.id.tv_comment, comment.comment);
            MagicImage magicImage = helper.getView(R.id.view_magic_image);
            magicImage.setImageUrls(comment.comment_images, 4);
        }
    }
}
