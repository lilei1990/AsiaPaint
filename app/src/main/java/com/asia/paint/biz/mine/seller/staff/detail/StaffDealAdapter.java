package com.asia.paint.biz.mine.seller.staff.detail;

import android.util.SparseArray;
import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.base.network.api.OrderService;
import com.asia.paint.base.network.bean.OrderDetail;
import com.asia.paint.base.network.bean.StaffDeal;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.FoldPanel;
import com.asia.paint.biz.order.mine.detail.OrderDetailActivity;
import com.asia.paint.utils.utils.DigitUtils;
import com.asia.paint.utils.utils.PriceUtils;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-30.
 */
public class StaffDealAdapter extends BaseGlideAdapter<StaffDeal> {

    public static SparseArray<String> mOrderStatusTips;

    static {
        mOrderStatusTips = new SparseArray<>();
        mOrderStatusTips.append(OrderService.ORDER_PAY, "等待买家付款");
        mOrderStatusTips.append(OrderService.ORDER_DELIVERY, "等待卖家发货");
        mOrderStatusTips.append(OrderService.ORDER_RECEIVE, "卖家已发货");
        mOrderStatusTips.append(OrderService.ORDER_COMMENT, "等待买家评价");
        mOrderStatusTips.append(OrderService.ORDER_CANCEL, "交易关闭");
        mOrderStatusTips.append(OrderService.ORDER_DONE, "交易成功");
    }
    public StaffDealAdapter() {
        super(R.layout.item_staff_deal);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, StaffDeal staffDeal) {
        if (staffDeal != null) {
            helper.setText(R.id.tv_date, staffDeal.add_time);
            helper.setText(R.id.tv_deal_status, mOrderStatusTips.get(staffDeal.order_status));
            FoldPanel<OrderDetail.Goods> foldPanel = helper.getView(R.id.view_fold_panel);
            foldPanel.setDefaultShowCount(2);
            StaffDealGoodsAdapter goodsAdapter = new StaffDealGoodsAdapter();
            foldPanel.setAdapter(goodsAdapter);
            foldPanel.setDatas(staffDeal._goods);
            setTotal(helper, staffDeal._goods);
        }
    }

    private void setTotal(GlideViewHolder helper, List<OrderDetail.Goods> goodsList) {
        int totalCount = 0;
        float totalMoney = 0;
        if (goodsList != null) {
            for (OrderDetail.Goods goods : goodsList) {
                if (goods != null) {
                    totalCount += goods.goods_numbers;
                    totalMoney += goods.goods_numbers * DigitUtils.parseFloat(goods.goods_price, 0);
                }
            }
        }
        helper.setText(R.id.tv_total_count, String.format("共%s件", totalCount));
        helper.setText(R.id.tv_total_price, PriceUtils.getPrice(String.valueOf(totalMoney)));
    }
}
