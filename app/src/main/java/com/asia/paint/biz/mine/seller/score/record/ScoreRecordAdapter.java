package com.asia.paint.biz.mine.seller.score.record;

import android.graphics.Paint;
import android.widget.TextView;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.CashRecord;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class ScoreRecordAdapter extends BaseGlideAdapter<CashRecord> {

    public ScoreRecordAdapter() {
        super(R.layout.item_score_record);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, CashRecord record) {
        if (record != null) {
            helper.setText(R.id.tv_name, record.content);
            helper.setText(R.id.tv_date, record.add_time);
            helper.setText(R.id.tv_status, getStatus(record.status));
            helper.getView(R.id.tv_status).setSelected(record.status == 2);
            TextView tv = helper.getView(R.id.tv_receipt);
            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            helper.setVisible(R.id.tv_receipt, record.mode == 2 && record.status == 0);
            helper.addOnClickListener(R.id.tv_receipt);
        }
    }

    private String getStatus(int status) {
        String result = "";
        switch (status) {
            case 0:
            case 1:
                result = "待审核";
                break;
            case 2:
                result = "通过";
                break;
            case 3:
                result = "拒绝";
                break;
        }
        return result;
    }
}
