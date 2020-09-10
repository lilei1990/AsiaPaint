package com.asia.paint.biz.find.post.detail;

import com.asia.paint.android.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class PostDetailAdapter extends BaseGlideAdapter<String> {
    public PostDetailAdapter() {
        super(R.layout.item_post_detail);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, String url) {
        helper.loadRoundedCornersImage(R.id.iv_image, url, 4);
    }
}
