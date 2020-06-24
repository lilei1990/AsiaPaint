package com.asia.paint.biz.mine.seller.score.record;

import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.base.network.api.ReceiptService;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.databinding.DialogReceiptTypeBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

/**
 * @author by chenhong14 on 2020-01-12.
 */
public class ReceiptTypeDialog extends BaseBottomDialogFragment<DialogReceiptTypeBinding> {

    private OnChangeCallback<Integer> mChangeCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_receipt_type;
    }

    @Override
    protected void initView() {

        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        mBinding.cbEReceipt.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbPaperReceipt.setChecked(!isChecked);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbEReceipt.isChecked();
            }
        });
        mBinding.cbPaperReceipt.setListener(new CheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                mBinding.cbEReceipt.setChecked(!isChecked);
            }

            @Override
            public boolean changeBySelf() {
                return !mBinding.cbPaperReceipt.isChecked();
            }
        });
        mBinding.tvSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                boolean checked = mBinding.cbEReceipt.isChecked();
                int type = checked ? ReceiptService.TYPE_ELECTRONIC : ReceiptService.TYPE_PAPER;
                if (mChangeCallback != null) {
                    mChangeCallback.onChange(type);
                }
                dismiss();
            }
        });
        mBinding.cbEReceipt.setChecked(true);

    }

    public void setChangeCallback(OnChangeCallback<Integer> changeCallback) {
        mChangeCallback = changeCallback;
    }
}
