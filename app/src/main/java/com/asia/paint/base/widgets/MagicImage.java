package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewMagicImageBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.biz.other.PhotoActivity;
import com.asia.paint.utils.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-22.
 */
public class MagicImage extends BaseFrameLayout<ViewMagicImageBinding> implements View.OnClickListener {

	private List<ImageView> mImageViews;

	public MagicImage(@NonNull Context context) {
		super(context);
	}

	public MagicImage(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MagicImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initView() {
		mImageViews = new ArrayList<>();
		mImageViews.add(mBinding.iv1);
		mImageViews.add(mBinding.iv2);
		mImageViews.add(mBinding.iv3);
		mImageViews.add(mBinding.iv4);
		mImageViews.add(mBinding.iv5);
		mImageViews.add(mBinding.iv6);
		for (View view : mImageViews) {
			view.setOnClickListener(this);
		}
	}

	public void setImageUrls(List<String> imageUrls) {
		setImageUrls(imageUrls, 0);
	}

	public void setImageUrls(List<String> imageUrls, int radius) {
		if (imageUrls == null) {
			return;
		}
		mBinding.layout1.setVisibility(imageUrls.size() > 0 ? VISIBLE : GONE);
		mBinding.layout2.setVisibility(imageUrls.size() > 3 ? VISIBLE : GONE);
		post(() -> {
			int height = (getMeasuredWidth() - AppUtils.dp2px(6)) / 3;
			LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) mBinding.layout1.getLayoutParams();
			if (params1 != null) {
				params1.height = height;
				mBinding.layout1.setLayoutParams(params1);
			}

			LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mBinding.layout2.getLayoutParams();
			if (params2 != null) {
				params2.height = height;
				mBinding.layout2.setLayoutParams(params2);
			}
			for (int i = 0; i < imageUrls.size() && i < mImageViews.size(); i++) {
				if (radius > 0) {
					loadRoundedCornersImage(mImageViews.get(i), imageUrls.get(i), radius);
				} else {
					loadImage(mImageViews.get(i), imageUrls.get(i));
				}
			}
		});
	}

	public void loadRoundedCornersImage(ImageView imageView, Object url, int radius) {
		imageView.setVisibility(VISIBLE);
		imageView.setTag(url);
		Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_default).transform(new CenterCrop(), new RoundedCorners(radius)).into(imageView);
	}

	public void loadImage(ImageView imageView, Object url) {
		imageView.setVisibility(VISIBLE);
		imageView.setTag(url);
		Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_default).transform(new CenterCrop()).into(imageView);
	}


	@Override
	protected int getLayoutId() {
		return R.layout.view_magic_image;
	}

	@Override
	public void onClick(View v) {
		PhotoActivity.start(getContext(), (String) v.getTag());
	}
}
