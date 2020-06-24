package com.asia.paint.biz.order.mine.aftersale.apply;

import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseBottomDialogFragment;
import com.asia.paint.databinding.DialogAfterSaleReasonBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-27.
 */
public class AfterSaleReasonDialog extends BaseBottomDialogFragment<DialogAfterSaleReasonBinding> {

    private AfterSaleReasonAdapter mReasonAdapter;
    private Builder mBuilder;


    private AfterSaleReasonDialog(Builder builder) {
        mBuilder = builder;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_after_sale_reason;
    }

    @Override
    protected void initView() {
        mBinding.tvTitle.setText(mBuilder.mTitle);
        mBinding.rvReason.setLayoutManager(new LinearLayoutManager(mContext));
        mReasonAdapter = new AfterSaleReasonAdapter();
        mReasonAdapter.setOnItemClickListener((adapter, view, position) -> {
            mReasonAdapter.setCheckPosition(position);
            if (mBuilder.mChangeCallback != null) {
                mBuilder.mChangeCallback.onChange(mReasonAdapter.getData(position));
            }
        });
        mBinding.rvReason.setAdapter(mReasonAdapter);
        mReasonAdapter.updateData(mBuilder.mReason, true);
        mBinding.btnClose.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dismiss();
            }
        });
    }

    public static class Builder {
        private OnChangeCallback<String> mChangeCallback;
        private String mTitle;
        private List<String> mReason;

        public String getTitle() {
            return mTitle;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public List<String> getReason() {
            return mReason;
        }

        public Builder setReason(List<String> reason) {
            mReason = reason;
            return this;
        }

        public AfterSaleReasonDialog build() {
            return new AfterSaleReasonDialog(this);
        }

        public Builder setChangeCallback(OnChangeCallback<String> changeCallback) {
            mChangeCallback = changeCallback;
            return this;
        }
    }
}
