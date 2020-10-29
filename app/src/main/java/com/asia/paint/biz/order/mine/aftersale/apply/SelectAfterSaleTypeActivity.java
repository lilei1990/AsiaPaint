package com.asia.paint.biz.order.mine.aftersale.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivitySelectAfterSaleTypeBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.mine.service.CustomerServiceActivity;
import com.asia.paint.biz.mine.vip.TR_IMActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.blankj.utilcode.util.ActivityUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-26.
 */
public class SelectAfterSaleTypeActivity extends BaseTitleActivity<ActivitySelectAfterSaleTypeBinding> {
    private static final String KEY_APPLY_AFTER_SALE = "KEY_APPLY_AFTER_SALE";
    private static final String KEY_AFTER_SALE_OVERDUE = "KEY_AFTER_SALE_OVERDUE";
    private OrderDetail.Goods mGoods;
    private boolean mOverdue;

    public static final int TYPE_RETURN_MONEY = 2;
    public static final int TYPE_RETURN_GOODS = 1;
    public static final int TYPE_RETURN_EXCHANGE = 3;

    public static void start(Context context, OrderDetail.Goods goods, boolean overdue) {
        Intent intent = new Intent(context, SelectAfterSaleTypeActivity.class);
        if (goods != null) {
            intent.putExtra(KEY_APPLY_AFTER_SALE, goods);
        }
        intent.putExtra(KEY_AFTER_SALE_OVERDUE, overdue);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mGoods = intent.getParcelableExtra(KEY_APPLY_AFTER_SALE);
        mOverdue = intent.getBooleanExtra(KEY_AFTER_SALE_OVERDUE, false);
    }

    @Override
    protected String middleTitle() {
        return "选择服务类型";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_after_sale_type;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding.tvRightText.setText("客服");
        mBaseBinding.tvRightText.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
//                CustomerServiceActivity.start(SelectAfterSaleTypeActivity.this, 0);
                ActivityUtils.startActivity(TR_IMActivity.class);
            }

        });
        mBaseBinding.tvRightText.setTextColor(AppUtils.getColor(R.color.color_185ACF));
        mBinding.layoutReturn.setVisibility(mOverdue ? View.GONE : View.VISIBLE);
        mBinding.tvReturnOverdue.setVisibility(mOverdue ? View.VISIBLE : View.GONE);
        if (mGoods != null) {
            ImageUtils.load(mBinding.ivGoodsImg, mGoods.goods_image);
            mBinding.tvGoodsName.setText(mGoods.goods_name);
            mBinding.tvGoodsSpec.setText(String.format("规格：%s", mGoods.specification));

        }
        mBinding.layoutReturnMoney.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startAfterSale(TYPE_RETURN_MONEY);
            }
        });
        mBinding.layoutReturnGoods.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startAfterSale(TYPE_RETURN_GOODS);
            }
        });
        mBinding.layoutExchange.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                startAfterSale(TYPE_RETURN_EXCHANGE);
            }
        });
    }

    private void startAfterSale(int type) {
        ApplyAfterSaleActivity.start(this, type, mGoods);
        finish();
    }

}
