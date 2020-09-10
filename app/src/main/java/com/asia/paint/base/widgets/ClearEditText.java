package com.asia.paint.base.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewClearEditextBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * @author by chenhong14 on 2019-11-02.
 */
public class ClearEditText extends LinearLayout {

    private static final int PHONE_LENGTH = 11;

    public ViewClearEditextBinding mBinding;
    private OnChangeCallback<Boolean> mOnChangeCallback;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_clear_editext, this, true);
        mBinding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.ivDelete.setVisibility(TextUtils.isEmpty(s) ? GONE : VISIBLE);
                if (mOnChangeCallback != null) {
                    mOnChangeCallback.onChange(canNext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.ivDelete.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mBinding.etPhone.setText("");
            }
        });
    }

    public boolean canNext() {
        String s = mBinding.etPhone.getText().toString();
        return !TextUtils.isEmpty(s) && s.length() == PHONE_LENGTH;
    }


    public void setOnChangeCallback(OnChangeCallback<Boolean> onChangeCallback) {
        mOnChangeCallback = onChangeCallback;
    }
}
