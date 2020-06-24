package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.asia.paint.base.container.BaseFrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2020-03-01.
 */
public class FlashTimeView extends BaseFrameLayout {
    public FlashTimeView(@NonNull Context context) {
        super(context);
    }

    public FlashTimeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashTimeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
