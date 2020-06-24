package com.asia.paint.base.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-19.
 */
public class CartGoodsRsp {


    /**
     * is_all_check : true
     * result : [{"rec_id":276,"rec_type":0,"user_id":11,"goods_id":142,"goods_name":"亚士漆高级油漆","spec_id":796,"specification":"2","price":"2.00","quantity":1,
     * "goods_image":"http://01bug.com/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg","is_shipping":1,"sid":0,"act_id":0,"goods_status":"normal"}]
     * quantity : 1
     * amount : 2.00
     */

    public boolean is_all_check;
    public int quantity;
    public String amount;
    @SerializedName("result")
    public List<CartGoods> cartGoods;


}
