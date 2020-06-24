package com.asia.paint.base.network.api;

import com.asia.paint.base.constants.Constants;
import com.asia.paint.base.network.bean.MeetingMember;
import com.asia.paint.base.network.bean.MeetingRsp;
import com.asia.paint.base.network.core.BaseRsp;
import com.asia.paint.network.NetworkUrl;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author by chenhong14 on 2019-11-19.
 */
@NetworkUrl(Constants.URL)
public interface MeetingService {


    int MEETING_ALL = 0;
    int MEETING_HOLD = 1;
    int MEETING_JOIN = 2;

    /**
     * 所有会议
     */
    @FormUrlEncoded
    @POST("api/seller/meeting")
    Observable<BaseRsp<MeetingRsp>> loadAllMeeting(@Field("keyword") String keyword, @Field("p") int page);


    /**
     * 我举办的会议
     *
     * @param keyword 会议号
     */
    @FormUrlEncoded
    @POST("api/seller/my_meeting")
    Observable<BaseRsp<MeetingRsp>> loadHoldMeeting(@Field("keyword") String keyword, @Field("p") int page);

    /**
     * 我参加的会议
     *
     * @param keyword 会议号
     */
    @FormUrlEncoded
    @POST("api/seller/join_meeting")
    Observable<BaseRsp<MeetingRsp>> loadJoinMeeting(@Field("keyword") String keyword, @Field("p") int page);

    /**
     * 举办会议
     * ids 邀请会员id【多个逗号拼接】
     */
    @FormUrlEncoded
    @POST("api/seller/add_meeting")
    Observable<BaseRsp<String>> holdMeeting(@Field("name") String name, @Field("address") String address, @Field("strtime") String startTime,
            @Field("endtime") String endTime, @Field("content") String content, @Field("ids") String ids);

    @FormUrlEncoded
    @POST("api/seller/cancel")
    Observable<BaseRsp<String>> cancelMeeting(@Field("id") int page);

    /**
     * 可邀请参会人员
     *
     * @param keyword 搜索关键字【电话或者昵称】
     * @param ids     会议已经邀请人排除【会议的ids】
     */
    @FormUrlEncoded
    @POST("api/seller/getuser")
    Observable<BaseRsp<List<MeetingMember>>> loadCanJoinMeetingMember(@Field("keyword") String keyword, @Field("ids") String ids);

    /**
     * 邀请会议
     *
     * @param id  会议id
     * @param ids 被邀请人【多个英文逗号分割】eg：10,11
     */
    @FormUrlEncoded
    @POST("api/seller/invite_meeting")
    Observable<BaseRsp<String>> inviteMeeting(@Field("id") int id, @Field("ids") String ids);

    /**
     * 加入会议
     */
    @FormUrlEncoded
    @POST("api/seller/canjia")
    Observable<BaseRsp<String>> joinMeeting(@Field("id") int id);

}

