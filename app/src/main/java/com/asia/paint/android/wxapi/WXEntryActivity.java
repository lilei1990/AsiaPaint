package com.asia.paint.android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.utils.utils.AppUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String ACTION_WEI_XIN_LOGIN = "ACTION_WEI_XIN_LOGIN";
    public static final String KEY_WEI_XIN_LOGIN = "KEY_WEI_XIN_LOGIN";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WEI_XIN_APP_ID, false);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SendMessageToWX.Resp) {
            finish();
            return;
        }
        //ERR_OK = 0(用户同意) ERR_AUTH_DENIED = -4（用户拒绝授权） ERR_USER_CANCEL = -2（用户取消）
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                AppUtils.showMessage("用户拒绝授权登录");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                AppUtils.showMessage("用户取消授权登录");
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意授权。
                final String code = ((SendAuth.Resp) baseResp).code;
                sendResult(code);
                break;
        }
        finish();
    }

    private void sendResult(String code) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent(ACTION_WEI_XIN_LOGIN);
        intent.putExtra(KEY_WEI_XIN_LOGIN, code);
        localBroadcastManager.sendBroadcast(intent);
    }


}