package com.asia.paint.base.widgets.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.asia.paint.android.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.android.databinding.DialogMessageBinding;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2019-11-02.
 */
public class MessageDialog extends BaseDialogFragment<DialogMessageBinding> {

    private Builder mBuilder;

    private MessageDialog(Builder builder) {
        mBuilder = builder;
    }

    @Override
    protected void initView() {
        mBinding.tvTitle.setText(mBuilder.title);
        mBinding.tvTitle.setVisibility(TextUtils.isEmpty(mBuilder.title) ? View.GONE : View.VISIBLE);
        mBinding.tvMessage.setText(mBuilder.message);
        mBinding.tvMessage.setVisibility(TextUtils.isEmpty(mBuilder.message) ? View.GONE : View.VISIBLE);
        mBinding.btnSure.setText(mBuilder.sureText);
        mBinding.btnSure.setVisibility(TextUtils.isEmpty(mBuilder.sureText) ? View.GONE : View.VISIBLE);
        mBinding.btnSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
                if (mBuilder.sureListener != null) {
                    mBuilder.sureListener.onClick(view);
                }

            }
        });
        mBinding.btnCancel.setText(mBuilder.cancelText);
        mBinding.btnCancel.setVisibility(TextUtils.isEmpty(mBuilder.cancelText) ? View.GONE : View.VISIBLE);
        mBinding.btnCancel.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
                if (mBuilder.cancelListener != null) {
                    mBuilder.cancelListener.onClick(view);
                }
            }
        });
    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(270);
    }

    @Override
    protected int getDialogHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_message;
    }


    public static class Builder {

        String title;
        String message;
        String sureText;
        String cancelText;
        View.OnClickListener sureListener;
        View.OnClickListener cancelListener;

        public String getTitle() {
            return title;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public String getSureText() {
            return sureText;
        }

        public Builder setSureButton(String text, View.OnClickListener listener) {
            this.sureText = text;
            this.sureListener = listener;
            return this;
        }

        public Builder setCancelButton(String text, View.OnClickListener listener) {
            this.cancelText = text;
            this.cancelListener = listener;
            return this;
        }

        public MessageDialog build() {
            return new MessageDialog(this);
        }
    }
}
