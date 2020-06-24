package com.asia.paint.base.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.asia.paint.R;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.biz.order.mine.aftersale.order.OrderAfterSaleActivity;
import com.asia.paint.biz.order.mine.OrderMineActivity;
import com.asia.paint.databinding.ViewMineOrderBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class MineOrderLayout extends FrameLayout {

    private ViewMineOrderBinding mBinding;

    public MineOrderLayout(@NonNull Context context) {
        this(context, null);
    }

    public MineOrderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineOrderLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_mine_order, this, true);
        init();
    }

    private void init() {
        mBinding.tvAllOrder.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                OrderMineActivity.start(getContext(), OrderService.ORDER_ALL);
            }
        });
        mBinding.layoutPay.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                OrderMineActivity.start(getContext(), OrderService.ORDER_PAY);
            }
        });
        mBinding.layoutDelivery.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                OrderMineActivity.start(getContext(), OrderService.ORDER_DELIVERY);
            }
        });
        mBinding.layoutReceive.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                OrderMineActivity.start(getContext(), OrderService.ORDER_RECEIVE);
            }
        });
        mBinding.layoutComment.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                OrderMineActivity.start(getContext(), OrderService.ORDER_COMMENT);
            }
        });
        mBinding.layoutAfterSale.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                getContext().startActivity(new Intent(getContext(), OrderAfterSaleActivity.class));
            }
        });
    }

    public void setPayMsgCount(int count) {
        mBinding.tvPayMsg.setText(String.valueOf(count));
        mBinding.tvPayMsg.setVisibility(count > 0 ? VISIBLE : INVISIBLE);
    }

    public void setDeliveryMsgCount(int count) {
        mBinding.tvDeliveryMsg.setText(String.valueOf(count));
        mBinding.tvDeliveryMsg.setVisibility(count > 0 ? VISIBLE : INVISIBLE);
    }

    public void setReceiveMsgCount(int count) {
        mBinding.tvReceiveMsg.setText(String.valueOf(count));
        mBinding.tvReceiveMsg.setVisibility(count > 0 ? VISIBLE : INVISIBLE);
    }

    public void setCommentMsgCount(int count) {
        mBinding.tvCommentMsg.setText(String.valueOf(count));
        mBinding.tvCommentMsg.setVisibility(count > 0 ? VISIBLE : INVISIBLE);
    }

    public void setDAfterSaleMsgCount(int count) {
        mBinding.tvAfterSaleMsg.setText(String.valueOf(count));
        mBinding.tvAfterSaleMsg.setVisibility(count > 0 ? VISIBLE : INVISIBLE);
    }


}
