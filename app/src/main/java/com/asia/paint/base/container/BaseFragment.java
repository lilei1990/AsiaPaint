package com.asia.paint.base.container;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author by chenhong14 on 2019-11-04.
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected Context mContext;
    protected Activity mActivity;
    protected T mBinding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
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
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected <V extends ViewModel> V getViewModel(@NonNull Class<V> modelClass) {
        return ViewModelProviders.of(this).get(modelClass);
    }

    protected <V extends ViewModel> V getViewModel(@NonNull String key, @NonNull Class<V> modelClass) {
        return ViewModelProviders.of(this).get(key, modelClass);
    }
}
