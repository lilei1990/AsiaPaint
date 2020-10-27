package com.asia.paint.base.network.bean;

import java.util.List;

/**
 * @author by chenhong14 on 2020-03-01.
 */
public class FlashGoods {

    /**
     * spike_id : 4
     * goods_name : 净味五合一内墙水漆·鸢尾花 内墙乳胶漆面漆(24袋)+底漆(12袋)套装
     * goods_id : 200
     * price : 1555.00
     * stock : 10
     * strtime : 2020-02-28 00:00:00
     * endtime : 2020-03-06 00:00:00
     * status : 1
     * add_time : 1582973223
     * default_image : ["https://store.asia-paints.com/uploads/goods/20191230/1db1ecefc4fbfbccfe0abac2e7861e25.jpg"]
     * market_price : 1588.00
     */

    public int spike_id;
    public String goods_name;
    public int goods_id;
    public String price;
    public int stock;
    public String strtime;
    public String endtime;
    public String second;
    public int status;
    public int add_time;
    public String market_price;
    public int groupbuy_id;
    public List<String> default_image;

    // 列表中展示的倒计时(非服务器返回字段)
    public String showHour;
    public String showMinute;
    public String showSecond;
}
