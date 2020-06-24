package com.asia.paint.utils.utils;


import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author by chenhong14 on 2018/5/14.
 */
public class CommonUtil {


    private static final int DEFAULT_INTERVAL_TIME = 500;
    private static long mLastTime;

    public static boolean isDoubleClick() {

        long time = System.currentTimeMillis();
        if (Math.abs(time - mLastTime) <= DEFAULT_INTERVAL_TIME) {
            return true;
        } else {
            mLastTime = time;
            return false;
        }
    }


    public static <T> void removeWeakReference(List<WeakReference<T>> datas, Object object) {
        if (datas == null || datas.isEmpty() || object == null) {
            return;
        }
        try {
            for (int i = 0; i < datas.size(); i++) {
                WeakReference weakReference = datas.get(i);
                if (weakReference != null) {
                    if (weakReference.get() == object) {
                        datas.remove(i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String hidePhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            return phone;
        }
        StringBuilder builder = new StringBuilder(phone);
        return builder.replace(3, 7, "****").toString();
    }

    public static String list2str(List<String> images) {
        StringBuilder builder = new StringBuilder();
        if (images != null) {
            for (String url : images) {
                builder.append(url).append(",");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    public static String showDoubleText(double value) {

        String format = "%.4f";
        return String.format(Locale.getDefault(), format, value);
    }

    public static final String DATE_FORMAT_1 = "yyyy.MM.dd";
    public static final String DATE_FORMAT_2 = "HH:mm";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd";

    public static String getFormatDate(long time, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(time));
    }

}
