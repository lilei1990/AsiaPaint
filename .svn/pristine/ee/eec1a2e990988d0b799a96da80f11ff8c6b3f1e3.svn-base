package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.asia.paint.R;
import com.asia.paint.databinding.ViewItemLayoutBinding;

/**
 * @author by chenhong14 on 2019-11-07.
 */
public class ItemLayout extends LinearLayout {
	private ViewItemLayoutBinding mBinding;

	public ItemLayout(Context context) {
		this(context, null);
	}

	public ItemLayout(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_item_layout, this, true);
	}

	public void setTitle(String title) {
		mBinding.tvItemTitle.setText(title);
	}

	public void setDescription(String description) {
		mBinding.tvItemDescription.setText(description);
	}

	public void setRightVisible(Boolean visible) {
		if (visible) {
			mBinding.ivItemRight.setVisibility(View.VISIBLE);
		} else {
			mBinding.ivItemRight.setVisibility(View.GONE);
		}
	}

}
