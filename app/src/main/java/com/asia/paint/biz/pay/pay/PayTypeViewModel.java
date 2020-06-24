package com.asia.paint.biz.pay.pay;


import static com.asia.paint.android.wxapi.WXPayEntryActivity.ACTION_WEI_XIN_PAY;
import static com.asia.paint.biz.pay.password.SetPayPwdActivity.ACTION_SET_PAY_PWD;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.asia.paint.android.wxapi.WXPayEntryActivity;
import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.PayService;
import com.asia.paint.base.network.bean.PayOrderInfo;
import com.asia.paint.base.widgets.MessageDialog;
import com.asia.paint.biz.mine.index.MineViewModel;
import com.asia.paint.biz.order.checkout.OrderSelectPayTypeDialog;
import com.asia.paint.biz.pay.PayDialog;
import com.asia.paint.biz.pay.PayViewModel;
import com.asia.paint.biz.pay.password.SetPayPwdActivity;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;


public class PayTypeViewModel extends BaseViewModel {

    private PayViewModel mPayViewModel;
    private CallbackDate<Boolean> mPayResult;
    private boolean mSetPayPwd;
    private String mAsiaMoney;
    private String mPayMoney;
    private int mOrderId;
    private Context mContext;
    private boolean mHasPayPWd;
    private OrderSelectPayTypeDialog mPayTypeDialog;

    public PayTypeViewModel(Context context, int orderId, String payMoney) {
        mContext = context;
        mOrderId = orderId;
        mPayMoney = payMoney;
        mPayResult = new CallbackDate<>();
        mPayViewModel = new PayViewModel();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter filter = new IntentFilter(ACTION_SET_PAY_PWD);
        localBroadcastManager.registerReceiver(new SetPayPwdBroadcastReceiver(), filter);
        IntentFilter weiXinPay = new IntentFilter(ACTION_WEI_XIN_PAY);
        localBroadcastManager.registerReceiver(new WeiXinPayBroadcastReceiver(), weiXinPay);
    }

    public CallbackDate<Boolean> startPay() {
        MineViewModel mineViewModel = new MineViewModel();
        mineViewModel.loadMineData().setCallback(result -> {
            if (result != null) {
                mAsiaMoney = result.money;
                mHasPayPWd = result.hasPayPwd();
                showPayTypeDialog();
            } else {
                mPayResult.setData(false);
            }
        });
        return mPayResult;
    }

    private void showPayTypeDialog() {

        mPayTypeDialog = OrderSelectPayTypeDialog.createInstance(mPayMoney, mAsiaMoney);
        mPayTypeDialog.setPayCallback(type -> {
            if (type == null) {
                mPayResult.setData(false);
                AppUtils.showMessage("请尽快完成支付");
                mPayTypeDialog.dismiss();
            } else {
                switch (type.pay) {
                    case BALANCE:
                        payViaBalance();
                        break;
                    case WEI_XIN:
                        payViaWeiXin();
                        break;
                    case ZHI_FU_BAO:
                        payViaZhiFuBao();
                        break;
                    case DEBIT_CARD:
                        payViaDebitCard();
                        break;
                }
            }

        });
        mPayTypeDialog.show(mContext);
    }

    public void onResume() {
        if (mSetPayPwd) {
            showPayPwdDialog();
            mSetPayPwd = false;
        }
    }

    /**
     * 余额支付
     */
    private void payViaBalance() {

        if (mHasPayPWd) {
            showPayPwdDialog();
        } else {
            new MessageDialog.Builder()
                    .title("支付密码")
                    .message("为了您的资金安全，请设置支付密码")
                    .setCancelButton("取消", null)
                    .setSureButton("确认", v ->
                            mContext.startActivity(new Intent(mContext, SetPayPwdActivity.class)))
                    .build()
                    .show(mContext);
        }
    }

    private void payViaWeiXin() {
        mPayViewModel.queryPayOrderInfo(mOrderId, PayService.PAY_WEI_XIN).setCallback(this::startWeiXinPay);

    }

    private void startWeiXinPay(PayOrderInfo payOrderInfo) {
        if (payOrderInfo == null) {
            return;
        }
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, payOrderInfo.appid);
        if (msgApi.isWXAppInstalled()) {
            msgApi.registerApp(payOrderInfo.appid);
            PayReq request = new PayReq();
            request.appId = payOrderInfo.appid;
            request.partnerId = payOrderInfo.partnerid;
            request.prepayId = payOrderInfo.prepayid;
            request.packageValue = payOrderInfo.packageInfo;
            request.nonceStr = payOrderInfo.noncestr;
            request.timeStamp = payOrderInfo.timestamp;
            request.sign = payOrderInfo.sign;
            msgApi.sendReq(request);
        } else {
            AppUtils.showMessage("请先安装微信");
        }
    }

    private void payViaZhiFuBao() {
        mPayViewModel.queryZhiFuBaoOrderInfo(mOrderId).setCallback(this::startZhiFuBaoPay);
    }

    private void startZhiFuBaoPay(String orderInfo) {
        Disposable subscribe = Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            PayResult payResult = new PayResult(result);
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            emitter.onNext(TextUtils.equals(resultStatus, "9000"));
        }).compose(new NetworkObservableTransformer<>())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mPayResult.setData(true);
                    }
                    AppUtils.showMessage(aBoolean ? "支付成功" : "支付失败");
                });
    }

    private void payViaDebitCard() {

    }

    private void showPayPwdDialog() {
        if (mPayTypeDialog != null) {
            mPayTypeDialog.dismissAllowingStateLoss();
        }
        PayDialog dialog = PayDialog.createInstance(mPayMoney, mAsiaMoney);
        dialog.setChangeCallback(pwd -> {
            if (TextUtils.isEmpty(pwd)) {
                mPayResult.setData(false);
                AppUtils.showMessage("请尽快完成支付");
            }
            mPayViewModel.payViaBalance(mOrderId, pwd).setCallback(result -> {
                if (result) {
                    mPayResult.setData(true);
                    dialog.dismiss();
                }
            });
        });
        dialog.show(mContext);
    }

    class SetPayPwdBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSetPayPwd = true;
        }
    }

    class WeiXinPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                boolean success = intent.getBooleanExtra(WXPayEntryActivity.KEY_WEI_XIN_PAY, false);
                if (success) {
                    mPayResult.setData(true);
                }
            }
        }
    }
}

