package com.asia.paint.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author 张进
 */

public class KeyBoardUtils {

    private static final long DURATION = 100L;

    private KeyBoardUtils() {
        //Do noting
    }


    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        closeKeybord(mContext, mEditText);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    public static void isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {//如果点击的是EditText 忽略
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
            } else {
                hideKeyboard(v.getContext(), v.getWindowToken());
                Log.i("hongc", "isShouldHideKeyboard: close");
            }
        }
    }

    public static boolean findView(View v, MotionEvent event){
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0],
                top = l[1],
                bottom = top + v.getHeight(),
                right = left + v.getWidth();

        return event.getX() > left && event.getX() < right
                && event.getY() > top && event.getY() < bottom;
    }

    public static void hideKeyboard(@NonNull Context context, @NonNull EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    public static void hideKeyboard(Context context, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null && im.isActive()) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static boolean isImActive(Context context) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return im != null && im.isActive();
    }

    /**
     * 打开键盘，如果是 edittext，则该 View 获取焦点
     */
    public static void openKeybord(@Nullable final View view) {
        if (view != null) {
            if (view instanceof EditText) {
                view.requestFocus();
            }
            // 将打开键盘的事件加在消息队列队尾，保证在频繁处理关掉/弹出键盘时能正确执行打开键盘
            view.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                    }
                }
            });

        }
    }


    private static boolean closeKeybord(@Nullable Context context, @Nullable View view) {
        if (context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            return imm != null && imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            return false;
        }
    }


    public static void closeSoftInput(final Context context) {
        // 有可能 context 不是 Activity
        if (context instanceof Activity) {
            final View view = ((Activity) context).getCurrentFocus();
            if (view == null) {
                return;
            }
            hideInputMethod(context, view);
        }
    }

    public static void closeSoftInput(@Nullable Window window) {
        final View view = window.getCurrentFocus();
        if (view == null) {
            return;
        }
        hideInputMethod(view.getContext(), view);

    }

    public static void openSoftInput(final Context context) {
        final View view = ((Activity) context).getCurrentFocus();
        if (view == null) {
            return;
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                showInputMethod(context, view);
            }
        }, DURATION);
    }


    /**
     * Hides the input method.
     *
     * @param context context
     * @param view    The currently focused view
     * @return success or not.
     */
    private static boolean hideInputMethod(@Nullable Context context, @Nullable View view) {
        if (context == null || view == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    /**
     * Show the input method.
     *
     * @param context context
     * @param view    The currently focused view, which would like to receive soft keyboard input
     * @return success or not.
     */
    private static boolean showInputMethod(@Nullable Context context, @Nullable View view) {
        if (context == null || view == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.showSoftInput(view, 0);

    }

}
