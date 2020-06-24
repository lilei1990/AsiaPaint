package com.asia.paint.biz.order.checkout;

import android.graphics.Color;
import android.text.TextUtils;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.OrderCheckout;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class OrderCheckoutAdapter extends BaseGlideAdapter<OrderCheckout> {
    public OrderCheckoutAdapter() {
        super(R.layout.item_order_checkout);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, OrderCheckout item) {
        if (item != null) {
            helper.setText(R.id.tv_name, item.name);
            helper.setText(R.id.tv_value_black, item.value);
            helper.setText(R.id.tv_value_red, item.value);
            helper.setGone(R.id.tv_value_black, item.color == OrderCheckout.Color.BLACK);
            helper.setGone(R.id.tv_value_red, item.color == OrderCheckout.Color.RED);
            if (!TextUtils.isEmpty(item.colorStr)) {
                int color = 0;
                try {
                    color = Color.parseColor(item.colorStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (color != 0) {
                    helper.setTextColor(R.id.tv_value_red, color);
                    helper.setGone(R.id.tv_value_red, true);
                }
            }
        }
    }

    public float getFinalPrice() {
        float finalPrice = 0;
        List<OrderCheckout> data = getData();
        for (OrderCheckout checkout : data) {
            if (checkout != null) {
                finalPrice += checkout.price;
            }
        }
        if (finalPrice < 0) {
            finalPrice = 0;
        }
        return finalPrice;
    }
}
