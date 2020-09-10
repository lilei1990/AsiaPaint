package com.asia.paint.biz.mine.seller.score.cash;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogCashTypeBinding;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.base.network.api.CashService;
import com.asia.paint.biz.mine.money.recharge.RechargeType;
import com.asia.paint.biz.mine.money.recharge.RechargeTypeAdapter;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class CashTypeDialog extends BaseBottomDialogFragment<DialogCashTypeBinding> {

    private RechargeTypeAdapter mRechargeTypeAdapter;
    private OnChangeCallback<RechargeType> mPayCallback;

    public static CashTypeDialog createInstance() {
        CashTypeDialog fragment = new CashTypeDialog();
     /*   Bundle bundle = new Bundle();
        bundle.putString(KEY_PAY_MONEY, money);
        bundle.putString(KEY_ASIA_MONEY, asiaMoney);
        fragment.setArguments(bundle);*/
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_cash_type;
    }

    @Override
    protected int getDialogHeight() {
        return AppUtils.dp2px(350);
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
                    Toast.makeText(mContext, "请先选择提现方式", Toast.LENGTH_SHORT).show();
                } else {
                    mPayCallback.onChange(checkRechargeType);
                }
            }
        });
    }

    private List<RechargeType> getPayType() {
        List<RechargeType> types = new ArrayList<>();

//        RechargeType zhiFuBao = new RechargeType();
//        zhiFuBao.name = "支付宝";
//        zhiFuBao.type = CashService.ZHI_FU_BAO;
//        zhiFuBao.pay = RechargeType.Pay.ZHI_FU_BAO;
//        zhiFuBao.iconId = R.mipmap.ic_mine_zhifubao;
//        types.add(zhiFuBao);
/*        RechargeType weiXin = new RechargeType();
        weiXin.name = "微信支付";
        weiXin.pay = RechargeType.Pay.WEI_XIN;
        weiXin.iconId = R.mipmap.ic_mine_weixin;
        types.add(weiXin);*/

        RechargeType bank = new RechargeType();
        bank.name = "银行卡";
        bank.type = CashService.BANK;
        bank.pay = RechargeType.Pay.DEBIT_CARD;
//        bank.description = "由网易支付提供服务";
        bank.iconId = R.mipmap.ic_mine_bank;
        types.add(bank);
        return types;
    }

    public void setCallback(OnChangeCallback<RechargeType> payCallback) {
        mPayCallback = payCallback;
    }
}
