package com.asia.paint.base.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewPasswordEditextBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * @author by chenhong14 on 2019-11-02.
 */
public class PasswordEditText extends LinearLayout {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 16;

    public ViewPasswordEditextBinding mBinding;
    private OnChangeCallback<Boolean> mOnChangeCallback;

    private int mCloseEyeId = R.mipmap.ic_eye_close;
    private int mOpenEyeId = R.mipmap.ic_eye_open;
    private int mCurEyeResId = mCloseEyeId;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_password_editext, this, true);
        mBinding.etPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        mBinding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.ivView.setVisibility(TextUtils.isEmpty(s) ? GONE : VISIBLE);
                if (mOnChangeCallback != null) {
                    mOnChangeCallback.onChange(canNext());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.ivView.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mCurEyeResId = mCurEyeResId == mCloseEyeId ? mOpenEyeId : mCloseEyeId;
                mBinding.ivView.setImageResource(mCurEyeResId);
                int inputType = mCurEyeResId == mCloseEyeId
                        ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                        : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                mBinding.etPwd.setInputType(inputType);
            }
        });
    }

    public boolean canNext() {
        String pwd = mBinding.etPwd.getText().toString();
        return !TextUtils.isEmpty(pwd) && pwd.length() >= MIN_LENGTH && pwd.length() <= MAX_LENGTH;
    }


    public void setOnChangeCallback(OnChangeCallback<Boolean> onChangeCallback) {
        mOnChangeCallback = onChangeCallback;
    }
}
