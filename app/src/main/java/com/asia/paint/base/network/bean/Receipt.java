package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class Receipt implements Parcelable {


    /**
     * id : 37
     * user_id : 11
     * receipt : 1
     * type : 2
     * title : 11112
     * sn : 2222222
     * is_default : 1
     * bank :
     * bank_sn :
     * address :
     * tel :
     * email :
     * add_time : 1578132986
     * company_tel :
     */

    public int id;
    public int user_id;
    public int receipt;
    public int type;
    public String title;
    public String sn;
    public int is_default;
    public String bank;
    public String bank_sn;
    public String address;
    public String tel;
    public String email;
    public int add_time;
    public String company_tel;

    public Receipt() {

    }

    protected Receipt(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        receipt = in.readInt();
        type = in.readInt();
        title = in.readString();
        sn = in.readString();
        is_default = in.readInt();
        bank = in.readString();
        bank_sn = in.readString();
        address = in.readString();
        tel = in.readString();
        email = in.readString();
        add_time = in.readInt();
        company_tel = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeInt(receipt);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(sn);
        dest.writeInt(is_default);
        dest.writeString(bank);
        dest.writeString(bank_sn);
        dest.writeString(address);
        dest.writeString(tel);
        dest.writeString(email);
        dest.writeInt(add_time);
        dest.writeString(company_tel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };

    public boolean isDefault() {
        return is_default == 1;
    }
}
