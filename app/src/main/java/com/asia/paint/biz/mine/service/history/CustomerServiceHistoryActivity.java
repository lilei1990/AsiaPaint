package com.asia.paint.biz.mine.service.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.biz.mine.service.CustomerServiceAdapter;
import com.asia.paint.biz.mine.service.CustomerServiceViewModel;
import com.asia.paint.databinding.ActivityCustomerHistoryServiceBinding;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class CustomerServiceHistoryActivity extends BaseTitleActivity<ActivityCustomerHistoryServiceBinding> {

    private static final String KEY_CUSTOMER_SERVICE_ID = "KEY_CUSTOMER_SERVICE_ID";
    private int mServiceId;
    private CustomerServiceAdapter mCustomerServiceAdapter;
    private CustomerServiceViewModel mViewModel;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, CustomerServiceHistoryActivity.class);
        intent.putExtra(KEY_CUSTOMER_SERVICE_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mServiceId = intent.getIntExtra(KEY_CUSTOMER_SERVICE_ID, 0);
    }

    @Override
    protected String middleTitle() {
        return "历史记录";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_history_service;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = getViewModel(CustomerServiceViewModel.class);
        mViewModel.resetPage();
        mBinding.rvServiceMsg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        mBinding.rvServiceMsg.setItemAnimator(null);
        mCustomerServiceAdapter = new CustomerServiceAdapter();
        mCustomerServiceAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadHistory();
        }, mBinding.rvServiceMsg);
        mBinding.rvServiceMsg.setAdapter(mCustomerServiceAdapter);
        loadHistory();
    }

    private void loadHistory() {
        mViewModel.getHistoryMsg().setCallback(result -> {
            mViewModel.updateListData(mCustomerServiceAdapter, result, true);
            mBinding.rvServiceMsg.scrollToPosition(0);
        });
    }

}
