package com.asia.paint.biz.mine.seller.meeting;

import com.asia.paint.base.container.BaseViewModel;
import com.asia.paint.base.network.api.MeetingService;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.network.bean.MeetingRsp;
import com.asia.paint.base.network.bean.TaskRsp;
import com.asia.paint.base.network.core.DefaultNetworkObserver;
import com.asia.paint.network.NetworkFactory;
import com.asia.paint.network.NetworkObservableTransformer;
import com.asia.paint.utils.callback.CallbackDate;
import com.asia.paint.utils.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class MeetingViewModel extends BaseViewModel {

    private CallbackDate<TaskRsp> mTaskRsp = new CallbackDate<>();
    private CallbackDate<Boolean> mCancelMeetingRsp = new CallbackDate<>();
    private CallbackDate<MeetingRsp> mAllMeetingRsp = new CallbackDate<>();
    private CallbackDate<MeetingRsp> mHoldMeetingRsp = new CallbackDate<>();
    private CallbackDate<MeetingRsp> mJoinMeetingRsp = new CallbackDate<>();
    private CallbackDate<List<MeetingMember>> mMeetingMemberRsp = new CallbackDate<>();
    private CallbackDate<String> mAddMeetingRsp = new CallbackDate<>();

    public CallbackDate<MeetingRsp> loadAllMeeting(String keyword) {
        NetworkFactory.createService(MeetingService.class)
                .loadAllMeeting(keyword, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MeetingRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MeetingRsp response) {
                        mAllMeetingRsp.setData(response);
                    }

                });
        return mAllMeetingRsp;
    }

    public CallbackDate<MeetingRsp> loadHoldMeeting(String keyword) {
        NetworkFactory.createService(MeetingService.class)
                .loadHoldMeeting(keyword, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MeetingRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MeetingRsp response) {
                        mHoldMeetingRsp.setData(response);
                    }

                });
        return mHoldMeetingRsp;
    }

    public CallbackDate<MeetingRsp> loadJoinMeeting(String keyword) {
        NetworkFactory.createService(MeetingService.class)
                .loadJoinMeeting(keyword, getCurPage())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<MeetingRsp>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(MeetingRsp response) {
                        mJoinMeetingRsp.setData(response);
                    }

                });
        return mJoinMeetingRsp;
    }

    public CallbackDate<String> holdMeeting(String name, String address, String startTime,
            String endTime, String content, List<MeetingMember> members) {
        StringBuilder builder = new StringBuilder();
        if (members != null) {
            for (MeetingMember meetingMember : members) {
                if (meetingMember != null) {
                    builder.append(meetingMember.id).append(",");
                }
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        NetworkFactory.createService(MeetingService.class)
                .holdMeeting(name, address, startTime, endTime, content, builder.toString())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {
                        mAddMeetingRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        AppUtils.showMessage(e.getMessage());
                    }
                });
        return mAddMeetingRsp;
    }


    public CallbackDate<Boolean> cancelMeeting(int id) {
        NetworkFactory.createService(MeetingService.class)
                .cancelMeeting(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {

                        mCancelMeetingRsp.setData(true);
                    }

                });
        return mCancelMeetingRsp;
    }

    public CallbackDate<List<MeetingMember>> loadCanJoinMeetingMember(String keyword, String ids) {
        NetworkFactory.createService(MeetingService.class)
                .loadCanJoinMeetingMember(keyword, ids)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<List<MeetingMember>>(false) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(List<MeetingMember> response) {
                        mMeetingMemberRsp.setData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mMeetingMemberRsp.setData(new ArrayList<>());
                    }
                });
        return mMeetingMemberRsp;
    }

    public CallbackDate<TaskRsp> inviteMeeting(int id, List<MeetingMember> members) {
        StringBuilder builder = new StringBuilder();
        if (members != null) {
            for (MeetingMember meetingMember : members) {
                if (meetingMember != null) {
                    builder.append(meetingMember.id).append(",");
                }
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        NetworkFactory.createService(MeetingService.class)
                .inviteMeeting(id, builder.toString())
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {

                        // mTaskRsp.setData(response);
                    }

                });
        return mTaskRsp;
    }

    public CallbackDate<TaskRsp> joinMeeting(int id) {
        NetworkFactory.createService(MeetingService.class)
                .joinMeeting(id)
                .compose(new NetworkObservableTransformer<>())
                .subscribe(new DefaultNetworkObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onResponse(String response) {

                        // mTaskRsp.setData(response);
                    }

                });
        return mTaskRsp;
    }


}
