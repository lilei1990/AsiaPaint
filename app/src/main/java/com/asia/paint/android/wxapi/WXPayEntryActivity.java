package com.asia.paint.android.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.asia.paint.android.R;
import com.asia.paint.base.constants.Constants;
import com.asia.paint.utils.utils.AppUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String ACTION_WEI_XIN_PAY = "ACTION_WEI_XIN_PAY";
    public static final String KEY_WEI_XIN_PAY = "KEY_WEI_XIN_PAY";

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
    public void onResp(BaseResp resp) {
        int result;
        boolean success = false;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                success = true;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        AppUtils.showMessage(getString(result));
        sendResult(success);
        finish();
    }

    private void sendResult(boolean result) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent(ACTION_WEI_XIN_PAY);
        intent.putExtra(KEY_WEI_XIN_PAY, result);
        localBroadcastManager.sendBroadcast(intent);
    }


}