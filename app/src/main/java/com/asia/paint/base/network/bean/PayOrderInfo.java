package com.asia.paint.base.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author by chenhong14 on 2020-01-01.
 */
public class PayOrderInfo {


    /**
     * appid : wx8d88ec08a74db0b3
     * noncestr : pnus7meT9TLty5cM
     * package : Sign=WXPay
     * partnerid : 1560923911
     * prepayid : wx01104158084707f4aa2eeff81129809300
     * timestamp : 1577846518
     * sign : C69E2CE879C7BC1239A5AECDBD9301EF
     */

    public String appid;
    public String noncestr;
    @SerializedName("package")
    public String packageInfo;
    public String partnerid;
    public String prepayid;
    public String timestamp;
    public String sign;
}
