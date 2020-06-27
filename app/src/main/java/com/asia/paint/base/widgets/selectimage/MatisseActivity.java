package com.asia.paint.base.widgets.selectimage;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.utils.AppUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MatisseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 7876;
    private static final String KEY_MAX_IMAGE = "KEY_MAX_IMAGE";
    private static OnChangeCallback<List<String>> mOnChangeCallback;
    private int mMax = 9;

    public static void start(Context context, OnChangeCallback<List<String>> onChangeCallback) {
        mOnChangeCallback = onChangeCallback;
        context.startActivity(new Intent(context, MatisseActivity.class));
    }

    public static void start(Context context, int max, OnChangeCallback<List<String>> onChangeCallback) {
        mOnChangeCallback = onChangeCallback;
        Intent intent = new Intent(context, MatisseActivity.class);
        intent.putExtra(KEY_MAX_IMAGE, max);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsiaPaintApplication.addActivity(this);
        Intent intent = getIntent();
        if (intent != null) {
            mMax = intent.getIntExtra(KEY_MAX_IMAGE, 9);
        }
        //如果为了追求性能，这里需要去掉
        /*       getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        selectImage();
    }


    public void selectImage() {
        XXPermissions.with(this)
                // .constantRequest()
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            Matisse.from(MatisseActivity.this)
                                    .choose(MimeType.ofImage())
                                    .countable(true)
                                    .maxSelectable(mMax)
                                    .capture(true)
//                                    .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
                                    .captureStrategy(new CaptureStrategy(true, "com.asia.paint.android.fileProvider"))
                                    //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                    //.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false) // Default is `true`
                                    .forResult(REQUEST_CODE_CHOOSE);
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        AppUtils.showMessage("请授予权限，否则无法选择图片");
                        finish();
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && data != null) {
            if (mOnChangeCallback != null) {
                mOnChangeCallback.onChange(Matisse.obtainPathResult(data));
            }
        }
        finish();
    }

    @Override
    public void finish() {
        mOnChangeCallback = null;
        super.finish();
    }

    @Override
    protected void onDestroy() {
        AsiaPaintApplication.removeActivity(this);
        super.onDestroy();
    }
}
