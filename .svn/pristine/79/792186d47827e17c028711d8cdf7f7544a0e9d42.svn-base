package com.asia.paint.utils.callback;


import android.os.Handler;
import android.os.Looper;

import java.util.List;

public class CallbackDateList<T> {
    private List<T> mData;
    private OnChangeCallbackList<T> mCallback;
    private Handler mHandler;

    public void setData(List<T> data) {
        mData = data;
        if (mCallback != null) {
            mCallback.onChange(mData);
        }
    }

    public void postData(List<T> data) {
        mData = data;
        if (mCallback != null) {
            getHandler().post(() -> mCallback.onChange(data));
        }
    }

    private Handler getHandler() {
        if (mHandler == null) {
            synchronized (CallbackDateList.class) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }

    public List<T> getData() {
        return mData;
    }

    public void setCallback(OnChangeCallbackList<T> callback) {
        mCallback = callback;
    }
}
