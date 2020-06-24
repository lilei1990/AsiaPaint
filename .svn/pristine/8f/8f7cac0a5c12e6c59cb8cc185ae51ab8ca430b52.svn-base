package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainCategory implements Parcelable {
    /**
     * id : 3
     * name : 培训B
     * desc : c
     * sort : 5
     * add_time : 1574323868
     */

    public int id;
    public String name;
    public String desc;
    public int sort;
    public int add_time;

    protected TrainCategory(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        sort = in.readInt();
        add_time = in.readInt();
    }

    public static final Creator<TrainCategory> CREATOR = new Creator<TrainCategory>() {
        @Override
        public TrainCategory createFromParcel(Parcel in) {
            return new TrainCategory(in);
        }

        @Override
        public TrainCategory[] newArray(int size) {
            return new TrainCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeInt(sort);
        dest.writeInt(add_time);
    }
}