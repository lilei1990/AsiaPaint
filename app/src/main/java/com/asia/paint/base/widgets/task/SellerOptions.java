package com.asia.paint.base.widgets.task;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseFrameLayout;
import com.asia.paint.biz.mine.seller.auth.ApplyResellActivity;
import com.asia.paint.biz.mine.seller.meeting.MeetingActivity;
import com.asia.paint.biz.mine.seller.monthly.MonthlyActivity;
import com.asia.paint.biz.mine.seller.staff.StaffActivity;
import com.asia.paint.biz.mine.seller.train.TrainActivity;
import com.asia.paint.databinding.ViewSellerOptionsBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class SellerOptions extends BaseFrameLayout<ViewSellerOptionsBinding> {
    public SellerOptions(@NonNull Context context) {
        super(context);
    }

    public SellerOptions(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SellerOptions(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mBinding.layoutApplySeller.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, ApplyResellActivity.class));
            }
        });
        mBinding.tvStaff.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, StaffActivity.class));
            }
        });
        mBinding.tvTrain.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, TrainActivity.class));
            }
        });
        mBinding.tvMeeting.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, MeetingActivity.class));
            }
        });
        mBinding.tvMonthly.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mContext.startActivity(new Intent(mContext, MonthlyActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_seller_options;
    }

    public void setIsSeller(boolean isSeller) {
        mBinding.layoutApplySeller.setVisibility(isSeller ? GONE : VISIBLE);
        mBinding.tvMySeller.setVisibility(isSeller ? VISIBLE : GONE);
        mBinding.layoutOption.setVisibility(isSeller ? VISIBLE : GONE);
        mBinding.tvMonthly.setVisibility(isSeller ? VISIBLE : GONE);
    }
}
