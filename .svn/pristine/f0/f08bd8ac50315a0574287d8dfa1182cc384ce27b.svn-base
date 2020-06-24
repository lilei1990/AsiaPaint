package com.asia.paint.utils.callback;


import android.os.Handler;
import android.os.Looper;

/**
 * @author by chenhong14 on 2019-11-20.
 */
public class CallbackDate<T> {
    private T mData;
    private OnChangeCallback<T> mCallback;
    private Handler mHandler;

    public void setData(T data) {
        mData = data;
        if (mCallback != null) {
            mCallback.onChange(mData);
        }
    }

    public void postData(T data) {
        mData = data;
        if (mCallback != null) {
            getHandler().post(() -> mCallback.onChange(data));
        }
    }

    private Handler getHandler() {
        if (mHandler == null) {
            synchronized (CallbackDate.class) {
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

    public void setCallback(OnChangeCallback<T> callback) {
        mCallback = callback;
    }
}
