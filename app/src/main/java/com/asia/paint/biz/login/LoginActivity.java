package com.asia.paint.biz.login;


import static com.asia.paint.android.wxapi.WXEntryActivity.ACTION_WEI_XIN_LOGIN;
import static com.asia.paint.android.wxapi.WXEntryActivity.KEY_WEI_XIN_LOGIN;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.asia.paint.R;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.base.network.bean.CodeBean;
import com.asia.paint.base.network.bean.LoginRsp;
import com.asia.paint.base.network.bean.UserInfo;
import com.asia.paint.base.network.bean.WeiXinInfo;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.AsiaPaintApplication;
import com.asia.paint.biz.login.bind.BindPhoneActivity;
import com.asia.paint.biz.login.forget.ForgetPasswordActivity;
import com.asia.paint.biz.main.MainActivity;
import com.asia.paint.biz.mine.favorites.FavoritesActivity;
import com.asia.paint.biz.mine.seller.goals.WebViewActivity;
import com.asia.paint.databinding.ActivityLoginBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.CacheUtils;
import com.asia.paint.utils.utils.SpanText;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author by chenhong14 on 2019-11-01.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    public static final String KEY_USER_ACCOUNT = "KEY_USER_ACCOUNT";
    private static final int MAX_COUNT = 100;
    private static final int MIN_COUNT = 0;

    private boolean mSmsCodeLogin = true;
    private LocalBroadcastManager mLocalBroadcastManager;
    private WeiXinLoginBroadcastReceiver mWeiXinLoginBroadcastReceiver = new WeiXinLoginBroadcastReceiver();

    private int count = MAX_COUNT;
    private SmsCode mCurSmsCodeStatus;
    private String mOpenId;
    private Runnable mSmsCodeCountRunnable = () -> {

        if (count == MIN_COUNT) {
            setSmsCodeStatus(mBinding.etPhone.canNext() ? SmsCode.ENABLE : SmsCode.DISABLE);
            count = MAX_COUNT;
        } else {
            setSmsCodeStatus(SmsCode.COUNT);
            count--;
        }
    };

    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter weiXinLogin = new IntentFilter(ACTION_WEI_XIN_LOGIN);
        mLocalBroadcastManager.registerReceiver(mWeiXinLoginBroadcastReceiver, weiXinLogin);
        mLoginViewModel = getViewModel(LoginViewModel.class);
        mBinding.etPhone.mBinding.etPhone.setHint("输入手机号");
        mBinding.etPhone.setOnChangeCallback(canNext -> {
            if (mCurSmsCodeStatus != SmsCode.COUNT) {
                setSmsCodeStatus(canNext ? SmsCode.ENABLE : SmsCode.DISABLE);
            }
            updateLoginButton();
        });
        setPhone();
        setSmsCodeStatus(SmsCode.DISABLE);
        mBinding.etSmsCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        mBinding.etSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLoginButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.tvSendSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                count = MAX_COUNT;
                setSmsCodeStatus(SmsCode.COUNT);
                mLoginViewModel.requestSmsCode(getPhone());

            }
        });
        mBinding.etPassword.setOnChangeCallback(new OnChangeCallback<Boolean>() {
            @Override
            public void onChange(Boolean result) {
                updateLoginButton();
            }
        });
        mBinding.ivScanCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                XXPermissions.with(LoginActivity.this)
                        .permission(Manifest.permission.CAMERA)
                        .request(new OnPermission() {
                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                startActivityForResult(new Intent(LoginActivity.this, CaptureActivity.class), CaptureActivity.REQ_CODE);
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {
                                AppUtils.showMessage("没有开启相机权限，无法使用扫码功能");
                            }
                        });
            }
        });
        mBinding.tvPwdLogin.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mSmsCodeLogin = false;
                updateLoginType();
            }
        });
        mBinding.tvSmsCodeLogin.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mSmsCodeLogin = true;
                updateLoginType();
            }
        });
        mBinding.tvNoSmsCode.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MessageDialog dialog = new MessageDialog.Builder()
                        .title("收不到验证码？")
                        .message("① 请检查是否输入正确的手机号\n② 请检查手机是否停机\n③ 请使用其他账号登录\n④ 请联系官方客服")
                        .setSureButton("我知道了", null)
                        .build();
                dialog.show(LoginActivity.this);
            }
        });
        mBinding.tvForgetPwd.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        mBinding.btnLogin.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String referrerCode = mBinding.etReferrerCode.getText().toString();
                if (mSmsCodeLogin) {
                    String smsCode = mBinding.etSmsCode.getText().toString();
                    mLoginViewModel.loginViaPhone(getPhone(), smsCode, referrerCode, mOpenId);
                } else {
                    String pwd = mBinding.etPassword.mBinding.etPwd.getText().toString();
                    mLoginViewModel.loginViaPwd(getPhone(), pwd, referrerCode);
                }
            }
        });
        mBinding.tvWeiXinLogin.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(LoginActivity.this, Constants.WEI_XIN_APP_ID);
                if (msgApi.isWXAppInstalled()) {
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "we_chat_sdk_login";
                    msgApi.sendReq(req);
                } else {
                    AppUtils.showMessage("请先安装微信");
                }
            }
        });
        mLoginViewModel.mLoginRsp.observe(this, new Observer<LoginRsp>() {
            @Override
            public void onChanged(LoginRsp loginRsp) {
                if (loginRsp != null) {
                    loginSuccess(loginRsp.userinfo);
                }
            }
        });
        setStatement();
        setSmsCodeStatus(mBinding.etPhone.canNext() ? SmsCode.ENABLE : SmsCode.DISABLE);
    }

    private void loginSuccess(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        CacheUtils.putTk(userInfo.token);
        AsiaPaintApplication.saveUserInfo(userInfo);
        CacheUtils.put(KEY_USER_ACCOUNT, userInfo.mobile);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void setPhone() {
        String phone = CacheUtils.getString(KEY_USER_ACCOUNT);
        if (!TextUtils.isEmpty(phone)) {
            mBinding.etPhone.mBinding.etPhone.setText(phone);
        }
    }

    private String getPhone() {
        return mBinding.etPhone.mBinding.etPhone.getText().toString().trim();
    }

    private void updateLoginType() {
        mBinding.layoutSmsCode.setVisibility(mSmsCodeLogin ? View.VISIBLE : View.GONE);
        mBinding.tvNoSmsCode.setVisibility(mSmsCodeLogin ? View.VISIBLE : View.GONE);
        mBinding.tvSmsCodeLogin.setVisibility(mSmsCodeLogin ? View.GONE : View.VISIBLE);
        mBinding.etPassword.setVisibility(mSmsCodeLogin ? View.GONE : View.VISIBLE);
        mBinding.tvForgetPwd.setVisibility(mSmsCodeLogin ? View.GONE : View.VISIBLE);
        mBinding.tvPwdLogin.setVisibility(mSmsCodeLogin ? View.VISIBLE : View.GONE);
        mBinding.etSmsCode.setText("");
        mBinding.etPassword.mBinding.etPwd.setText("");
        updateLoginButton();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getMiddleTitle() {
        return "登录";
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    private void setSmsCodeStatus(SmsCode code) {
        mCurSmsCodeStatus = code;

        switch (mCurSmsCodeStatus) {
            case COUNT:
                mBinding.tvSendSmsCode.setEnabled(false);
                String smsCodeCountFormat = "已发送（%ss）";
                mBinding.tvSendSmsCode.setText(String.format(smsCodeCountFormat, count));
                mBinding.tvSendSmsCode.postDelayed(mSmsCodeCountRunnable, 1000);
                break;
            case ENABLE:
                mBinding.tvSendSmsCode.setEnabled(true);
                mBinding.tvSendSmsCode.setText("发送验证码");
                break;
            case DISABLE:
                mBinding.tvSendSmsCode.setEnabled(false);
                mBinding.tvSendSmsCode.setText("发送验证码");
                break;
        }
    }

    private void setStatement() {
        String statement = "未注册用户点击登录默认注册，注册默认同意《用户协议》";
        String target = "《用户协议》";
        SpanText spanText = new SpanText.Builder()
                .origin(statement)
                .target(target)
                .setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        WebViewActivity.start(LoginActivity.this, OtherService.USER_ITEMS);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(getResources().getColor(R.color.color_1054CB));
                        ds.setUnderlineText(false);
                    }
                }).build();

        mBinding.tvStatement.setText(spanText.toSpan());
        mBinding.tvStatement.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void updateLoginButton() {
        boolean enable;
        if (mSmsCodeLogin) {
            String smsCode = mBinding.etSmsCode.getText().toString();
            enable = mBinding.etPhone.canNext() && (!TextUtils.isEmpty(smsCode) && smsCode.length() >= 4 && smsCode.length() <= 6);
        } else {
            enable = mBinding.etPhone.canNext() && mBinding.etPassword.canNext();

        }
        mBinding.btnLogin.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mWeiXinLoginBroadcastReceiver);
        mBinding.tvSendSmsCode.removeCallbacks(mSmsCodeCountRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CaptureActivity.REQ_CODE && resultCode == RESULT_OK && data != null) {
            String url=data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
            if (TextUtils.isEmpty(url)){
                ToastUtils.showLongToast(this,"二维码为空，请重新扫描");
                return;
            }
            requestCode(url);
        } else if (requestCode == BindPhoneActivity.REQUEST_BIND_PHONE && resultCode == RESULT_OK) {
            finish();
        }
    }

    /**
     * 获取推荐人code
     */
    private void requestCode(String url) {
        mLoginViewModel.requestCode(url);
        mLoginViewModel.codebean.observe(this, new Observer<CodeBean>() {
            @Override
            public void onChanged(CodeBean codeBean) {
                if (codeBean != null && !TextUtils.isEmpty(codeBean.code))
                    mBinding.etReferrerCode.setText(codeBean.code);
                else {
                    new MessageDialog.Builder()
                            .title("无效二维码")
                            .setSureButton("确认",null).build()
                            .show(LoginActivity.this);
                }
            }



        });
    }

    class WeiXinLoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String code = intent.getStringExtra(KEY_WEI_XIN_LOGIN);
                queryWeiXinInfo(code);
            }
        }
    }

    private void queryWeiXinInfo(String code) {
        mLoginViewModel.queryWeiXinInfo(code).setCallback(new OnChangeCallback<WeiXinInfo>() {
            @Override
            public void onChange(WeiXinInfo result) {
                mOpenId = result.openid;
                loginViaWeiXin(result.openid);
                //refreshWeiXinToken(result.refresh_token);
            }
        });

    }

/*    private void refreshWeiXinToken(String refreshToken){
        mLoginViewModel.refreshWeiXinToken(refreshToken).setCallback(new OnChangeCallback<WeiXinInfo>() {
            @Override
            public void onChange(WeiXinInfo result) {
                loginViaWeiXin(result.access_token, result.openid);
            }
        });
    }*/

    private void loginViaWeiXin(String openId) {
        mLoginViewModel.loginViaWeiXin(openId).setCallback(result -> {
            if (!result.isBind()) {
                BindPhoneActivity.start(LoginActivity.this, openId);
            } else {
                loginSuccess(result);
            }
        });
    }

    private void bindWeiXin(String token, String openId) {
        mLoginViewModel.bindWeiXin(token, openId);
    }
}
