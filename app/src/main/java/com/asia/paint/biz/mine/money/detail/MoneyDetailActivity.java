package com.asia.paint.biz.mine.money.detail;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.biz.mine.index.MineViewModel;
import com.asia.paint.databinding.ActivityMoneyDetailBinding;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class MoneyDetailActivity extends BaseActivity<ActivityMoneyDetailBinding> {

    private MoneyDetailAdapter mMoneyDetailAdapter;
    private MineViewModel mViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_detail;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "余额明细";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(MineViewModel.class);
        mViewModel.resetPage();
        mBinding.rvMoneyDetail.setLayoutManager(new LinearLayoutManager(this));
        mMoneyDetailAdapter = new MoneyDetailAdapter();
        mMoneyDetailAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadMoneyDetail();
        }, mBinding.rvMoneyDetail);
        mBinding.rvMoneyDetail.setAdapter(mMoneyDetailAdapter);
        loadMoneyDetail();
    }

    private void loadMoneyDetail() {
        mViewModel.queryMoneyDetail().setCallback(result -> mViewModel.updateListData(mMoneyDetailAdapter, result));
    }
}
