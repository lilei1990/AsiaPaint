package com.asia.paint.biz.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.databinding.ActivityPhotoBinding;
import com.bumptech.glide.Glide;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class PhotoActivity extends BaseActivity<ActivityPhotoBinding> {

    private static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";

    private String mUrl;

    public static void start(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(KEY_IMAGE_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void intentValue(Intent intent) {
        super.intentValue(intent);
        mUrl = intent.getStringExtra(KEY_IMAGE_URL);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(mUrl).placeholder(R.mipmap.ic_default).into(mBinding.photoView);
        mBinding.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
