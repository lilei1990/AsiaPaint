package com.asia.paint.biz.shop.detail;

import android.text.Html;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogDeliverBinding;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2019-11-09.
 */
public class DeliveryDialog extends BaseBottomDialogFragment<DialogDeliverBinding> {

    private String mDescription;


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_deliver;
    }

    @Override
    protected void initView() {
        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        mBinding.tvDescription.setText(Html.fromHtml(mDescription));
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
