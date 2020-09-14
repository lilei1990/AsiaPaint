package com.asia.paint.biz.mine.seller.meeting.hold;

import com.asia.paint.android.R;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-31.
 */
public class HoldMeetingMemberAdapter extends BaseGlideAdapter<MeetingMember> {
    public HoldMeetingMemberAdapter() {
        super(R.layout.item_hold_meeting_member);
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, MeetingMember member) {
        if (member != null) {
            helper.setText(R.id.tv_name, String.format("%s  %s", member.nickname, member.id));
            helper.addOnClickListener(R.id.iv_delete);
        }
    }
}
