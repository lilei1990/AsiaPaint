package com.asia.paint.base.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by chenhong14 on 2019-12-14.
 */
public class BaseListRsp<T> {

    public int totalpage;
    @SerializedName("result")
    public List<T> data;
}
