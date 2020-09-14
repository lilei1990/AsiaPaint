package com.asia.paint.biz.mine.money.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityRechargeBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.api.MoneyService;
import com.asia.paint.base.network.bean.PayOrderInfo;
import com.asia.paint.base.network.bean.YinlianBean;
import com.asia.paint.base.util.MoneyFilter;
import com.asia.paint.biz.pay.pay.PayResult;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DigitUtils;
import com.chinapay.mobilepayment.activity.MainActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class RechargeActivity extends BaseActivity<ActivityRechargeBinding> {


    private RechargeTypeAdapter mRechargeTypeAdapter;
    private RechargeViewModel mRechargeViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "充值";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRechargeViewModel = getViewModel(RechargeViewModel.class);
        mBinding.etRechargeValue.setFilters(new InputFilter[]{new MoneyFilter()});
        mBinding.ivRechargeClear.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mBinding.etRechargeValue.setText("");
            }
        });
        mBinding.rvRechargeType.setLayoutManager(new LinearLayoutManager(this));
        List<RechargeType> payType = getRechargeType();
        mRechargeTypeAdapter = new RechargeTypeAdapter(payType, payType.get(0));
        mBinding.rvRechargeType.setAdapter(mRechargeTypeAdapter);
        mRechargeTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            RechargeType rechargeType = mRechargeTypeAdapter.getItem(position);
            mRechargeTypeAdapter.setCheckRechargeType(rechargeType);
        });
        mBinding.btnRecharge.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                RechargeType checkRechargeType = mRechargeTypeAdapter.getCheckRechargeType();
                if (checkRechargeType == null) {
                    Toast.makeText(RechargeActivity.this, "请先选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                String money = getText(mBinding.etRechargeValue);
                float value = DigitUtils.parseFloat(money);
                if (value > 0) {
                    loadRechargeOrder(checkRechargeType.type, money);
                } else {
                    AppUtils.showMessage("充值金额不能为0");
                }
            }
        });
    }

    private void loadRechargeOrder(int type, String money) {
        switch (type) {
            case MoneyService.PAY_ZHI_FU_BAO:
                mRechargeViewModel.rechargeByZhiFuBao(money).setCallback(this::startZhiFuBaoPay);
                break;
            case MoneyService.PAY_WEI_XIN:
                mRechargeViewModel.rechargeWeiXin(money).setCallback(this::startWeiXinPay);
                break;
            case MoneyService.PAY_BANK:
                mRechargeViewModel.rechargeByYinlian(money).setCallback(this::startYinlian);
                break;

        }
    }

    private void startYinlian(YinlianBean orderInfo) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("MerId",orderInfo.MerId);
            jsonObject.put("MerOrderNo",orderInfo.MerOrderNo);
            jsonObject.put("OrderAmt",orderInfo.OrderAmt+"");
            jsonObject.put("TranDate",orderInfo.TranDate);
            jsonObject.put("TranTime",orderInfo.TranTime);
            jsonObject.put("BusiType",orderInfo.BusiType);
            jsonObject.put("Version",orderInfo.Version);
            jsonObject.put("CurryNo",orderInfo.CurryNo);
            jsonObject.put("AccessType",orderInfo.AccessType);
            jsonObject.put("AcqCode",orderInfo.AcqCode);
            jsonObject.put("MerPageUrl",orderInfo.MerPageUrl);
            jsonObject.put("MerBgUrl",orderInfo.MerBgUrl);
            jsonObject.put("MerResv",orderInfo.MerResv);
            jsonObject.put("Signature",orderInfo.Signature);
            Intent intent = new Intent(mContext, MainActivity.class);//第二步. 设置Intent指向MainActivity.class
            intent.putExtra("orderInfo", jsonObject.toString());
            intent.putExtra("mode", "00");//mode 03测试 00生产
            // orderInfo为Json字符串。
            mContext.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG","异常原因="+e.toString());
        }

    }

    private void startWeiXinPay(PayOrderInfo payOrderInfo) {
        if (payOrderInfo == null) {
            return;
        }
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, payOrderInfo.appid);
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

    private void startZhiFuBaoPay(String orderInfo) {
        Disposable subscribe = Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            PayTask alipay = new PayTask(this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            PayResult payResult = new PayResult(result);
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            emitter.onNext(TextUtils.equals(resultStatus, "9000"));
        }).compose(new NetworkObservableTransformer<>())
                .subscribe(aBoolean -> AppUtils.showMessage(aBoolean ? "充值成功" : "充值失败"));
    }

    private List<RechargeType> getRechargeType() {
        List<RechargeType> types = new ArrayList<>();
        RechargeType zhiFuBao = new RechargeType();
        zhiFuBao.name = "支付宝";
        zhiFuBao.type = MoneyService.PAY_ZHI_FU_BAO;
        zhiFuBao.iconId = R.mipmap.ic_mine_zhifubao;
        types.add(zhiFuBao);

        RechargeType weiXin = new RechargeType();
        weiXin.name = "微信支付";
        weiXin.iconId = R.mipmap.ic_mine_weixin;
        weiXin.type = MoneyService.PAY_WEI_XIN;
        types.add(weiXin);

        RechargeType bank = new RechargeType();
        bank.name = "银行卡快捷支付";
        bank.description = "由网易支付提供服务";
        bank.iconId = R.mipmap.ic_mine_bank;
        bank.type = MoneyService.PAY_BANK;
        types.add(bank);
        return types;
    }


}
