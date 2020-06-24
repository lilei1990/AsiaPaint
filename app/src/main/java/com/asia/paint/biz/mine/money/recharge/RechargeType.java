package com.asia.paint.biz.mine.money.recharge;

/**
 * @author by chenhong14 on 2019-11-13.
 */
public class RechargeType {

    public enum Pay {
        BALANCE, ZHI_FU_BAO, WEI_XIN, DEBIT_CARD
    }

    public int iconId;

    public Pay pay;

    public String name;

    public String description;

    public int type;

}
