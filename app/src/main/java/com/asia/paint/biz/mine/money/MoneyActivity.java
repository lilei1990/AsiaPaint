package com.asia.paint.biz.mine.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityMoneyBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.MineDataRsp;
import com.asia.paint.biz.mine.index.MineViewModel;
import com.asia.paint.biz.mine.money.detail.MoneyDetailActivity;
import com.asia.paint.biz.mine.money.recharge.RechargeActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class MoneyActivity extends BaseActivity<ActivityMoneyBinding> {

    private MineViewModel mViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money;
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
        mViewModel = getViewModel(MineViewModel.class);
        setRightMenu("明细", new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(MoneyActivity.this, MoneyDetailActivity.class));
            }
        });
        mBinding.btnRecharge.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(MoneyActivity.this, RechargeActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadMineData().setCallback(new OnChangeCallback<MineDataRsp>() {
            @Override
            public void onChange(MineDataRsp result) {
                mBinding.tvMineMoney.setText(result.money);
            }
        });
    }
}
