package com.asia.paint.base.util;

import android.widget.ImageView;

import com.asia.paint.R;
import com.asia.paint.utils.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author by chenhong14 on 2019-11-29.
 */
public class ImageUtils {

    public static void load(ImageView view, Object url) {
        if (view == null) {
            return;
        }
        Glide.with(view.getContext()).load(url).placeholder(R.mipmap.ic_default).transform(new CenterCrop()).into(view);
    }

    public static void loadCircleImage(ImageView view, Object url) {
        RequestOptions options = RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.ic_default)
                .error(R.mipmap.ic_default)
                .circleCrop();
        Glide.with(view.getContext()).load(url).apply(options).into(view);
    }

    public static void loadRoundedCornersImage(ImageView view, Object url, int radius) {
        RoundedCorners roundedCorners = new RoundedCorners(AppUtils.dp2px(radius));
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(R.mipmap.ic_default)
                .error(R.mipmap.ic_default);
        Glide.with(view.getContext()).load(url).apply(options).into(view);
    }
}
