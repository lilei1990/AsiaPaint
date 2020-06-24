package com.asia.paint.biz.mine.seller.monthly;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.Monthly;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.mine.seller.monthly.detail.MonthlyDetailActivity;
import com.asia.paint.databinding.ActivityMonthlyBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class MonthlyActivity extends BaseTitleActivity<ActivityMonthlyBinding> {

    private MonthlyAdapter mMonthlyAdapter;
    private MonthlyViewModel mMonthlyViewModel;
    private String mKeyword;


    private Runnable mSearchRunnable = new Runnable() {
        @Override
        public void run() {
            mMonthlyViewModel.resetPage();
            loadMonthly();
        }
    };

    @Override
    protected String middleTitle() {
        return "新闻中心";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_monthly;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMonthlyViewModel = getViewModel(MonthlyViewModel.class);
        mKeyword = "";
        mMonthlyViewModel.resetPage();
        mBinding.tvMonthlySort.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
            }
        });
        mBinding.rvMonthly.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvMonthly.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mMonthlyAdapter = new MonthlyAdapter();
        mMonthlyAdapter.setOnItemClickListener((adapter, view, position) -> {
            Monthly monthly = mMonthlyAdapter.getData(position);
            if (monthly != null) {
                MonthlyDetailActivity.start(MonthlyActivity.this, monthly.id);
            }
        });
        mMonthlyAdapter.setOnLoadMoreListener(() -> {
            mMonthlyViewModel.autoAddPage();
            loadMonthly();
        }, mBinding.rvMonthly);
        mBinding.rvMonthly.setAdapter(mMonthlyAdapter);
        mBinding.viewSearch.setChangeCallback(result -> {
            mKeyword = result;
            query();
        });
        loadMonthly();
    }

    private void loadMonthly() {
        mMonthlyViewModel.loadMonthly(mKeyword).setCallback(result
                -> mMonthlyViewModel.updateListData(mMonthlyAdapter, result));
    }

    private void query() {
        mBinding.rvMonthly.removeCallbacks(mSearchRunnable);
        mBinding.rvMonthly.postDelayed(mSearchRunnable, 500);
    }

    @Override
    protected void onDestroy() {
        mBinding.rvMonthly.removeCallbacks(mSearchRunnable);
        super.onDestroy();
    }
}
