package com.asia.paint.biz.order.mine.aftersale.order;

import android.os.Bundle;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityAfterSaleBinding;
import com.asia.paint.base.container.BaseActivity;
import com.asia.paint.base.network.bean.AfterSaleGoods;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.order.mine.aftersale.AfterSaleViewModel;
import com.asia.paint.biz.order.mine.aftersale.detail.AfterSaleDetailActivity;
import com.asia.paint.utils.callback.OnChangeCallback;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-08.
 */
public class OrderAfterSaleActivity extends BaseActivity<ActivityAfterSaleBinding> {

    private AfterSaleViewModel mSaleViewModel;
    private OrderAfterSaleAdapter mOrderAfterSaleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_after_sale;
    }

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected String getMiddleTitle() {
        return "售后";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSaleViewModel = getViewModel(AfterSaleViewModel.class);
        mSaleViewModel.resetPage();
        mBinding.rvAfterSale.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvAfterSale.addItemDecoration(new DefaultItemDecoration(14, 12, 14, 0));
        mBinding.rvAfterSale.setItemAnimator(null);
        mOrderAfterSaleAdapter = new OrderAfterSaleAdapter();
        mOrderAfterSaleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AfterSaleGoods goods = mOrderAfterSaleAdapter.getData(position);
            int id = view.getId();
            switch (id) {
                case R.id.tv_cancel:
                    mSaleViewModel.afterSaleOperation(goods.rec_id, 11, null, null).setCallback(new OnChangeCallback<Boolean>() {
                        @Override
                        public void onChange(Boolean result) {
                            if (result) {
                                mSaleViewModel.resetPage();
                                loadReturnList();
                            }
                        }
                    });
                    break;
                case R.id.tv_view_detail:
                    AfterSaleDetailActivity.start(OrderAfterSaleActivity.this, goods.rec_id);
                    break;
            }
        });
        mBinding.rvAfterSale.setAdapter(mOrderAfterSaleAdapter);
        mOrderAfterSaleAdapter.setOnLoadMoreListener(() -> {
            mSaleViewModel.autoAddPage();
            loadReturnList();
        }, mBinding.rvAfterSale);
        loadReturnList();
    }

    private void loadReturnList() {
        mSaleViewModel.loadReturnList().setCallback(result ->
                mSaleViewModel.updateListData(mOrderAfterSaleAdapter, result));
    }
}
