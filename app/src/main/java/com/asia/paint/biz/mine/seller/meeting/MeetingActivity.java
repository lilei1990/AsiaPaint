package com.asia.paint.biz.mine.seller.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.ActivityMeetingBinding;
import com.asia.paint.base.container.BaseTitleActivity;
import com.asia.paint.base.network.api.MeetingService;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.biz.mine.seller.meeting.detail.MeetingDetailActivity;
import com.asia.paint.biz.mine.seller.meeting.hold.HoldMeetingActivity;
import com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity;

import java.util.List;

import static com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity.KEY_MEETING_MEMBER;

/**
 * @author by chenhong14 on 2019-12-13.
 */
public class MeetingActivity extends BaseTitleActivity<ActivityMeetingBinding> {

    private MeetingPagerAdapter mMeetingPagerAdapter;

    @Override
    protected String middleTitle() {
        return "会议中心";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingPagerAdapter = new MeetingPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mMeetingPagerAdapter);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mMeetingPagerAdapter.reset(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    public class MeetingPagerAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Pair<String, Fragment>> data;

        {
            data = new SparseArray<>();
            data.append(0, new Pair<>("全部会议", MeetingFragment.createInstance(MeetingService.MEETING_ALL)));
            data.append(1, new Pair<>("我参与的", MeetingFragment.createInstance(MeetingService.MEETING_JOIN)));
            data.append(2, new Pair<>("我举办的", MeetingFragment.createInstance(MeetingService.MEETING_HOLD)));

        }

        public MeetingPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return data.get(position).second;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).first;
        }

        public void reset(int position) {
            MeetingFragment fragment = (MeetingFragment) getItem(position);
            fragment.reset();
        }

        public void inviteMeetingMember(List<MeetingMember> members) {
            MeetingFragment fragment = (MeetingFragment) getItem(mBinding.viewPager.getCurrentItem());
            fragment.inviteMeetingMember(members);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HoldMeetingActivity.REQUEST_HOLD_MEETING && resultCode == RESULT_OK) {
            mMeetingPagerAdapter.reset(mBinding.viewPager.getCurrentItem());
        } else if (requestCode == MeetingMemberActivity.REQUEST_MEETING_MEMBER && resultCode == RESULT_OK && data != null) {
            List<MeetingMember> meetingMembers = data.getParcelableArrayListExtra(KEY_MEETING_MEMBER);
            mMeetingPagerAdapter.inviteMeetingMember(meetingMembers);
        } else if (requestCode == MeetingDetailActivity.REQUEST_CODE_MEETING_DETAIL && resultCode == RESULT_OK) {
            mMeetingPagerAdapter.reset(mBinding.viewPager.getCurrentItem());
        }
    }
}
