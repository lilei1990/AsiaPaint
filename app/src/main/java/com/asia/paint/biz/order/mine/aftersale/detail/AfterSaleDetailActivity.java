package com.asia.paint.biz.order.mine.aftersale.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.ReturnDetail;
import com.asia.paint.base.util.ImageUtils;
import com.asia.paint.biz.order.mine.aftersale.AfterSaleViewModel;
import com.asia.paint.biz.order.mine.aftersale.apply.SelectAfterSaleTypeActivity;
import com.asia.paint.databinding.ActivityAfterSaleDetailBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-28.
 */
public class AfterSaleDetailActivity extends BaseTitleActivity<ActivityAfterSaleDetailBinding> {

    private static final String KEY_RETURN_GOODS_ID = "KEY_RETURN_GOODS_ID";
    private SparseArray<String> mType;
    private SparseArray<String> mReturnStatus;
    private int mRecId;
    AfterSaleViewModel mSaleViewModel;


    {
        mType = new SparseArray<>();
        mType.put(SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY, "退款");
        mType.put(SelectAfterSaleTypeActivity.TYPE_RETURN_GOODS, "退货");
        mType.put(SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE, "换货");
        mReturnStatus = new SparseArray<>();
        mReturnStatus.put(1, "申请中");
        mReturnStatus.put(2, "审核通过");
        mReturnStatus.put(3, "邮寄中");
        mReturnStatus.put(4, "卖家已收货");
        mReturnStatus.put(5, "卖家已发货");
        mReturnStatus.put(8, "退款成功");
        mReturnStatus.put(-1, "已拒绝");
        mReturnStatus.put(11, "已取消");

    }

    public static void start(Context context, int recId) {
        Intent intent = new Intent(context, AfterSaleDetailActivity.class);
        intent.putExtra(KEY_RETURN_GOODS_ID, recId);
        context.startActivity(intent);
    }

    @Override
    protected void intentValue(Intent intent) {
        mRecId = intent.getIntExtra(KEY_RETURN_GOODS_ID, 0);
    }

    @Override
    protected String middleTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_after_sale_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSaleViewModel = getViewModel(AfterSaleViewModel.class);
        loadReturnDetail();
    }

    private void loadReturnDetail() {
        mSaleViewModel.queryReturnDetail(mRecId).setCallback(this::updateReturnDetailRsp);
    }

    private void updateReturnDetailRsp(ReturnDetail goods) {
        if (goods == null) {
            return;
        }
        setAfterSaleTitle(goods.type);
        mBinding.tvReturnStatus.setText(mReturnStatus.get(goods.status));
        if (goods.type == 3 && goods.status == 8) {
            mBinding.tvReturnStatus.setText("买家确认收货");
        }
        mBinding.tvReturnStatusTips.setText(goods.status_text);
        mBinding.tvReturnDate.setText(goods.updatetime);

        ImageUtils.load(mBinding.ivIcon, goods.goods_image);
        mBinding.tvName.setText(goods.goods_name);
        String spec = "";
        if (goods.type == SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE) {
            spec = "原规格：" + goods.specification;
        } else {
            spec = "规格：" + goods.specification;
        }
        mBinding.tvGoodsSpec.setText(spec);
        mBinding.tvReason.setText(String.format("%s原因：%s", mType.get(goods.type), goods.reson));
        mBinding.tvReturnMoney.setText(String.format("退款金额：%s", goods.money));
        mBinding.tvReturnMoney.setVisibility(goods.type != SelectAfterSaleTypeActivity.TYPE_RETURN_EXCHANGE ? View.VISIBLE : View.GONE);
        mBinding.tvCount.setText(String.format("申请件数：%s", goods.num));
        mBinding.tvDate.setText(String.format("申请时间：%s", goods.add_time));
        mBinding.tvSn.setText(String.format("%s编号：%s", mType.get(goods.type), goods.order_sn));
        mBinding.tvRemake.setText(String.format("%s说明：%s", mType.get(goods.type), goods.desc));
        mBinding.tvAddress.setText(String.format("收货地址：%s", ""));
        showCancelBtn(goods);
        showDeliveryBtn(goods);
    }

    private void showCancelBtn(ReturnDetail detail) {
        boolean show = detail.status == 1 || detail.status == 2;
        mBinding.tvCancel.setVisibility(show ? View.VISIBLE : View.GONE);
        mBinding.tvCancel.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mSaleViewModel.afterSaleOperation(detail.rec_id, 11, null, null).setCallback(result -> {
                    if (result) {
                        loadReturnDetail();
                    }
                });
            }
        });
    }

    private void showDeliveryBtn(ReturnDetail detail) {
        boolean show = detail.type != SelectAfterSaleTypeActivity.TYPE_RETURN_MONEY && detail.status == 2;
        mBinding.tvDeliveryInfo.setVisibility(show ? View.VISIBLE : View.GONE);
        mBinding.tvDeliveryInfo.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                AddDeliveryDialog dialog = new AddDeliveryDialog();
                dialog.setPairCallback((sn, company) ->
                        mSaleViewModel.afterSaleOperation(detail.rec_id, 3, company, sn).setCallback(
                                result -> {
                                    if (result) {
                                        loadReturnDetail();
                                    }
                                }));
                dialog.show(AfterSaleDetailActivity.this);
            }
        });
    }

    private void setAfterSaleTitle(int type) {
        String title = mType.get(type);
        mBaseBinding.tvTitle.setText(String.format("%s详情", title));
        mBinding.tvReturnInfo.setText(String.format("%s信息", title));
    }
}
