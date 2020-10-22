package com.asia.paint.base.network.bean;

/**
 * @author by chenhong14 on 2019-11-25.
 */
public class CreateOrderRsp {


    /**
     * order_id : 762
     * order_sn : 2019112550409
     * freight : 0
     */

    public int order_id;
    public String order_sn;
    public String order_amount;
    public int freight;
    //是否支持余额支付
    public int show_ye;
}
