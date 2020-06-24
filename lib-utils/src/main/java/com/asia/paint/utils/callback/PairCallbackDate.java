package com.asia.paint.utils.callback;


import android.os.Handler;
import android.os.Looper;

/**
 * @author by chenhong14 on 2019-11-20.
 */
public class PairCallbackDate<T, K> {
    private T mData;
    private K mValue;
    private OnChangePairCallback<T, K> mCallback;
    private Handler mHandler;

    public void setData(T data, K value) {
        mData = data;
        mValue = value;
        if (mCallback != null) {
            mCallback.onChange(mData, mValue);
        }
    }

    public void postData(T data, K value) {
        mData = data;
        mValue = value;
        if (mCallback != null) {
            getHandler().post(() -> mCallback.onChange(data, value));
        }
    }

    private Handler getHandler() {
        if (mHandler == null) {
            synchronized (PairCallbackDate.class) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }

    public T getData() {
        return mData;
    }

    public K getValue() {
        return mValue;
    }

    public void setCallback(OnChangePairCallback<T, K> callback) {
        mCallback = callback;
    }
}
