package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable {
    /**
     * id : 12
     * sn : 2019127
     * name : 32132
     * address : 13213
     * strtime : 2019-12-22 16:09:33
     * endtime : 2019-12-22 16:09:33
     * content : 312213
     * ids :
     * user_id : 14
     * status : 2
     * add_time : 2019-12-22 16:09:41
     */

    public int id;
    public String sn;
    public String name;
    public String address;
    public String strtime;
    public String endtime;
    public String content;
    public String adder;
    public String ids;
    public int user_id;
    public int status;
    public String add_time;

    protected Meeting(Parcel in) {
        id = in.readInt();
        sn = in.readString();
        name = in.readString();
        address = in.readString();
        strtime = in.readString();
        endtime = in.readString();
        content = in.readString();
        ids = in.readString();
        user_id = in.readInt();
        status = in.readInt();
        add_time = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(sn);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(strtime);
        dest.writeString(endtime);
        dest.writeString(content);
        dest.writeString(ids);
        dest.writeInt(user_id);
        dest.writeInt(status);
        dest.writeString(add_time);
    }
}