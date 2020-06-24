package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    /**
     * address_id : 34
     * address_name : 123
     * user_id : 11
     * consignee : lisi
     * address : 北京 顺义区 旺泉街道
     * tel : 15196655222
     * area : -
     * is_default : 0
     */

    public int address_id;
    public String address_name;
    public int user_id;
    public String consignee;
    public String address;
    public String tel;
    public String area;
    public int is_default;

    public Address() {

    }

    protected Address(Parcel in) {
        address_id = in.readInt();
        address_name = in.readString();
        user_id = in.readInt();
        consignee = in.readString();
        address = in.readString();
        tel = in.readString();
        area = in.readString();
        is_default = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public boolean isDefault() {
        return is_default == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(address_id);
        dest.writeString(address_name);
        dest.writeInt(user_id);
        dest.writeString(consignee);
        dest.writeString(address);
        dest.writeString(tel);
        dest.writeString(area);
        dest.writeInt(is_default);
    }
}