package com.asia.paint.base.container;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.databinding.ActivityBaseTitleBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.KeyBoardUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author by chenhong14 on 2019-11-01.
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected ActivityBaseTitleBinding mBaseBinding;
    protected Context mContext;
    protected T mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getIntentValue();
        AsiaPaintApplication.addActivity(this);
        //如果为了追求性能，这里需要去掉
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (showTitleBar()) {
            initTitleBar();
        } else if (getLayoutId() != 0) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        }
        findViewById(android.R.id.content).setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                KeyBoardUtils.closeSoftInput(BaseActivity.this);
            }
        });
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        if (intent != null) {
            intentValue(intent);
        }
    }

    protected void intentValue(Intent intent) {

    }

    private void initTitleBar() {
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_title);
        mBaseBinding.ivBack.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
        mBaseBinding.tvTitle.setText(getMiddleTitle());

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), mBaseBinding.layoutContainer, true);
    }

    protected String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    protected abstract int getLayoutId();

    protected String getMiddleTitle() {
        return "";
    }

    protected void setRightMenu(String text, View.OnClickListener listener) {
        if (showTitleBar()) {
            mBaseBinding.tvRightText.setText(text);
            mBaseBinding.tvRightText.setOnClickListener(listener);
        }
    }

    protected boolean showTitleBar() {
        return false;
    }

    protected <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return ViewModelProviders.of(this).get(modelClass);
    }

    protected <T extends ViewModel> T getViewModel(@NonNull String key, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(this).get(key, modelClass);
    }

    @Override
    protected void onDestroy() {
        AsiaPaintApplication.removeActivity(this);
        super.onDestroy();
    }

}
