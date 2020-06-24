package com.asia.paint.utils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.annotation.ColorRes;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public class AppUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static void init(Context context) {

        sContext = context.getApplicationContext();
        if (sContext == null){
            sContext = context;
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public static void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    public static Resources getResources() {
        return sContext.getResources();
    }

    public static int getColor(@ColorRes int id) {
        return getResources().getColor(id);
    }


    public static int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    public static int sp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }
}
