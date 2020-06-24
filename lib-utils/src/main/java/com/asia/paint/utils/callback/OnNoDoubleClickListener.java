package com.asia.paint.utils.callback;

import android.view.View;

/**
 * 防暴击监听
 *
 * @author by chenhong14 on 2019/11/2.
 */
public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    private static final int DEFAULT_INTERVAL_TIME = 800;
    private int mInterval = DEFAULT_INTERVAL_TIME;
    private long mLastTime;

    public OnNoDoubleClickListener() {

    }

    public OnNoDoubleClickListener(int interval) {
        mInterval = interval;
    }

    @Override
    public void onClick(View v) {
        long time = System.currentTimeMillis();
        if (Math.abs(time - mLastTime) > mInterval) {
            onNoDoubleClick(v);
            mLastTime = time;
        }
    }

    public abstract void onNoDoubleClick(View view);
}
