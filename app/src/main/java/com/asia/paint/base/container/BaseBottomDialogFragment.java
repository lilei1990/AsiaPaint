package com.asia.paint.base.container;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;


import com.asia.paint.android.R;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

/**
 * @author by chenhong14 on 2019-11-09.
 */
public abstract class BaseBottomDialogFragment<T extends ViewDataBinding> extends BaseDialogFragment<T> {


    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected void setParams(Window window, Dialog dialog) {
        super.setParams(window, dialog);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomToTop);
    }
}
