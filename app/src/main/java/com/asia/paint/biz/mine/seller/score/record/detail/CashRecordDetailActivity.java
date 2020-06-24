package com.asia.paint.biz.mine.seller.score.record.detail;

import android.os.Bundle;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.databinding.ActivityCashRecordDetailBinding;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2020-01-12.
 */
public class CashRecordDetailActivity extends BaseTitleActivity<ActivityCashRecordDetailBinding> {
    @Override
    protected String middleTitle() {
        return "积分提现";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_record_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
