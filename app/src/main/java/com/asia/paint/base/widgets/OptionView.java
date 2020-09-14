package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewOptionBinding;
import com.asia.paint.base.container.BaseFrameLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class OptionView extends BaseFrameLayout<ViewOptionBinding> {

    public OptionView(@NonNull Context context) {
        super(context);
    }

    public OptionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OptionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_option;
    }

    public void setOptionIcon(@DrawableRes int resId) {
        mBinding.ivOptionIcon.setImageResource(resId);
    }

    public void setOptionDescription(String description) {
        mBinding.tvOptionDescription.setText(description);
    }

    public void setOptionContent(String content) {
        mBinding.tvOptionContent.setText(content);
    }

    public String getOptionContent() {
        return mBinding.tvOptionContent.getText().toString();
    }
}
