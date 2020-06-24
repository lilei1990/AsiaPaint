package com.asia.paint.base.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by chenhong14 on 2019-12-23.
 */
public class OrderReturnDetailRsp {


    /**
     * result : {"order_id":796,"order_sn":"2019120756615","order_status":10,"user_id":11,"flow_type":0,"pay_id":"0","pay_name":"在线支付","goods_amount":"60
     * .00","bonus":"0.00","order_amount":"65.00","pay_time":"","bonus_id":0,"discount":"100.00","sid":"0","description":"","address_name":"london",
     * "consignee":"wangwu","address":"新疆巴音郭楞州和硕县兵团二师二十六团","tel":"15196655222","area":null,"act_id":0,"express_sn":null,"express_company":null,
     * "deliver_time":"","confirm_time":0,"send_time":0,"receive_time":"","freight":"15.00","add_time":"2019-12-07 10:48:35","score":"0.00","receipt":null,
     * "_goods":[{"rec_id":853,"order_id":796,"goods_id":168,"goods_name":"净味三合一内墙水漆.白杨","goods_numbers":1,"goods_price":"60.00","back_price":"60.00",
     * "spec_id":824,"userid":11,"goods_image":"https://store.asia-paints.com/uploads/goods/20191202/5d7f116c8ee2e3800229383f36600c00.jpg","status":0,
     * "is_show":1,"specification":"净味三合一内墙水漆.白杨(2L)","is_return":0,"comment":null,"comment_status":0,"comment_score":"5.0","comment_images":null,
     * "comment_video":null,"comment_time":null,"reply":null,"reply_time":null,"add_time":1575686915,"is_top":0,"is_hide":0}],"goods_count":1,"_list":[{"name
     * ":"商品总价：","value":"￥60.00元","color":"#666666"},{"name":"运费金额：","value":"￥15.00元","color":"#666666"},{"name":"优惠减免：","value":"-￥0.00元",
     * "color":"#666666"},{"name":"实付金额：","value":"￥65.00元","color":"#F41020"}]}
     */
    @SerializedName("result")
    public OrderReturnDetail orderReturnDetail;

    public static class OrderReturnDetail {
        /**
         * order_id : 796
         * order_sn : 2019120756615
         * order_status : 10
         * user_id : 11
         * flow_type : 0
         * pay_id : 0
         * pay_name : 在线支付
         * goods_amount : 60.00
         * bonus : 0.00
         * order_amount : 65.00
         * pay_time :
         * bonus_id : 0
         * discount : 100.00
         * sid : 0
         * description :
         * address_name : london
         * consignee : wangwu
         * address : 新疆巴音郭楞州和硕县兵团二师二十六团
         * tel : 15196655222
         * area : null
         * act_id : 0
         * express_sn : null
         * express_company : null
         * deliver_time :
         * confirm_time : 0
         * send_time : 0
         * receive_time :
         * freight : 15.00
         * add_time : 2019-12-07 10:48:35
         * score : 0.00
         * receipt : null
         * _goods : [{"rec_id":853,"order_id":796,"goods_id":168,"goods_name":"净味三合一内墙水漆.白杨","goods_numbers":1,"goods_price":"60.00","back_price":"60.00",
         * "spec_id":824,"userid":11,"goods_image":"https://store.asia-paints.com/uploads/goods/20191202/5d7f116c8ee2e3800229383f36600c00.jpg","status":0,
         * "is_show":1,"specification":"净味三合一内墙水漆.白杨(2L)","is_return":0,"comment":null,"comment_status":0,"comment_score":"5.0","comment_images":null,
         * "comment_video":null,"comment_time":null,"reply":null,"reply_time":null,"add_time":1575686915,"is_top":0,"is_hide":0}]
         * goods_count : 1
         * _list : [{"name":"商品总价：","value":"￥60.00元","color":"#666666"},{"name":"运费金额：","value":"￥15.00元","color":"#666666"},{"name":"优惠减免：","value":"-￥0
         * .00元","color":"#666666"},{"name":"实付金额：","value":"￥65.00元","color":"#F41020"}]
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
        public String express_sn;
        public String express_company;
        public String deliver_time;
        public String confirm_time;
        public String send_time;
        public String receive_time;
        public String freight;
        public String add_time;
        public String score;
        public Object receipt;
        public int goods_count;
        public List<OrderDetail.Goods> _goods;
        public List<Checkout> _list;

        //是否支持售后【0否,点击提示:售后期已过，如有问题联系客服，1是】
        public int return_status;


        public static class Checkout {
            /**
             * name : 商品总价：
             * value : ￥60.00元
             * color : #666666
             */

            public String name;
            public String value;
            public String color;
        }
    }
}
