package com.asia.paint.biz.mine.seller.meeting.hold;

import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogHoldMeetingBinding;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2020-01-01.
 */
public class HoldMeetingDialog extends BaseDialogFragment<DialogHoldMeetingBinding> {
    private Builder mBuilder;

    private HoldMeetingDialog(Builder builder) {
        mBuilder = builder;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_hold_meeting;
    }

    @Override
    protected void initView() {
        mBinding.tvMeetingNo.setText(String.format("会议号：%s", mBuilder.meetingNo));
        mBinding.tvSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mBuilder.mOnClickListener != null) {
                    mBuilder.mOnClickListener.onClick(view);
                }
                dismiss();
            }
        });
    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(290);
    }

    public static class Builder {
        private String meetingNo;
        private View.OnClickListener mOnClickListener;

        public Builder setMeetingNo(String meetingNo) {
            this.meetingNo = meetingNo;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
            return this;
        }

        public HoldMeetingDialog build() {
            return new HoldMeetingDialog(this);
        }
    }
}
