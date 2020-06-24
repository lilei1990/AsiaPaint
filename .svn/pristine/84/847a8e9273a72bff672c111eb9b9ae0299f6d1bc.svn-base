package com.asia.paint.biz.mine.seller.meeting;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.base.network.bean.Meeting;
import com.asia.paint.databinding.DialogSureMeetingBinding;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2019-12-30.
 */
public class SureMeetingDialog extends BaseDialogFragment<DialogSureMeetingBinding> {
    private static final String KEY_MEETING = "KEY_MEETING";
    private Meeting mMeeting;
    private OnChangeCallback<Meeting> mOnChangeCallback;

    public static SureMeetingDialog createInstance(Meeting meeting) {
        SureMeetingDialog dialog = new SureMeetingDialog();
        if (meeting != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_MEETING, meeting);
            dialog.setArguments(bundle);
        }
        return dialog;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mMeeting = bundle.getParcelable(KEY_MEETING);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sure_meeting;
    }

    @Override
    protected void initView() {
        if (mMeeting != null) {
            mBinding.tvMeetingName.setText(mMeeting.name);
            mBinding.tvMeetingNo.setText(String.format("会议号：%s", mMeeting.sn));
            mBinding.tvMeetingAddress.setText(String.format("会议地址：%s", mMeeting.address));
            mBinding.tvMeetingTime.setText(String.format("会议时间：%s", mMeeting.add_time));
        }
        mBinding.btnSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mOnChangeCallback != null) {
                    mOnChangeCallback.onChange(mMeeting);
                }
                dismiss();
            }
        });
    }

    @Override
    protected int getDialogWidth() {
        return AppUtils.dp2px(288);
    }


    public void setOnChangeCallback(OnChangeCallback<Meeting> onChangeCallback) {
        mOnChangeCallback = onChangeCallback;
    }
}
