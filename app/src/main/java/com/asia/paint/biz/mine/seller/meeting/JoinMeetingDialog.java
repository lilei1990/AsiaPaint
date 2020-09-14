package com.asia.paint.biz.mine.seller.meeting;

import android.text.TextUtils;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.DialogJoinMeetingBinding;
import com.asia.paint.base.container.BaseDialogFragment;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;

/**
 * @author by chenhong14 on 2019-12-30.
 */
public class JoinMeetingDialog extends BaseDialogFragment<DialogJoinMeetingBinding> {

    private OnChangeCallback<String> mChangeCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_join_meeting;
    }

    @Override
    protected void initView() {
        mBinding.btnFindMeeting.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String meetingNo = mBinding.etMeetingNo.getText().toString().trim();
                if (TextUtils.isEmpty(meetingNo)) {
                    AppUtils.showMessage("请输入会议号");
                    return;
                }
                if (mChangeCallback != null) {
                    mChangeCallback.onChange(meetingNo);
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
