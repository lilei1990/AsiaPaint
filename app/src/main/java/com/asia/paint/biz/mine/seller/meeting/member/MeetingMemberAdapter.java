package com.asia.paint.biz.mine.seller.meeting.member;

import android.view.View;

import com.asia.paint.R;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.recyclerview.BaseGlideAdapter;
import com.asia.paint.base.recyclerview.GlideViewHolder;
import com.asia.paint.base.widgets.CheckBox;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author by chenhong14 on 2019-12-31.
 */
public class MeetingMemberAdapter extends BaseGlideAdapter<MeetingMember> {

    public List<MeetingMember> mSelectedMember;

    public MeetingMemberAdapter() {
        super(R.layout.item_meeting_member);
        mSelectedMember = new ArrayList<>();
    }

    @Override
    protected void convert(@NonNull GlideViewHolder helper, MeetingMember member) {
        if (member != null) {
            helper.loadCircleImage(R.id.iv_avatar, member.avatar);
            helper.setText(R.id.tv_name, member.nickname);
            helper.setText(R.id.tv_phone, member.mobile);
            CheckBox checkBox = helper.getView(R.id.cb_check);
            checkBox.setListener(new CheckBox.OnCheckChangeListener() {
                @Override
                public void onChange(boolean isChecked) {
                    handleMember(member);
                }

                @Override
                public boolean changeBySelf() {
                    return false;
                }
            });
            helper.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View view) {
                    handleMember(member);
                }
            });
            checkBox.setChecked(containMember(member));
        }
    }

    public void handleMember(MeetingMember member) {
        if (member == null) {
            return;
        }

        if (containMember(member)) {
            removeMember(member);
        } else {
            mSelectedMember.add(member);
        }
        notifyDataSetChanged();
    }

    private boolean containMember(MeetingMember member) {
        if (member == null) {
            return false;
        }
        for (MeetingMember meetingMember : mSelectedMember) {
            if (meetingMember != null && meetingMember.id == member.id) {
                return true;
            }
        }
        return false;
    }

    private void removeMember(MeetingMember member) {
        if (member == null) {
            return;
        }
        MeetingMember target = null;
        for (MeetingMember meetingMember : mSelectedMember) {
            if (meetingMember != null && meetingMember.id == member.id) {
                target = meetingMember;
                break;
            }
        }
        if (target != null) {
            mSelectedMember.remove(target);
        }
    }

    public void clearSelectedMember() {
        mSelectedMember.clear();
    }

    public List<MeetingMember> getSelectedMember() {
        return mSelectedMember;
    }

    public void setSelectedMember(List<MeetingMember> member) {
        if (member != null) {
            mSelectedMember.addAll(member);
        }
    }
}
