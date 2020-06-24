package com.asia.paint.biz.mine.seller.score.detail;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.biz.mine.seller.score.ScoreViewModel;
import com.asia.paint.databinding.ActivityScoreDetailBinding;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class ScoreDetailActivity extends BaseTitleActivity<ActivityScoreDetailBinding> {

    private ScoreDetailAdapter mScoreDetailAdapter;
    private ScoreViewModel mViewModel;

    @Override
    protected String middleTitle() {
        return "积分明细";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel(ScoreViewModel.class);
        mViewModel.resetPage();
        mBinding.rvScoreDetail.setLayoutManager(new LinearLayoutManager(this));
        mScoreDetailAdapter = new ScoreDetailAdapter();
        mScoreDetailAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadScoreDetail();
        }, mBinding.rvScoreDetail);
        mBinding.rvScoreDetail.setAdapter(mScoreDetailAdapter);
        loadScoreDetail();
    }

    private void loadScoreDetail() {
        mViewModel.queryScoreDetail().setCallback(result -> mViewModel.updateListData(mScoreDetailAdapter, result));
    }
}
