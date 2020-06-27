package com.asia.paint.biz.mine.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.mine.settings.about.AboutUsActivity;
import com.asia.paint.biz.mine.settings.account.index.AccountIndexActivity;
import com.asia.paint.biz.mine.settings.address.AddressActivity;
import com.asia.paint.biz.mine.settings.bind.BindNewPhoneActivity;
import com.asia.paint.biz.mine.settings.feedback.FeedbackActivity;
import com.asia.paint.biz.mine.settings.password.NewPwdActivity;
import com.asia.paint.biz.mine.settings.unsubscribe.UnsubscribeAccountActivity;
import com.asia.paint.biz.pay.password.SetPayPwdActivity;
import com.asia.paint.databinding.ActivitySettingsBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CleanDataUtils;
import com.asia.paint.utils.utils.CommonUtil;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "设置";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.itemAddress.setTitle("地址管理");
        mBinding.itemAddress.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AddressActivity.class));
            }
        });
        mBinding.itemPayments.setTitle("收款账户");
        mBinding.itemPayments.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AccountIndexActivity.class));
            }
        });

        mBinding.itemBindPhone.setTitle("绑定手机");
        UserInfo userInfo = AsiaPaintApplication.getUserInfo();
        String mobile = userInfo.mobile;
        mBinding.itemBindPhone.setSubTitle(CommonUtil.hidePhone(mobile));
        mBinding.itemBindPhone.setRightClick("更换号码", new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BindNewPhoneActivity.start(SettingsActivity.this);
            }
        });
        mBinding.itemPwd.setTitle("修改登录密码");
        mBinding.itemPwd.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(SettingsActivity.this, NewPwdActivity.class));
            }
        });
        mBinding.itemPayPwd.setTitle("修改支付密码");
        mBinding.itemPayPwd.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                SetPayPwdActivity.start(SettingsActivity.this);
            }
        });
        mBinding.itemWeiXin.setTitle("微信");
        mBinding.itemWeiXin.setButton("绑定", new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                AppUtils.showMessage("绑定");
            }
        });

        setClearCache();

        mBinding.itemFeedback.setTitle("反馈与建议");
        mBinding.itemFeedback.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(SettingsActivity.this, FeedbackActivity.class));
            }
        });
        mBinding.itemAboutAsia.setTitle("关于亚士漆");
        mBinding.itemAboutAsia.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(SettingsActivity.this, AboutUsActivity.class));
            }
        });

        mBinding.itemAccountLogout.setTitle("注销账户");
        mBinding.itemAccountLogout.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(SettingsActivity.this, UnsubscribeAccountActivity.class));
            }
        });

        mBinding.tvExt.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                new MessageDialog.Builder()
                        .title("确认退出登录吗？")
                        .setCancelButton("取消", null)
                        .setSureButton("确认", new OnNoDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View view) {
                                AsiaPaintApplication.exitToLogin();
                            }
                        }).build()
                        .show(SettingsActivity.this);
            }
        });
    }

    public void setClearCache() {
        mBinding.itemClearCache.setTitle("清除缓存");
        try {
            String totalCacheSize = CleanDataUtils.getTotalCacheSize(AppUtils.getContext());
            mBinding.itemClearCache.setDescription(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.itemClearCache.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                new MessageDialog.Builder()
                        .title("确认清除缓存吗？")
                        .setCancelButton("取消", null)
                        .setSureButton("确认", new OnNoDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View view) {
                                CleanDataUtils.clearAllCache(AppUtils.getContext());
                                setClearCache();
                            }
                        }).build()
                        .show(SettingsActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BindNewPhoneActivity.REQUEST_BIND_PHONE && resultCode == RESULT_OK && data != null) {
            String newPhone = data.getStringExtra(BindNewPhoneActivity.KEY_BIND_NEW_PHONE);
            if (!TextUtils.isEmpty(newPhone)) {
                mBinding.itemBindPhone.setSubTitle(CommonUtil.hidePhone(newPhone));
            }
        }
    }
}
