package com.asia.paint.biz.mine.seller.train;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Train;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class TrainAdapter extends BaseGlideAdapter<Train> {
    public TrainAdapter() {
        super(R.layout.item_train);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Train train) {
        if (train != null) {
            helper.loadRoundedCornersImage(R.id.iv_icon, train.image, 4);
            helper.setText(R.id.tv_name, train.name);
            helper.setText(R.id.tv_hot_count, train.view);
            helper.setText(R.id.tv_description, train.desc);
        }
    }
}
