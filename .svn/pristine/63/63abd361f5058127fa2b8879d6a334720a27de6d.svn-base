package com.asia.paint.utils.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-11-20.
 */
public final class CopyUtils {

    private CopyUtils() {

    }

    public static <T extends Copyable> T copy(T origin) {
        if (origin != null) {
            return origin.copy();
        }
        return null;
    }

    public static <T extends Copyable> List<T> copyList(List<T> origins) {
        if (origins == null) {
            return null;
        }
        List<T> newList = new ArrayList<>();
        for (T origin : origins) {
            if (origin != null) {
                newList.add(origin.copy());
            }
        }
        return newList;
    }

    public static class Copyable {
        public <T extends Copyable> T copy() {
            return null;
        }
    }

    public static boolean copy(Context context, String copyStr) {
        if (context == null || TextUtils.isEmpty(copyStr)) {
            return false;
        }
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm == null) {
                return false;
            }
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText(null, copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
