package com.asia.paint.biz.order.mine.aftersale.apply;

import com.asia.paint.R;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CheckBox;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-27.
 */
public class AfterSaleReasonAdapter extends BaseGlideAdapter<String> {

    private int mCheckPosition = -1;

    public AfterSaleReasonAdapter() {
        super(R.layout.item_after_sale_reason);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, String item) {
        helper.setText(R.id.tv_reason, item);
        CheckBox checkBox = helper.getView(R.id.cb_check);
        checkBox.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                setOnItemClick(helper.itemView, helper.getAdapterPosition());
            }

            @Override
            public boolean changeBySelf() {
                return false;
            }
        });
        checkBox.setChecked(helper.getAdapterPosition() == mCheckPosition);
    }


    public int getCheckPosition() {
        return mCheckPosition;
    }

    public void setCheckPosition(int checkPosition) {
        mCheckPosition = checkPosition;
        notifyDataSetChanged();
    }

}
