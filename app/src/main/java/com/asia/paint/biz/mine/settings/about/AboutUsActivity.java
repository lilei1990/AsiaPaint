package com.asia.paint.biz.mine.settings.about;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityAboutUsBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.base.widgets.dialog.UpdateDialog;
import com.asia.paint.biz.mine.seller.goals.WebActivity;
import com.asia.paint.biz.update.UpdateViewModle;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {
    private UpdateViewModle updateViewmodle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "关于亚士漆";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateViewmodle = getViewModel(UpdateViewModle.class);
        mBinding.itemAboutAsia.setTitle("亚士漆简介");
        mBinding.tvVersionCode.setText("V" + getAppVersionName(AboutUsActivity.this));
        mBinding.itemAboutAsia.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                WebActivity.start(AboutUsActivity.this, OtherService.ABOUT_ASIA);
            }
        });
        mBinding.itemCheckUpdate.setTitle("检测更新");
        mBinding.itemCheckUpdate.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                updateViewmodle.update().setCallback(result -> {
                    if (result != null) {
                        if (result.sn > getVersionCode(mContext)) {//如果服务器版本号大于当前版本号，则更新
                            UpdateDialog dialog = UpdateDialog.createInstance(result);
                            dialog.show(mContext);
                           dialog.setChangeCallback(new OnChangeCallback<String>() {
                               @Override
                               public void onChange(String result) {
                               }
                           });
                        }
                    }
                });
            }
        });
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static int getVersionCode(Context context) {
        int verCode = 0;
        try {
            verCode = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }
}
