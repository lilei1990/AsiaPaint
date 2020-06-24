package com.asia.paint.base.network.bean;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class OrderInfoRsp {

    public int quantity;
    public int freight;
    public List<Coupon> coupon;
    public int bonus;
    public String amount;
    public int is_score;
    public int is_paypword;
    public List<CartGoods> cart;
    public List<Address> myaddress;
    public OrderScore score_info;
//    public List<OrderScore> score_info;
    public List<PinTuan> _pintuan;

    public boolean isSupportUsedScore() {
        return is_score == 1;
    }

    public boolean hasPayPwd() {
        return is_paypword == 1;
    }


}
