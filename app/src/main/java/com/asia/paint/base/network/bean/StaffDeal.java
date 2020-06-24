package com.asia.paint.base.network.bean;

import java.util.List;

public class StaffDeal {
    /**
     * order_id : 839
     * order_sn : 2019122551702
     * order_status : 40
     * user_id : 9
     * flow_type : 0
     * pay_id : 3
     * pay_name : 余额支付
     * goods_amount : 1998.00
     * bonus : 0.00
     * order_amount : 1998.00
     * pay_time : 1577266889
     * bonus_id : 0
     * discount : 100.00
     * sid : 10001
     * description :
     * address_name : 天府五街
     * consignee : lee
     * address : 四川成都
     * tel : 18782922932
     * area : null
     * act_id : 0
     * express_sn : null
     * express_company : null
     * deliver_time : 0
     * confirm_time : 0
     * send_time : 1577416517
     * receive_time : 1577521292
     * freight : 0.00
     * add_time : 1577266871
     * score : 0.00
     * receipt : 0
     * _goods : [{"rec_id":903,"order_id":839,"goods_id":174,"goods_name":"全效净味墙固底漆.睡莲","goods_numbers":2,"goods_price":"999.00","back_price":"999
     * .00","spec_id":830,"userid":9,"goods_image":"https://store.asia-paints.com/uploads/goods/20191215/0a1e966cfc16e47316e8d0cd14413f27.jpg",
     * "status":1,"is_show":1,"specification":"全效净味墙固底漆.睡莲(2L)","is_return":1,"comment":null,"comment_status":0,"comment_score":"5.0",
     * "comment_images":null,"comment_video":null,"comment_time":null,"reply":null,"reply_time":null,"add_time":1577266871,"is_top":0,"is_hide":0}]
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
    public int pay_time;
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
    public int receipt;
    public List<OrderDetail.Goods> _goods;

}