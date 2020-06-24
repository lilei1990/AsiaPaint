package com.asia.paint.base.network.bean;

/**
 * @author by chenhong14 on 2020-01-02.
 */
public class RechargeDetail {



    /**
     * id : 102
     * user_id : 11
     * order_sn : CZ2020010232924
     * money : 0.01
     * before : 0.00
     * after : 0.00
     * memo : 会员充值
     * from : 0
     * status : 1
     * type : 2
     * pay_way : 2
     * pay_time : 2020-01-02 23:35:14
     * createtime : 2020-01-02 23:35:07
     * is_del : 0
     */

    public int id;
    public int user_id;
    public String order_sn;
    public String money;
    public String before;
    public String after;
    public String memo;
    public int from;
    public int status;
    //1充值2订单消费3退款
    public int type;
    public int pay_way;
    public String pay_time;
    public String createtime;
    public int is_del;
}
