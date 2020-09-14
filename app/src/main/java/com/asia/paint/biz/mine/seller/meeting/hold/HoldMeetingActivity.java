package com.asia.paint.biz.mine.seller.meeting.hold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityHoldMeetingBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.mine.seller.meeting.MeetingViewModel;
import com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.asia.paint.utils.utils.DateUtils;
import com.asia.paint.utils.utils.KeyBoardUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity.KEY_MEETING_MEMBER;

/**
 * @author by chenhong14 on 2019-12-30.
 */
public class HoldMeetingActivity extends BaseTitleActivity<ActivityHoldMeetingBinding> {

    private MeetingViewModel mMeetingViewModel;
    private HoldMeetingMemberAdapter mMemberAdapter;
    public static final int REQUEST_HOLD_MEETING = 0xF589;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, HoldMeetingActivity.class);
        activity.startActivityForResult(intent, REQUEST_HOLD_MEETING);
    }


    @Override
    protected String middleTitle() {
        return "会议中心";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hold_meeting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingViewModel = getViewModel(MeetingViewModel.class);
        mBinding.layoutMeetingTime.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                KeyBoardUtils.closeSoftInput(HoldMeetingActivity.this);
                selectMeetingTime();
            }
        });
        mBinding.ivInviteMember.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                MeetingMemberActivity.start(HoldMeetingActivity.this, (ArrayList<MeetingMember>) mMemberAdapter.getData());
            }
        });
        mBinding.rvMeetingMember.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvMeetingMember.setItemAnimator(null);
        mBinding.rvMeetingMember.addItemDecoration(new DefaultItemDecoration(0, 0, 0, 8));
        mMemberAdapter = new HoldMeetingMemberAdapter();
        mMemberAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_delete) {
                mMemberAdapter.remove(position);
            }
        });
        mBinding.rvMeetingMember.setAdapter(mMemberAdapter);
        mBinding.btnHoldMeeting.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                String name = getText(mBinding.etMeetingName);
                if (TextUtils.isEmpty(name)) {
                    AppUtils.showMessage("请输入会议名称");
                    return;
                }
                String address = getText(mBinding.etMeetingAddress);
                if (TextUtils.isEmpty(address)) {
                    AppUtils.showMessage("请输入会议地址");
                    return;
                }
                String time = getText(mBinding.tvMeetingTime);
                if (TextUtils.isEmpty(time)) {
                    AppUtils.showMessage("请选择会议时间");
                    return;
                }
                String content = getText(mBinding.etMeetingContent);
                if (TextUtils.isEmpty(content)) {
                    AppUtils.showMessage("请填写会议内容");
                    return;
                }
                mMeetingViewModel.holdMeeting(name, address, time, time, content, mMemberAdapter.getData())
                        .setCallback(result ->
                                new HoldMeetingDialog.Builder()
                                        .setMeetingNo(result)
                                        .setOnClickListener(new OnNoDoubleClickListener() {
                                            @Override
                                            public void onNoDoubleClick(View view) {
                                                setResult(RESULT_OK);
                                                finish();
                                            }
                                        })
                                        .build()
                                        .show(HoldMeetingActivity.this));
            }
        });
    }

    private void selectMeetingTime() {
        String date = mBinding.tvMeetingTime.getText().toString();
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, (date1, v) -> {//选中事件回调
            mBinding.tvMeetingTime.setText(DateUtils.dateToString(date1, DateUtils.DATE_PATTERN_2));
        })
                .setCancelColor(AppUtils.getColor(R.color.color_333333))
                .setSubmitColor(AppUtils.getColor(R.color.color_333333))
                .setContentTextSize(18)
                .build();
        if (!TextUtils.isEmpty(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.getDate(date, DateUtils.DATE_PATTERN_2));
            pvTime.setDate(calendar);
        }
        pvTime.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MeetingMemberActivity.REQUEST_MEETING_MEMBER && resultCode == RESULT_OK && data != null) {
            List<MeetingMember> meetingMembers = data.getParcelableArrayListExtra(KEY_MEETING_MEMBER);
            mMemberAdapter.updateData(meetingMembers, true);
        }
    }
}
