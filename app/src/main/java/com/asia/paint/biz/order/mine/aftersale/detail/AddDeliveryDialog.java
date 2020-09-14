package com.asia.paint.biz.order.mine.aftersale.detail;

import android.text.TextUtils;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogAddDelveryBinding;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.utils.callback.OnChangePairCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2019-12-28.
 */
public class AddDeliveryDialog extends BaseDialogFragment<DialogAddDelveryBinding> {

    private OnChangePairCallback<String, String> mPairCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_delvery;
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
                String sn = mBinding.etSn.getText().toString().trim();
                String company = mBinding.etCompany.getText().toString().trim();
                if (TextUtils.isEmpty(sn) || TextUtils.isEmpty(company)) {
                    AppUtils.showMessage("物流单号或者物流公司为空");
                    return;
                }
                if (mPairCallback != null) {
                    mPairCallback.onChange(sn, company);
                }
                dismiss();
            }
        });

    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(270);
    }

    public void setPairCallback(OnChangePairCallback<String, String> pairCallback) {
        mPairCallback = pairCallback;
    }
}
