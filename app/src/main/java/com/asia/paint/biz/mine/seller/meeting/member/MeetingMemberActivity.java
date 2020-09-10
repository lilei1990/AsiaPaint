package com.asia.paint.biz.mine.seller.meeting.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityMeetingMemberBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.mine.seller.meeting.MeetingViewModel;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-31.
 */
public class MeetingMemberActivity extends BaseTitleActivity<ActivityMeetingMemberBinding> {

    private MeetingViewModel mMeetingViewModel;
    private MeetingMemberAdapter mMemberAdapter;
    private String mKeyword = "";
    public static final String KEY_MEETING_MEMBER = "KEY_MEETING_MEMBER";
    public static final int REQUEST_MEETING_MEMBER = 0xF599;
    private List<MeetingMember> mMeetingMembers;

    public static void start(Activity activity, ArrayList<MeetingMember> members) {
        Intent intent = new Intent(activity, MeetingMemberActivity.class);
        if (members != null) {
            intent.putParcelableArrayListExtra(KEY_MEETING_MEMBER, members);
        }
        activity.startActivityForResult(intent, REQUEST_MEETING_MEMBER);
    }

    @Override
    protected void intentValue(Intent intent) {
        mMeetingMembers = intent.getParcelableArrayListExtra(KEY_MEETING_MEMBER);
    }

    @Override
    protected String middleTitle() {
        return "会议成员";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_member;
    }

    private Runnable mSearchRunnable = new Runnable() {
        @Override
        public void run() {
            mMemberAdapter.clearSelectedMember();
            queryMeetingMember(mKeyword);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingViewModel = getViewModel(MeetingViewModel.class);
        mBinding.btnSure.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(KEY_MEETING_MEMBER, (ArrayList<? extends Parcelable>) mMemberAdapter.getSelectedMember());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mMemberAdapter = new MeetingMemberAdapter();
        mMemberAdapter.setSelectedMember(mMeetingMembers);
        mBinding.rvMember.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvMember.addItemDecoration(new DefaultItemDecoration(14, 8, 14, 0));
        mBinding.rvMember.setAdapter(mMemberAdapter);
        mBinding.viewSearch.setChangeCallback(result -> {
            mKeyword = result;
            query();
        });
        queryMeetingMember(mKeyword);
    }

    private void query() {
        mBinding.rvMember.removeCallbacks(mSearchRunnable);
        mBinding.rvMember.postDelayed(mSearchRunnable, 500);
    }

    private void queryMeetingMember(String keyword) {
        mMeetingViewModel.loadCanJoinMeetingMember(keyword, "").setCallback(result ->
                mMemberAdapter.replaceData(result));
    }

    @Override
    protected void onDestroy() {
        mBinding.rvMember.removeCallbacks(mSearchRunnable);
        super.onDestroy();
    }
}

