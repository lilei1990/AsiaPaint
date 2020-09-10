package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewSettingsItemBinding;
import com.asia.paint.base.container.BaseFrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class SettingsItem extends BaseFrameLayout<ViewSettingsItemBinding> {
    public SettingsItem(@NonNull Context context) {
        super(context);
    }

    public SettingsItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_settings_item;
    }

    public void setTitle(String title) {
        mBinding.tvItemTitle.setText(title);
    }

    public void setSubTitle(String subTitle) {
        mBinding.tvItemSubTitle.setText(subTitle);
    }

    public void setDescription(String description) {
        mBinding.tvItemDescription.setText(description);

    }

    public void setRightClick(String description, OnClickListener listener) {
        mBinding.tvItemRightClick.setText(description);
        mBinding.tvItemRightClick.setOnClickListener(listener);
    }

    public void setButton(String text, OnClickListener listener) {
        mBinding.btnItem.setText(text);
        mBinding.btnItem.setVisibility(VISIBLE);
        mBinding.btnItem.setOnClickListener(listener);
        mBinding.ivNext.setVisibility(GONE);
    }

}
