package com.asia.paint.biz.order.checkout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogOrderSelectPayTypeBinding;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.biz.mine.money.recharge.RechargeType;
import com.asia.paint.biz.mine.money.recharge.RechargeTypeAdapter;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.PriceUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class OrderSelectPayTypeDialog extends BaseBottomDialogFragment<DialogOrderSelectPayTypeBinding> {

    private static final String KEY_PAY_MONEY = "KEY_PAY_MONEY";
    private static final String KEY_ASIA_MONEY = "KEY_ASIA_MONEY";
    private RechargeTypeAdapter mRechargeTypeAdapter;
    private OnChangeCallback<RechargeType> mPayCallback;
    private String mMoney;
    //账户余额
    private String mAsiaMoney;
    private int mShow_ye = 1;
    public static OrderSelectPayTypeDialog createInstance(String money, String asiaMoney) {
        OrderSelectPayTypeDialog fragment = new OrderSelectPayTypeDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PAY_MONEY, money);
        bundle.putString(KEY_ASIA_MONEY, asiaMoney);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mMoney = bundle.getString(KEY_PAY_MONEY);
        mAsiaMoney = bundle.getString(KEY_ASIA_MONEY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_order_select_pay_type;
    }

    @Override
    protected int getDialogHeight() {
        return AppUtils.dp2px(500);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    protected void initView() {
        mBinding.ivClose.setOnClickListener(v -> {
            dismiss();
            if (mPayCallback != null) {
                mPayCallback.onChange(null);
            }
        });
        mBinding.tvMoney.setText(mMoney);
        mBinding.rvOrderPay.setLayoutManager(new LinearLayoutManager(mContext));
        List<RechargeType> payType = getPayType();
        mRechargeTypeAdapter = new RechargeTypeAdapter(payType, payType.get(0));
        mBinding.rvOrderPay.setAdapter(mRechargeTypeAdapter);
        mRechargeTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            RechargeType rechargeType = mRechargeTypeAdapter.getItem(position);
            mRechargeTypeAdapter.setCheckRechargeType(rechargeType);
        });

        mBinding.tvSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                RechargeType checkRechargeType = mRechargeTypeAdapter.getCheckRechargeType();
                if (checkRechargeType == null) {
                    Toast.makeText(mContext, "请先选择支付方式", Toast.LENGTH_SHORT).show();
                } else {
                    mPayCallback.onChange(checkRechargeType);
                }
            }
        });
    }

    private List<RechargeType> getPayType() {
        List<RechargeType> types = new ArrayList<>();

        RechargeType asia = new RechargeType();
        if (TextUtils.isEmpty(mAsiaMoney)) {
            mAsiaMoney = "0.0";
        }
        //为0不支持余额支付
        if (mShow_ye!=0) {
            asia.name = "账户余额:" + PriceUtils.getPrice(mAsiaMoney);
            asia.pay = RechargeType.Pay.BALANCE;
            asia.iconId = R.mipmap.ic_asia_pay;
            types.add(asia);
        }
        RechargeType zhiFuBao = new RechargeType();
        zhiFuBao.name = "支付宝";
        zhiFuBao.pay = RechargeType.Pay.ZHI_FU_BAO;
        zhiFuBao.iconId = R.mipmap.ic_mine_zhifubao;
        types.add(zhiFuBao);
        RechargeType weiXin = new RechargeType();
        weiXin.name = "微信支付";
        weiXin.pay = RechargeType.Pay.WEI_XIN;
        weiXin.iconId = R.mipmap.ic_mine_weixin;
        types.add(weiXin);

        RechargeType bank = new RechargeType();
        bank.name = "银行卡快捷支付";
        bank.pay = RechargeType.Pay.DEBIT_CARD;
        bank.description = "由网易支付提供服务";
        bank.iconId = R.mipmap.ic_mine_bank;
        types.add(bank);
        return types;
    }

    public void setShow_ye(int show_ye) {
        mShow_ye = show_ye;
    }

    public void setPayCallback(OnChangeCallback<RechargeType> payCallback) {
        mPayCallback = payCallback;
    }
}
