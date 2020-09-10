package com.asia.paint.base.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewSearchBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.utils.callback.OnChangeCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-28.
 */
public class SearchView extends BaseFrameLayout<ViewSearchBinding> {

    private OnChangeCallback<String> mChangeCallback;

    public SearchView(@NonNull Context context) {
        super(context);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mChangeCallback != null) {
                    mChangeCallback.onChange(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_search;
    }

    public void setChangeCallback(OnChangeCallback<String> changeCallback) {
        mChangeCallback = changeCallback;
    }

    public void setText(String text){
        mBinding.etSearch.setText(text);
    }
}
