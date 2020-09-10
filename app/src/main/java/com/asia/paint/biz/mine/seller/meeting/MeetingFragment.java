package com.asia.paint.biz.mine.seller.meeting;

import android.os.Bundle;
import android.view.View;

import com.asia.paint.android.R;
import com.asia.paint.android.databinding.FragmentMeetingBinding;
import com.asia.paint.base.container.BaseFragment;
import com.asia.paint.base.network.api.MeetingService;
import com.asia.paint.base.network.bean.Meeting;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.network.bean.MeetingRsp;
import com.asia.paint.base.recyclerview.DefaultItemDecoration;
import com.asia.paint.biz.mine.seller.meeting.detail.MeetingDetailActivity;
import com.asia.paint.biz.mine.seller.meeting.hold.HoldMeetingActivity;
import com.asia.paint.biz.mine.seller.meeting.member.MeetingMemberActivity;
import com.asia.paint.utils.callback.OnChangeCallback;
import com.asia.paint.utils.callback.OnNoDoubleClickListener;
import com.asia.paint.utils.utils.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class MeetingFragment extends BaseFragment<FragmentMeetingBinding> implements OnChangeCallback<MeetingRsp> {

    private static final String KEY_MEETING_TYPE = "KEY_MEETING_TYPE";
    private int mType;
    private MeetingViewModel mViewModel;
    private MeetingAdapter mMeetingAdapter;
    private Meeting mMeeting;

    public static MeetingFragment createInstance(int type) {
        MeetingFragment fragment = new MeetingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_MEETING_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void argumentsValue(Bundle bundle) {
        mType = bundle.getInt(KEY_MEETING_TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meeting;
    }

    @Override
    protected void initView() {
        mViewModel = getViewModel(MeetingViewModel.class);
        mViewModel.resetPage();
        mBinding.tvJoinMeeting.setVisibility(mType == MeetingService.MEETING_ALL ? View.VISIBLE : View.GONE);
        mBinding.tvJoinMeeting.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                JoinMeetingDialog dialog = new JoinMeetingDialog();
                dialog.setChangeCallback(result -> {
                    Meeting meeting = mMeetingAdapter.findMeeting(result);
                    if (meeting == null) {
                        AppUtils.showMessage("未找到相关会议");
                    } else {
                        SureMeetingDialog sureDialog = SureMeetingDialog.createInstance(meeting);
                        sureDialog.setOnChangeCallback(result1 -> mViewModel.joinMeeting(result1.id));
                        sureDialog.show(mContext);
                    }
                });
                dialog.show(mContext);
            }
        });
        mBinding.tvHoldMeeting.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                HoldMeetingActivity.start(mActivity);
            }
        });
        mBinding.rvMeeting.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvMeeting.addItemDecoration(new DefaultItemDecoration(0, 8, 0, 0));
        mMeetingAdapter = new MeetingAdapter();
        mMeetingAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mMeeting = mMeetingAdapter.getData(position);
                int id = view.getId();
                if (id == R.id.iv_invite) {
                    MeetingMemberActivity.start(mActivity, null);
                }

            }
        });
        mMeetingAdapter.setOnItemClickListener((adapter, view, position) -> {
            Meeting meeting = mMeetingAdapter.getData(position);
            MeetingDetailActivity.start(mActivity, meeting);
        });
        mMeetingAdapter.setOnLoadMoreListener(() -> {
            mViewModel.autoAddPage();
            loadMeeting();
        }, mBinding.rvMeeting);
        mBinding.rvMeeting.setAdapter(mMeetingAdapter);
        loadMeeting();
    }

    public void reset() {
        mViewModel.resetPage();
        loadMeeting();
    }

    private void loadMeeting() {
        switch (mType) {
            case MeetingService.MEETING_ALL:
                mViewModel.loadAllMeeting("").setCallback(this);
                break;
            case MeetingService.MEETING_HOLD:
                mViewModel.loadHoldMeeting("").setCallback(this);
                break;
            case MeetingService.MEETING_JOIN:
                mViewModel.loadJoinMeeting("").setCallback(this);
                break;
        }
    }

    public void inviteMeetingMember(List<MeetingMember> members) {
        if (mMeeting == null || members == null || members.isEmpty()) {
            return;
        }
        mViewModel.inviteMeeting(mMeeting.id, members);
    }

    @Override
    public void onChange(MeetingRsp result) {
        mViewModel.updateListData(mMeetingAdapter, result);
    }
}
