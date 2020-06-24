package com.asia.paint.base.recyclerview;

import android.graphics.Rect;
import android.view.View;

import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class DefaultItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public DefaultItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public DefaultItemDecoration(int space) {
        this.left = space;
        this.top = space;
        this.right = space;
        this.bottom = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(AppUtils.dp2px(left), AppUtils.dp2px(top), AppUtils.dp2px(right), AppUtils.dp2px(bottom));
    }
}
