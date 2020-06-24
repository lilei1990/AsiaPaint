package com.asia.paint.biz.mine.seller.meeting;

import android.text.TextUtils;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.Meeting;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class MeetingAdapter extends BaseGlideAdapter<Meeting> {
    public MeetingAdapter() {
        super(R.layout.item_meeting);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, Meeting meeting) {
        if (meeting != null) {
            helper.setText(R.id.tv_meeting_name, meeting.name);
            helper.setText(R.id.tv_meeting_no, String.format("会议号：%s", meeting.sn));
            helper.setText(R.id.tv_meeting_address, String.format("会议地点：%s", meeting.address));
            helper.setText(R.id.tv_meeting_time, String.format("会议时间：%s", meeting.add_time));
            helper.setText(R.id.tv_meeting_content, String.format("会议内容：%s", meeting.content));
            helper.addOnClickListener(R.id.iv_invite);
        }
    }

    public Meeting findMeeting(String meetingNo) {
        List<Meeting> meetings = getData();
        for (Meeting meeting : meetings) {
            if (meeting != null && TextUtils.equals(meeting.sn, meetingNo)) {
                return meeting;
            }
        }
        return null;
    }
}
