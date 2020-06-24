package com.asia.paint.base.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrderDetail {
    /**
     * order_id : 762
     * order_sn : 2019112550409
     * order_status : 10
     * user_id : 11
     * flow_type : 0
     * pay_id : 0
     * pay_name : 在线支付
     * goods_amount : 25.00
     * bonus : 0.00
     * order_amount : 25.00
     * pay_time : 0
     * bonus_id : 0
     * discount : 100.00
     * sid :
     * description :
     * address_name : baolihuayuan
     * consignee : lisi777
     * address : 重庆永川区吉安镇
     * tel : 13888888888
     * area : null
     * act_id : 0
     * express_sn : null
     * express_company : null
     * deliver_time : 0
     * confirm_time : 0
     * send_time : 0
     * receive_time : 0
     * freight : 0.00
     * add_time : 2019-11-25 00:11:47
     * _goods : [{"rec_id":814,"order_id":762,"goods_id":142,"goods_name":"亚士漆高级油漆","goods_numbers":1,"goods_price":"3.00","back_price":"0.00",
     * "spec_id":797,"userid":11,"goods_image":"http://01bug.com/000/yashiqi/public/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg",
     * "status":0,"is_show":1,"specification":"3","is_return":0,"comment":null,"comment_status":0,"comment_score":"5.0","comment_images":null,
     * "comment_video":null,"comment_time":null,"reply":null,"reply_time":null,"add_time":1574611907,"is_top":0,"is_hide":0},{"rec_id":815,
     * "order_id":762,"goods_id":142,"goods_name":"亚士漆高级油漆","goods_numbers":11,"goods_price":"2.00","back_price":"0.00","spec_id":796,"userid":11,
     * "goods_image":"http://01bug.com/000/yashiqi/public/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg","status":0,"is_show":1,
     * "specification":"2","is_return":0,"comment":null,"comment_status":0,"comment_score":"5.0","comment_images":null,"comment_video":null,
     * "comment_time":null,"reply":null,"reply_time":null,"add_time":1574611907,"is_top":0,"is_hide":0}]
     */

    public int order_id;
    public String order_sn;
    public int order_status;
    public int user_id;
    public int flow_type;
    public String pay_id;
    public String pay_name;
    public String goods_amount;
    public String bonus;
    public String order_amount;
    public String pay_time;
    public int bonus_id;
    public String discount;
    public String sid;
    public String description;
    public String address_name;
    public String consignee;
    public String address;
    public String tel;
    public Object area;
    public int act_id;
    public Object express_sn;
    public Object express_company;
    public String deliver_time;
    public String confirm_time;
    public String send_time;
    public String receive_time;
    public String freight;
    public String add_time;
    public String score;
    public String receipt;
    public int goods_count;
    public List<Goods> _goods;

    //是否支持售后【0否,点击提示:售后期已过，如有问题联系客服，1是】

    public static class Goods implements Parcelable {
        /**
         * rec_id : 814
         * order_id : 762
         * goods_id : 142
         * goods_name : 亚士漆高级油漆
         * goods_numbers : 1
         * goods_price : 3.00
         * back_price : 0.00
         * spec_id : 797
         * userid : 11
         * goods_image : http://01bug.com/000/yashiqi/public/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg
         * status : 0
         * is_show : 1
         * specification : 3
         * is_return : 0
         * comment : null
         * comment_status : 0
         * comment_score : 5.0
         * comment_images : null
         * comment_video : null
         * comment_time : null
         * reply : null
         * reply_time : null
         * add_time : 1574611907
         * is_top : 0
         * is_hide : 0
         */

        public int rec_id;
        public int order_id;
        public int goods_id;
        public String goods_name;
        public int goods_numbers;
        public String goods_price;
        public String back_price;
        public int spec_id;
        public int userid;
        public String goods_image;
        //收货状态【0:未收货，1:已收货】
        public int status;
        public int is_show;
        public String specification;
        public int is_return;
        public Object comment;
        public int comment_status;
        public String comment_score;
        public Object comment_images;
        public Object comment_video;
        public Object comment_time;
        public Object reply;
        public Object reply_time;
        public String add_time;
        public int is_top;
        public int is_hide;
        public int is_gift;
        public int return_status;

        protected Goods(Parcel in) {
            rec_id = in.readInt();
            order_id = in.readInt();
            goods_id = in.readInt();
            goods_name = in.readString();
            goods_numbers = in.readInt();
            goods_price = in.readString();
            back_price = in.readString();
            spec_id = in.readInt();
            userid = in.readInt();
            goods_image = in.readString();
            status = in.readInt();
            is_show = in.readInt();
            specification = in.readString();
            is_return = in.readInt();
            comment_status = in.readInt();
            comment_score = in.readString();
            add_time = in.readString();
            is_top = in.readInt();
            is_hide = in.readInt();
            return_status = in.readInt();
        }

        public static final Creator<Goods> CREATOR = new Creator<Goods>() {
            @Override
            public Goods createFromParcel(Parcel in) {
                return new Goods(in);
            }

            @Override
            public Goods[] newArray(int size) {
                return new Goods[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(rec_id);
            dest.writeInt(order_id);
            dest.writeInt(goods_id);
            dest.writeString(goods_name);
            dest.writeInt(goods_numbers);
            dest.writeString(goods_price);
            dest.writeString(back_price);
            dest.writeInt(spec_id);
            dest.writeInt(userid);
            dest.writeString(goods_image);
            dest.writeInt(status);
            dest.writeInt(is_show);
            dest.writeString(specification);
            dest.writeInt(is_return);
            dest.writeInt(comment_status);
            dest.writeString(comment_score);
            dest.writeString(add_time);
            dest.writeInt(is_top);
            dest.writeInt(is_hide);
            dest.writeInt(return_status);
        }
    }
}