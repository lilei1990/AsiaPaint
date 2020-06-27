package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2020/6/25.
 */

public class UpdateRsp implements Parcelable{
    public int id;
    public int sn;
    public String url;
    public String name;
    public String content;
    public int is_must;
    public String qrcode;
    public String add_time;

    protected UpdateRsp(Parcel in) {
        id = in.readInt();
        sn = in.readInt();
        url = in.readString();
        name = in.readString();
        content = in.readString();
        is_must = in.readInt();
        qrcode = in.readString();
        add_time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(sn);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(content);
        dest.writeInt(is_must);
        dest.writeString(qrcode);
        dest.writeString(add_time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateRsp> CREATOR = new Creator<UpdateRsp>() {
        @Override
        public UpdateRsp createFromParcel(Parcel in) {
            return new UpdateRsp(in);
        }

        @Override
        public UpdateRsp[] newArray(int size) {
            return new UpdateRsp[size];
        }
    };

    @Override
    public String toString() {
        return "UpdateRsp{" +
                "id=" + id +
                ", sn=" + sn +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", is_must=" + is_must +
                ", qrcode='" + qrcode + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
