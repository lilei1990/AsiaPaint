package com.asia.paint.base.widgets.comment;

import com.asia.paint.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-15.
 */
public class CommentImageAdapter extends BaseGlideAdapter<String> {
    public CommentImageAdapter() {
        super(R.layout.item_comment_image);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, String item) {
        helper.loadRoundedCornersImage(R.id.iv_image, item, 4);
    }
}
