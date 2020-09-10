package com.asia.paint.biz.mine.seller.meeting.detail;

import static com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity.KEY_MEETING_MEMBER;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityMeetingDetailBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.Meeting;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.widgets.dialog.MessageDialog;
import com.asia.paint.biz.mine.seller.meeting.MeetingViewModel;
import com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author by chenhong14 on 2020-01-01.
 */
public class MeetingDetailActivity extends BaseTitleActivity<ActivityMeetingDetailBinding> {

    private static final String KEY_MEETING_DETAIL = "KEY_MEETING_DETAIL";
    public static final int REQUEST_CODE_MEETING_DETAIL = 0x1234;
    private Meeting mMeeting;
    private MeetingViewModel mMeetingViewModel;


    public static void start(Activity activity, Meeting meeting) {
        Intent intent = new Intent(activity, MeetingDetailActivity.class);
        if (meeting != null) {
            intent.putExtra(KEY_MEETING_DETAIL, meeting);
        }
        activity.startActivityForResult(intent, REQUEST_CODE_MEETING_DETAIL);
    }

    @Override
    protected void intentValue(Intent intent) {
        mMeeting = intent.getParcelableExtra(KEY_MEETING_DETAIL);
    }

    @Override
    protected String middleTitle() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingViewModel = getViewModel(MeetingViewModel.class);
        if (mMeeting != null) {
            mBaseBinding.tvTitle.setText(mMeeting.name);
            mBinding.tvMeetingNo.setText(String.format("会议号：%s", mMeeting.sn));
            mBinding.tvMeetingAddress.setText(String.format("会议地点：%s", mMeeting.address));
            mBinding.tvMeetingTime.setText(String.format("会议时间：%s", mMeeting.add_time));
            mBinding.tvMeetingContent.setText(mMeeting.content);
            mBinding.tvMeetingHolder.setText(String.format("举办人：%s", mMeeting.adder));
        }
        mBinding.tvCancelMeeting.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                new MessageDialog.Builder()
                        .title("取消会议")
                        .message("是否确定取消会议？")
                        .setCancelButton("否", null)
                        .setSureButton("是", new OnNoDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View view) {
                                mMeetingViewModel.cancelMeeting(mMeeting.id).setCallback(result -> {
                                    if (result) {
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });
                            }
                        }).build()
                        .show(MeetingDetailActivity.this);

            }
        });
        mBinding.tvInviteMember.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MeetingMemberActivity.start(MeetingDetailActivity.this, null);
            }
        });
    }

    public void inviteMeetingMember(List<MeetingMember> members) {
        if (mMeeting == null || members == null || members.isEmpty()) {
            return;
        }
        mMeetingViewModel.inviteMeeting(mMeeting.id, members);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MeetingMemberActivity.REQUEST_MEETING_MEMBER && resultCode == RESULT_OK && data != null) {
            List<MeetingMember> meetingMembers = data.getParcelableArrayListExtra(KEY_MEETING_MEMBER);
            inviteMeetingMember(meetingMembers);
        }
    }
}
