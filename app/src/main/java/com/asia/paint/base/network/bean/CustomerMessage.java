package com.asia.paint.base.network.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class CustomerMessage implements MultiItemEntity {

    public static final int SELF = 0;
    public static final int SERVICE = 1;
    public int itemType;
    /**
     * id : 48
     * user_id : 11
     * admin_id : 1
     * content :
     * type : 2
     * image : https://store.asia-paints.com/uploads/color/20200105/c4b98e5178ffd87b873f414dd4062431.jpeg
     * status : 0
     * form : 1
     * add_time : 11:26:33
     * admin_name : 客服01
     * admin_logo : https://store.asia-paints.com/common/logo.png
     */
    public String image;
    public String content;
    public int id;
    public int user_id;
    public int admin_id;
    public int type;
    public int status;
    //来源【0客户发起，1客服回复】
    public int form;
    public String add_time;
    public String admin_name;
    public String admin_logo;

    @Override
    public int getItemType() {
        return form;
    }
}
