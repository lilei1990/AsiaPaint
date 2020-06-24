package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.databinding.ViewPostHeaderBinding;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class PostHeaderView extends BaseFrameLayout<ViewPostHeaderBinding> {
    public PostHeaderView(@NonNull Context context) {
        super(context);
    }

    public PostHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PostHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_post_header;
    }

    @Override
    protected void initView() {

    }

    public void setAvatar(Object url) {
        ImageUtils.loadCircleImage(mBinding.ivAvatar, url);
    }

    public void setUserName(String userName) {
        mBinding.tvUserName.setText(userName);
    }

    public void setTime(String time) {
        mBinding.tvUserTime.setText(time);
    }

    public void setContent(String content) {
        mBinding.tvContent.setText(content);
    }
}
