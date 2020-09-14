package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewPostdetailHeaderBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.base.util.ImageUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2020/6/26.
 */

public class PostDetailHeaderView extends BaseFrameLayout<ViewPostdetailHeaderBinding> {
    private boolean ishidden;
    private onClicListener clickListener;

    public PostDetailHeaderView(@NonNull Context context) {
        super(context);
    }

    public PostDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PostDetailHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUserId(boolean ishidden){
        this.ishidden=ishidden;
        if (ishidden)
            mBinding.imgDel.setVisibility(View.VISIBLE);
        else
            mBinding.imgDel.setVisibility(View.GONE);
        invalidate();
    }

    public void setClickListener(onClicListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_postdetail_header;
    }

    @Override
    protected void initView() {
        mBinding.imgDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener!=null)
                    clickListener.Click();
            }
        });
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

    public interface onClicListener{
        void Click();
    }
}
