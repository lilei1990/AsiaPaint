package com.asia.paint.biz.mine.seller.score.detail;

import android.text.TextUtils;
import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.ScoreDetail;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class ScoreDetailAdapter extends BaseGlideAdapter<ScoreDetail> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_COST = 2;

    public ScoreDetailAdapter() {
        super(R.layout.item_score_detail);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, ScoreDetail detail) {
        if (detail != null) {
            helper.setText(R.id.tv_money_description, detail.memo);
            helper.setText(R.id.tv_money_date, detail.createtime);
            setMoneyText(helper, detail);
        }
    }

    private void setMoneyText(GlideViewHolder helper, ScoreDetail detail) {
        TextView textView = helper.getView(R.id.tv_money_value);
        String value = "";
        if (detail.score > 0) {
            value = String.valueOf(detail.score);
        } else if (!TextUtils.isEmpty(detail.receipt)) {
            value = detail.receipt;
        }

        if (detail.type == TYPE_COST) {
            value = String.format("-%s", value);
            textView.setSelected(false);
        } else if (detail.type == TYPE_ADD) {
            value = String.format("+%s", value);
            textView.setSelected(true);
        }
        textView.setText(value);
    }
}
