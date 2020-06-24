package com.asia.paint.base.image;

import android.content.Context;
import android.widget.ImageView;

import com.asia.paint.banner.loader.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public class GlideImageLoader extends ImageLoader {

	@Override
	public void displayImage(Context context, Object path, ImageView imageView) {
		Glide.with(context).load(path).transform(new CenterCrop()).into(imageView);
	}
}
