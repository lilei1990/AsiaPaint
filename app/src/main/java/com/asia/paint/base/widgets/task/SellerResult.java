package com.asia.paint.base.widgets.task;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ViewSellerResultBinding;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.biz.mine.seller.goals.SellerGoalsActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class SellerResult extends BaseFrameLayout<ViewSellerResultBinding> {
    public SellerResult(@NonNull Context context) {
        super(context);
    }

    public SellerResult(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SellerResult(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mBinding.layoutSellerResult.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, SellerGoalsActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_seller_result;
    }

    public void setYesterdayScore(String score) {
        mBinding.tvYesterdayScore.setText(score);
    }

    public void setYearScore(String score) {
        mBinding.tvYearScore.setText(score);
    }
}
