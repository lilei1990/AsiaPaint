package com.asia.paint.biz.pay;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.databinding.DialogCashPayBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.PriceUtils;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2019-12-02.
 */
public class CashPayDialog extends BaseDialogFragment<DialogCashPayBinding> {

    private static final String KEY_SCORE = "KEY_SCORE";
    private static final String KEY_TO_MONEY = "KEY_TO_MONEY";
    private static final String KEY_TAX = "KEY_TAX";
    private String mScore;
    private String mToMoney;
    private String mTax;
    private OnChangeCallback<String> mChangeCallback;

    public static CashPayDialog createInstance(String score, String money, String tax) {
        CashPayDialog dialog = new CashPayDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SCORE, score);
        bundle.putString(KEY_TO_MONEY, money);
        bundle.putString(KEY_TAX, tax);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mScore = bundle.getString(KEY_SCORE);
        mToMoney = bundle.getString(KEY_TO_MONEY);
        mTax = bundle.getString(KEY_TAX);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_cash_pay;
    }

    @Override
    protected void initView() {

        mBinding.ivClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
        mBinding.tvScore.setText(mScore);
        mBinding.tvToMoney.setText(PriceUtils.getPrice(mToMoney));
        mBinding.tvTax.setText(PriceUtils.getPrice(mTax));
        mBinding.viewPwd.setOnPasswordChangedListener(() -> {
            if (mBinding.viewPwd.isValidPassword()) {
                if (mChangeCallback != null) {
                    mChangeCallback.onChange(mBinding.viewPwd.getPassword());
                }
                dismiss();
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
