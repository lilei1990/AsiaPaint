package com.asia.paint.utils.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author by chenhong14 on 2019-11-17.
 */
public class CacheUtils {

    private static final String FILE_NAME = "sp_user";
    private static final String KEY_TK = "key_yk";
    private static final String KEY_P = "KEY_P";
    public static final String KEY_SMZ = "KEY_SMZ";


    public static void put(String key, Object value) {

        SharedPreferences preferences = CacheUtils.getSharedPreferences(FILE_NAME);
        SharedPreferences.Editor edit = preferences.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
        edit.apply();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences preferences = CacheUtils.getSharedPreferences(FILE_NAME);
        return preferences.getString(key, defaultValue);
    }

    public static Boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences preferences = CacheUtils.getSharedPreferences(FILE_NAME);
        return preferences.getBoolean(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    private static SharedPreferences getSharedPreferences(String fileNanme) {
        return AppUtils.getContext().getSharedPreferences(fileNanme, Context.MODE_PRIVATE);
    }

    public static String getTk() {
        return getString(KEY_TK);
    }

    public static void putTk(String tk) {
        put(KEY_TK, tk);
    }

    public static void hasPayPwd(Boolean has) {
        put(KEY_P, has);
    }

    public static boolean hasPayPwd() {
        return getBoolean(KEY_P, false);
    }
}
