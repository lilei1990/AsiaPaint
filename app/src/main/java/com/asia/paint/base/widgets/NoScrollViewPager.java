package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author by chenhong14 on 2019-12-10.
 */
public class NoScrollViewPager extends ViewPager {
    private boolean mScroll;

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mScroll && super.onInterceptTouchEvent(event);
    }

    public void setScroll(boolean scroll) {
        mScroll = scroll;
    }
}
