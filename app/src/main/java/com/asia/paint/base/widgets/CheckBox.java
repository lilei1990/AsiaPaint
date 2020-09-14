package com.asia.paint.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.asia.paint.android.R;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.android.databinding.ViewCheckBoxBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-20.
 */
public class CheckBox extends BaseFrameLayout<ViewCheckBoxBinding> {

    private int mSelectorResId;
    private boolean mChecked;
    private OnCheckChangeListener mListener;

    public CheckBox(@NonNull Context context) {
        super(context);
    }

    public CheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, com.asia.paint.banner.R.styleable.CheckBox);
            mSelectorResId = typedArray.getResourceId(com.asia.paint.banner.R.styleable.CheckBox_cb_selector, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void initView() {
        if (mSelectorResId > 0) {
            mBinding.checkBox.setBackgroundResource(mSelectorResId);
        }
        mBinding.checkBox.setSelected(mChecked);
        mBinding.checkBox.setOnClickListener(v -> {
            if (mListener == null || mListener.changeBySelf()) {
                mChecked = !mChecked;
                mBinding.checkBox.setSelected(mChecked);
            }

            if (mListener != null) {
                mListener.onChange(mChecked);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_check_box;
    }

    public void setListener(OnCheckChangeListener listener) {
        mListener = listener;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
        if (mBinding != null && mBinding.checkBox != null) {
            mBinding.checkBox.setSelected(checked);
        }
    }


    public interface OnCheckChangeListener {
        void onChange(boolean isChecked);

        boolean changeBySelf();
    }

    public abstract static class OnDefaultCheckChangeListener implements OnCheckChangeListener {


        @Override
        public boolean changeBySelf() {
            return true;
        }
    }
}
