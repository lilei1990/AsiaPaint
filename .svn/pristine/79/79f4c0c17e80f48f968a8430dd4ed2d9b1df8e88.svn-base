package com.asia.paint.biz.pay;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.databinding.DialogPayBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-02.
 */
public class PayDialog extends BaseDialogFragment<DialogPayBinding> {

    private static final String KEY_PAY_MONEY = "KEY_PAY_MONEY";
    private static final String KEY_REST_MONEY = "KEY_REST_MONEY";
    private String mPayMoney;
    private String mRestMoney;
    private OnChangeCallback<String> mChangeCallback;

    public static PayDialog createInstance(String payMoney, String restMoney) {
        PayDialog dialog = new PayDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PAY_MONEY, payMoney);
        bundle.putString(KEY_REST_MONEY, restMoney);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mPayMoney = bundle.getString(KEY_PAY_MONEY);
        mRestMoney = bundle.getString(KEY_REST_MONEY);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_pay;
    }

    @Override
    protected void initView() {

        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
                if (mChangeCallback != null) {
                    mChangeCallback.onChange("");
                }
            }
        });
        mBinding.tvPayMoney.setText(mPayMoney);
        mBinding.tvRestMoney.setText(mRestMoney);
        mBinding.viewPwd.setOnPasswordChangedListener(() -> {
            if (mBinding.viewPwd.isValidPassword()) {
                if (mChangeCallback != null) {
                    mChangeCallback.onChange(mBinding.viewPwd.getPassword());
                }
            }
        });

    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(288);
    }

    public void setChangeCallback(OnChangeCallback<String> changeCallback) {
        mChangeCallback = changeCallback;
    }
}
