package com.asia.paint.biz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.asia.paint.android.BuildConfig;
import com.asia.paint.base.model.AddCartViewModel;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.network.core.NetworkInit;
import com.asia.paint.base.util.ActivityStack;
import com.asia.paint.biz.login.LoginActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CacheUtils;
import com.chinapay.mobilepayment.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public class AsiaPaintApplication extends Application {

    private static final String KEY_USER_INFO = "KEY_USER_INFO";

    private static AddCartViewModel mAddCartViewModel;
    private static List<OnChangeCallback<Integer>> mCartCountCallback;
    public static AsiaPaintApplication mAsiaPaintApplication;

    static {
        mCartCountCallback = new ArrayList<>();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mAsiaPaintApplication = this;
        AppUtils.init(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        CrashReport.initCrashReport(getApplicationContext(), "ee468f0002", false);
        if (BuildConfig.DEBUG) {
            CrashReport.setUserSceneTag(this, 136721);
        }
        NetworkInit.init();
        initYinlian();
    }

    private void initYinlian() {
        Utils.setPackageName(getPackageName());
    }

    private static List<Activity> mActivities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        ActivityStack.getInstance().pushActivity(activity);
    }
    public static void finishAllActivity() {
        ActivityStack.getInstance().clearAllActivity();
    }
    public static void removeActivity(Activity activity) {
        ActivityStack.getInstance().popActivity(activity);
    }


    public static void saveUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        Gson gson = new Gson();
        String userInfoStr = gson.toJson(userInfo);
        CacheUtils.put(KEY_USER_INFO, userInfoStr);
    }

    public static UserInfo getUserInfo() {
        String userInfoStr = CacheUtils.getString(KEY_USER_INFO);
        if (TextUtils.isEmpty(userInfoStr)) {
            return new UserInfo();
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(userInfoStr, UserInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return new UserInfo();
    }

    public static void queryCartCount() {

        if (mAddCartViewModel == null) {
            synchronized (AddCartViewModel.class) {
                if (mAddCartViewModel == null) {
                    mAddCartViewModel = new AddCartViewModel();
                    mAddCartViewModel.queryCartCount().setCallback(result -> {
                        if (result.count >= 0) {
                            for (OnChangeCallback<Integer> callback : mCartCountCallback) {
                                if (callback != null) {
                                    callback.onChange(result.count);
                                }
                            }
                        }
                    });
                }
            }
        }
        mAddCartViewModel.queryCartCount();
    }

    /**
     * 购物车角标更新的回调
     *
     * @param callback 回调方法
     */
    public static void addCartCountCallback(OnChangeCallback<Integer> callback) {
        if (callback != null) {
            mCartCountCallback.add(callback);
        }
    }

    public static void removeCartCountCallback(OnChangeCallback<Integer> callback) {
        mCartCountCallback.remove(callback);
    }





    public static void exitToLogin() {
        try {
            finishAllActivity();
            Intent intent = new Intent(AppUtils.getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            CacheUtils.putTk("");
            AppUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
