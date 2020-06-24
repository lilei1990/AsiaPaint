package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-17.
 */
public class ShopBannerRsp {


    public List<Banner> adj;
    public List<CategoryBean> category;


    public static class CategoryBean implements Parcelable {
        /**
         * id : 3
         * pid : 0
         * name : 传奇系列
         * sort : 4
         * status : normal
         * image : /uploads/20190620/642d9a2fd548c516818ba1e871faa131.jpg
         * add_time : 1573362409
         */

        public Integer id;
        public int pid;
        public String name;
        public int sort;
        public String status;
        public String image;
        public int add_time;

        public CategoryBean() {

        }

        protected CategoryBean(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            pid = in.readInt();
            name = in.readString();
            sort = in.readInt();
            status = in.readString();
            image = in.readString();
            add_time = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            dest.writeInt(pid);
            dest.writeString(name);
            dest.writeInt(sort);
            dest.writeString(status);
            dest.writeString(image);
            dest.writeInt(add_time);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CategoryBean> CREATOR = new Creator<CategoryBean>() {
            @Override
            public CategoryBean createFromParcel(Parcel in) {
                return new CategoryBean(in);
            }

            @Override
            public CategoryBean[] newArray(int size) {
                return new CategoryBean[size];
            }
        };
    }
}
