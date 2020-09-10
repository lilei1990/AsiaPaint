package com.asia.paint.biz.mine.seller.score.record;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogReceiptUploadBinding;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.base.network.api.ReceiptService;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.base.widgets.selectimage.MatisseActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * @author by chenhong14 on 2020-01-12.
 */
public class ReceiptUploadDialog extends BaseDialogFragment<DialogReceiptUploadBinding> {

    private static final String KEY_RECEIPT_TYPE = "KEY_RECEIPT_TYPE";
    private ReceiptUploadAdapter mReceiptUploadAdapter;
    private OnReceiptDialogListener mOnReceiptDialogListener;
    private int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_receipt_upload;
    }

    public static ReceiptUploadDialog createInstance(int type) {
        ReceiptUploadDialog dialog = new ReceiptUploadDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_RECEIPT_TYPE, type);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_RECEIPT_TYPE);
    }

    @Override
    protected void initView() {
        mBinding.btnCancel.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        mBinding.btnSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                String sn = mBinding.etExpressSn.getText().toString();
                String money = mBinding.etReceiptMoney.getText().toString();
                if (mOnReceiptDialogListener != null) {
                    mOnReceiptDialogListener.onChange(sn, money, mReceiptUploadAdapter.getImg());
                }

                dismiss();
            }
        });
        mBinding.rvImage.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mBinding.rvImage.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mBinding.rvImage.setItemAnimator(null);
        mReceiptUploadAdapter = new ReceiptUploadAdapter();
        mBinding.rvImage.setAdapter(mReceiptUploadAdapter);
        mReceiptUploadAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            if (id == R.id.iv_delete) {
                mReceiptUploadAdapter.getData().remove(mReceiptUploadAdapter.getData(position));
                addImageUrls(new ArrayList<>());
                mReceiptUploadAdapter.notifyDataSetChanged();
            } else if (id == R.id.iv_img && mReceiptUploadAdapter.isAddImg(view.getTag())) {
                MatisseActivity.start(mContext, this::addImageUrls);
            }
        });
        mBinding.tvTitle.setText(mType == ReceiptService.TYPE_ELECTRONIC ? "提供电子发票" : "提供纸质发票");
        mBinding.layoutExpressSn.setVisibility(mType == ReceiptService.TYPE_PAPER ? View.VISIBLE : View.GONE);
        mBinding.tvImage.setVisibility(mType == ReceiptService.TYPE_ELECTRONIC ? View.VISIBLE : View.GONE);
        mBinding.rvImage.setVisibility(mType == ReceiptService.TYPE_ELECTRONIC ? View.VISIBLE : View.GONE);
        addImageUrls(new ArrayList<>());
    }

    private void addImageUrls(List<String> result) {
        mReceiptUploadAdapter.addImg(result, R.mipmap.ic_add_apply_after_sale);
    }


    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(330);
    }

    public void setOnReceiptDialogListener(OnReceiptDialogListener onReceiptDialogListener) {
        mOnReceiptDialogListener = onReceiptDialogListener;
    }

    public interface OnReceiptDialogListener {
        void onChange(String sn, String receiptMoney, List<String> images);
    }
}
