package com.asia.paint.biz.mine.receipt;

import android.content.Context;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.Receipt;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-11-12.
 */
public class ReceiptAdapter extends BaseGlideAdapter<Receipt> {

    public ReceiptAdapter(Context context) {
        super(context, R.layout.item_receipt);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Receipt receipt) {
        if (receipt != null) {
            helper.setText(R.id.tv_receipt_name, receipt.title);
            helper.setText(R.id.tv_receipt_no, receipt.sn);
            helper.setGone(R.id.tv_default_receipt, receipt.isDefault());
            helper.addOnClickListener(R.id.tv_edit_receipt, R.id.tv_del_receipt);
        }
    }

}
