package com.asia.paint.base.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-21.
 */
public class ShopGoodsDetailRsp {


    /**
     * 运费信息
     */
    public String freight;
    @SerializedName("result")
    public Goods result;
    /**
     * 购物车数量
     */
    public int carnum;
    public int _comment_count;
    public float score;
    public List<Comment> _comment;
    public List<Gift> _gift;
    public GroupBuy _groupbuy;
    public Spike _spike;
    public Specs _specs;
    public int goods_number;

    @Override
    public String toString() {
        return "ShopGoodsDetailRsp{" +
                "freight='" + freight + '\'' +
                ", result=" + result +
                ", carnum=" + carnum +
                ", _comment_count=" + _comment_count +
                ", score=" + score +
                ", _comment=" + _comment +
                ", _gift=" + _gift +
                ", _groupbuy=" + _groupbuy +
                ", _spike=" + _spike +
                ", _specs=" + _specs +
                ", goods_number=" + goods_number +
                '}';
    }
}
