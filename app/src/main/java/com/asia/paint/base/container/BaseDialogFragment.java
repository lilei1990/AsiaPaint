package com.asia.paint.base.container;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

/**
 * @author by chenhong14 on 2019-11-02.
 */
public abstract class BaseDialogFragment<T extends ViewDataBinding> extends AppCompatDialogFragment {

    protected T mBinding;
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            argumentsValue(bundle);
        }
    }

    protected void argumentsValue(Bundle bundle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initView();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDialogParams();
    }

    protected void setParams(Window window, Dialog dialog) {

    }

    /**
     * 设置dialog的属性
     */
    private void setDialogParams() {
        //设置dialog为全屏
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        setParams(window, dialog);
        //不设置这个，不会全屏
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去除弹框阴影
        //不设置这个，父布局的属性不生效
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(getDialogWidth(), getDialogHeight());
    }

    public void show(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof FragmentActivity) {
                show(((FragmentActivity) context).getSupportFragmentManager(), "");
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
    }


    protected int getDialogWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    protected int getDialogHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
