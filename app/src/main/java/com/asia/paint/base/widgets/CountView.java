package com.asia.paint.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.biz.cart.CartViewModel;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-09.
 */
public class CountView extends FrameLayout {
    private int mLayoutResId = R.layout.view_count;
    private int minCount = 1;
    private int maxCount = Integer.MAX_VALUE;
    private AtomicInteger mCount = new AtomicInteger(minCount);
    private TextView mReduceTv;
    private TextView mCountTv;
    private TextView mPlusTv;
    private CartViewModel mViewModel;
    private Integer mSpecId;
    private Integer mRecId;
    private CountViewCallback mCallback;
    private boolean mEnable = true;

    public CountView(@NonNull Context context) {
        this(context, null);
    }

    public CountView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        init();
    }

    private void initView(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, com.asia.paint.banner.R.styleable.CountView);
            mLayoutResId = typedArray.getResourceId(com.asia.paint.banner.R.styleable.CountView_count_layout, mLayoutResId);
            typedArray.recycle();
        }
        View view = View.inflate(context, mLayoutResId, this);
        mReduceTv = view.findViewById(R.id.tv_reduce);
        mCountTv = view.findViewById(R.id.tv_count);
        mPlusTv = view.findViewById(R.id.tv_plus);
    }

    private void init() {
        mViewModel = new CartViewModel();
        setCount(mCount.get());
        mReduceTv.setOnClickListener(v -> {
            if (isAutoReduce()) {
                mViewModel.updateCart(mRecId, mCount.get() - 1).setCallback(aBoolean -> {
                    if (aBoolean) {
                        notifyUpdate();
                    }
                });
            } else {
                setCount(mCount.decrementAndGet());
                if (mCallback!=null){
                    mCallback.onChange(getCount());
                }
            }
        });
        mPlusTv.setOnClickListener(v -> {
            if (isAutoPlus()) {
                mViewModel.addCart(mSpecId, 1).setCallback(aBoolean -> {
                    if (aBoolean) {
                        notifyUpdate();
                    }
                });
            } else {
                setCount(mCount.incrementAndGet());
                if (mCallback!=null){
                    mCallback.onChange(getCount());
                }
            }


        });
    }

    private boolean isAutoPlus() {
        return mSpecId != null;
    }

    private boolean isAutoReduce() {
        return mRecId != null;
    }

    private void notifyUpdate() {
        if (mCallback != null) {
            mCallback.onUpdate();
        }
    }

    private void updateReduceStatus() {
        mReduceTv.setEnabled(mEnable && mCount.get() > minCount);
    }

    private void updateAddStatus() {
        mPlusTv.setEnabled(mEnable && mCount.get() < maxCount);
    }

    public void setCount(int count) {
        if (count >= minCount && count <= maxCount) {
            this.mCount.set(count);
            mCountTv.setText(String.valueOf(count));
            updateAddStatus();
            updateReduceStatus();
        }
    }

    public void setEnable(boolean enable) {
        mEnable = enable;
        mReduceTv.setEnabled(enable);
        mPlusTv.setEnabled(enable);
    }

    public int getCount() {
        return mCount.get();
    }

    public void setMinCount(int minCount) {
        if (minCount >= 0) {
            this.minCount = minCount;
        }
    }

    public void setMaxCount(int maxCount) {
        if (maxCount >= minCount) {
            this.maxCount = maxCount;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public int getSpecId() {
        return mSpecId;
    }

    public void setSpecId(int specId) {
        mSpecId = specId;
    }


    public void setCallback(CountViewCallback callback) {
        mCallback = callback;
    }

    public void setRecId(Integer recId) {
        mRecId = recId;
    }

    public interface CountViewCallback {
        void onUpdate();

        void onChange(int count);
    }
}
