package com.asia.paint.base.util;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.FileNotFoundException;
import java.util.Stack;

/**
 * Activity堆栈
 * Created by Kevin on 2018/1/30.
 */
public class ActivityStack {
    private static Stack<Activity> mActivityStack = new Stack<>();
    private static ActivityStack instance = new ActivityStack();

    public static ActivityStack getInstance() {
        return instance;
    }

    /**
     * 弹出Activity并销毁
     *
     * @param targetActivity
     */
    public void popActivity(Activity targetActivity) {
        if (targetActivity != null) {
            targetActivity.finish();
            mActivityStack.remove(targetActivity);
            targetActivity = null;
        }
    }

    /**
     * 弹出Activity并销毁
     *
     * @param activityName
     * @return
     */
    public boolean popActivity(String activityName) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().getSimpleName().equals(activityName)) {
                popActivity(activity);
                return true;
            }
        }
        return false;
    }

    /**
     * 拿到存活的activity
     *
     * @param activityName
     * @return
     */
    public Activity getActivity(String activityName) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().getSimpleName().equals(activityName)) {
                return activity;
            }
        }
        try {
            throw new ClassNotFoundException("未找到你需要的activity!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拿到存活的activity
     *
     * @param modelClass
     * @return
     */
    public <T extends Activity> T getActivity(@NonNull Class<T> modelClass) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().getSimpleName().equals(modelClass.getSimpleName())) {
                return (T) activity;
            }
        }
        try {
            throw new ClassNotFoundException("未找到你需要的activity!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Activity入栈
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 清Activity栈
     */
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }
}