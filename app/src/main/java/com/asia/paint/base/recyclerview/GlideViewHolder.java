package com.asia.paint.base.recyclerview;

import android.view.View;
import android.widget.ImageView;

import com.asia.paint.R;
import com.asia.paint.utils.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.IdRes;

/**
 * @author by chenhong14 on 2019-11-05.
 */
public class GlideViewHolder extends BaseViewHolder {


    public GlideViewHolder(View view) {
        super(view);
    }


    public void loadImage(@IdRes int viewId, Object url) {

        ImageView imageView = getView(viewId);
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_default).into(imageView);

    }

    public void loadCircleImage(@IdRes int viewId, Object url) {
        ImageView imageView = getView(viewId);
        RequestOptions options = RequestOptions.circleCropTransform();
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_default).apply(options).into(imageView);
    }

    public void loadRoundedCornersImage(@IdRes int viewId, Object url, int radius) {
        ImageView imageView = getView(viewId);
        RoundedCorners roundedCorners = new RoundedCorners(AppUtils.dp2px(radius));
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_default).apply(options).into(imageView);
    }

}
