package com.asia.paint.biz.mine.settings.account.index;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityAccountIndexBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.CashService;
import com.asia.paint.base.network.bean.CashAccount;
import com.asia.paint.base.network.bean.CashAccountRsp;
import com.asia.paint.biz.mine.settings.account.BindCashAccountActivity;
import com.asia.paint.biz.mine.settings.account.CashAccountViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2020-01-07.
 */
public class AccountIndexActivity extends BaseTitleActivity<ActivityAccountIndexBinding> {

    private CashAccountViewModel mViewModel;

    @Override
    protected String middleTitle() {
        return "设置收款账户";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_index;
    }

    private CashAccount mZhiFuBaoAccount;
    private CashAccount mBankAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.layoutZhiFuBao.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BindCashAccountActivity.start(AccountIndexActivity.this, CashService.ZHI_FU_BAO, mZhiFuBaoAccount);
            }
        });
        mBinding.layoutBankAccount.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BindCashAccountActivity.start(AccountIndexActivity.this, CashService.BANK, mBankAccount);
            }
        });
        mViewModel = getViewModel(CashAccountViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.queryMyAccount().setCallback(this::setAccount);
    }

    private void setAccount(CashAccountRsp cashAccountRsp) {
        if (cashAccountRsp == null) {
            return;
        }
        List<CashAccount> accounts = cashAccountRsp.data;
        if (accounts == null || accounts.isEmpty()) {
            return;
        }
        for (CashAccount account : accounts) {
            if (account != null) {
                if (account.type == CashService.ZHI_FU_BAO) {
                    mZhiFuBaoAccount = account;
                    mBinding.tvZhiFuBaoAccount.setText(String.format("%s (%s)", account.name, account.account));
                } else if (account.type == CashService.BANK) {
                    mBankAccount = account;
                    mBinding.tvBankAccount.setText(String.format("%s (%s)", account.bank, account.account));
                }
            }
        }
    }
}
