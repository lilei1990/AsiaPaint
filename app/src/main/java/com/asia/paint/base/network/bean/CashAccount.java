package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CashAccount implements Parcelable {
    /**
     * id : 9
     * type : 1
     * account : 123456789
     * name : 李四
     * bank : null
     * bank_name : null
     * user_id : 11
     * add_time : 1578383199
     */

    public int id;
    public int type;
    public String account;
    public String name;
    public String bank;
    public String bank_name;
    public int user_id;
    public String add_time;
    public String idcard;
    public String tel;

    public CashAccount() {

    }

    protected CashAccount(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        account = in.readString();
        name = in.readString();
        bank = in.readString();
        bank_name = in.readString();
        user_id = in.readInt();
        add_time = in.readString();
        idcard = in.readString();
        tel = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(account);
        dest.writeString(name);
        dest.writeString(bank);
        dest.writeString(bank_name);
        dest.writeInt(user_id);
        dest.writeString(add_time);
        dest.writeString(idcard);
        dest.writeString(tel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CashAccount> CREATOR = new Creator<CashAccount>() {
        @Override
        public CashAccount createFromParcel(Parcel in) {
            return new CashAccount(in);
        }

        @Override
        public CashAccount[] newArray(int size) {
            return new CashAccount[size];
        }
    };
}