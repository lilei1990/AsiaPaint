package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author by chenhong14 on 2019-12-31.
 */
public class MeetingMember implements Parcelable {

    /**
     * id : 396
     * nickname : 会员673
     * mobile : 15936914877
     * avatar :
     */

    public int id;
    public String nickname;
    public String mobile;
    public String avatar;

    protected MeetingMember(Parcel in) {
        id = in.readInt();
        nickname = in.readString();
        mobile = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nickname);
        dest.writeString(mobile);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MeetingMember> CREATOR = new Creator<MeetingMember>() {
        @Override
        public MeetingMember createFromParcel(Parcel in) {
            return new MeetingMember(in);
        }

        @Override
        public MeetingMember[] newArray(int size) {
            return new MeetingMember[size];
        }
    };
}
