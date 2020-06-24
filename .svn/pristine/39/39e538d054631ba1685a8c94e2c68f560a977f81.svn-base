package com.asia.paint.biz.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.biz.login.LoginActivity;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.utils.utils.CacheUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-06.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(CacheUtils.getTk())) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
