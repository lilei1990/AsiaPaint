package com.asia.paint.biz.mine.money.detail;

import android.widget.TextView;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.RechargeDetail;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class MoneyDetailAdapter extends BaseGlideAdapter<RechargeDetail> {

    private static final int TYPE_RECHARGE = 1;
    private static final int TYPE_COST = 2;
    private static final int TYPE_RETURN = 3;

    public MoneyDetailAdapter() {
        super(R.layout.item_money_detail);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, RechargeDetail recharge) {
        if (recharge != null) {
            helper.setText(R.id.tv_money_description, recharge.memo);
            helper.setText(R.id.tv_money_date, recharge.pay_time);
            setMoneyText(helper, recharge);
        }
    }

    private void setMoneyText(GlideViewHolder helper, RechargeDetail recharge) {
        TextView textView = helper.getView(R.id.tv_money_value);
        String money = recharge.money;
        if (recharge.type == TYPE_COST) {
            money = String.format("-%s", money);
            textView.setSelected(false);
        } else if (recharge.type == TYPE_RECHARGE || recharge.type == TYPE_RETURN) {
            money = String.format("+%s", money);
            textView.setSelected(true);
        }
        textView.setText(money);
    }
}
