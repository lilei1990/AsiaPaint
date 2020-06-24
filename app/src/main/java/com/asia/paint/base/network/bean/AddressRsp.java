package com.asia.paint.base.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-23.
 */
public class AddressRsp {

    /**
     * result : [{"address_id":34,"address_name":"123","user_id":11,"consignee":"lisi","address":"北京 顺义区 旺泉街道","tel":"15196655222","area":"-","is_default":0}]
     * totalpage : 1
     */

    public int totalpage;
    @SerializedName("result")
    public List<Address> address;
}
