package com.asia.paint.biz.mine.seller.monthly;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.Monthly;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class MonthlyAdapter extends BaseGlideAdapter<Monthly> {

    public MonthlyAdapter() {
        super(R.layout.item_monthly);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Monthly monthly) {

        if (monthly != null) {
            helper.loadRoundedCornersImage(R.id.iv_icon, monthly.image, 4);
            helper.setGone(R.id.iv_new_flag, true);
            helper.setGone(R.id.iv_hot_flag, true);
            helper.setText(R.id.tv_name, monthly.name);
            helper.setText(R.id.tv_update_time, String.format("更新时间：%s", monthly.add_time));
            helper.setText(R.id.tv_view_count, monthly.view);
        }
    }
}
