package com.asia.paint.base.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author by chenhong14 on 2019-11-05.
 */
public abstract class BaseGlideAdapter<T> extends BaseQuickAdapter<T, GlideViewHolder> {

    private boolean mShowEmpty = true;

    public BaseGlideAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseGlideAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseGlideAdapter(int layoutResId) {
        super(layoutResId);
    }
    public BaseGlideAdapter(Context context,int layoutResId) {
        super(layoutResId);
        mContext = context;
        showDefaultEmptyView(mShowEmpty);
    }


    public void updateData(List<T> datas, boolean clearCache) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        if (clearCache) {
            replaceData(datas);
        } else {
            addData(datas);
        }
    }

    public void updateData(List<T> datas) {
        updateData(datas, false);
    }

    public T getData(int position) {
        List<T> data = getData();
        return data.get(position);
    }

    private void showDefaultEmptyView(boolean show) {
        mShowEmpty = show;
        if (mShowEmpty) {
            setDefaultEmptyView();
        } else {
            setEmptyView(null);
        }
    }

    private void setDefaultEmptyView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_empty, null);
        setEmptyView(view);
    }
}
