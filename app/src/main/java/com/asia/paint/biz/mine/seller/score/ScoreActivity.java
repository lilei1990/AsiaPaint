package com.asia.paint.biz.mine.seller.score;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.OtherService;
import com.asia.paint.biz.mine.seller.goals.WebViewActivity;
import com.asia.paint.biz.mine.seller.score.cash.CashActivity;
import com.asia.paint.biz.mine.seller.score.cash.CashTypeDialog;
import com.asia.paint.biz.mine.seller.score.detail.ScoreDetailActivity;
import com.asia.paint.biz.mine.seller.score.record.ScoreRecordActivity;
import com.asia.paint.databinding.ActivityScoreBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class ScoreActivity extends BaseTitleActivity<ActivityScoreBinding> {

    private ScoreViewModel mScoreViewModel;
    private String mReceipt = "0";

    @Override
    protected String middleTitle() {
        return "积分";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_score;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScoreViewModel = getViewModel(ScoreViewModel.class);
        mBaseBinding.tvRightText.setText("明细");
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(ScoreActivity.this, ScoreDetailActivity.class));
            }
        });
        mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_1054CB));
        mScoreViewModel.queryScoreDetail().setCallback(result -> {
            if (result == null) {
                return;
            }
            if (result.score_info != null) {
                mBinding.tvScore.setText(String.valueOf(result.score_info.my_score));
            }
            mReceipt = result.receipt;
            mBinding.tvReceipt.setText(mReceipt);
        });
        mBinding.btnRecord.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startActivity(new Intent(ScoreActivity.this, ScoreRecordActivity.class));
            }
        });
        mBinding.btnToMoney.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                CashTypeDialog dialog = CashTypeDialog.createInstance();
                dialog.setCallback(result -> {
                    if (result == null) {
                        return;
                    }
                    dialog.dismiss();
                    CashActivity.start(ScoreActivity.this, result.type, mReceipt);
                });
                dialog.show(ScoreActivity.this);
                //AppUtils.showMessage("积分为" + mBinding.tvScore.getText().toString());
            }
        });
        mBinding.tvScoreTips.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                WebViewActivity.start(ScoreActivity.this, OtherService.SCORE_RULE);
            }
        });

        mBinding.tvTaxTips.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                WebViewActivity.start(ScoreActivity.this, OtherService.TAX_RULE);
            }
        });
    }
}
