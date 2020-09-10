package com.asia.paint.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewFoldPanelBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-25.
 */
public class FoldPanel<T> extends BaseFrameLayout<ViewFoldPanelBinding> {

    private BaseGlideAdapter<T> mAdapter;
    private boolean mShowAll;
    private int mDefaultShowCount = 1;
    private List<T> mDatas = new ArrayList<>();
    private List<T> mShowDatas = new ArrayList<>();

    public FoldPanel(@NonNull Context context) {
        super(context);
    }

    public FoldPanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FoldPanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(mContext));
        // setAdapter(mAdapter);
        mBinding.ivFlagSwitch.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mShowAll = !mShowAll;
                updateDatas();
            }
        });
    }


    public void setDatas(List<T> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        updateDatas();
    }

    private void updateDatas() {
        mShowDatas.clear();
        if (mDatas.size() > mDefaultShowCount && !mShowAll) {
            mShowDatas.addAll(mDatas.subList(0, mDefaultShowCount));
        } else {
            mShowDatas.addAll(mDatas);
        }
        if (mAdapter != null) {
            mAdapter.replaceData(mShowDatas);
        }
        setSwitchFlag();
    }

    private void setSwitchFlag() {
        mBinding.ivFlagSwitch.setVisibility(mDatas.size() > mDefaultShowCount ? VISIBLE : GONE);
        mBinding.ivFlagSwitch.setSelected(mShowAll);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.view_fold_panel;
    }

    public void setDefaultShowCount(int defaultShowCount) {
        mDefaultShowCount = defaultShowCount;
    }

    public void setAdapter(BaseGlideAdapter<T> adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            mBinding.rvList.setAdapter(mAdapter);
        }
    }
}
