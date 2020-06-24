package com.asia.paint.base.network.bean;

/**
 * @author by chenhong14 on 2020-01-04.
 */
public class ScoreRsp extends BaseListRsp<ScoreDetail> {


    /**
     * score_info : {"tip":"可用于商城交易,也可直接体现1积分=1RMB","my_score":0,"score":1,"money":"1.00"}
     * result : []
     * totalpage : 0
     */

    public ScoreInfoBean score_info;
    public String receipt;

    public static class ScoreInfoBean {
        /**
         * tip : 可用于商城交易,也可直接体现1积分=1RMB
         * my_score : 0
         * score : 1
         * money : 1.00
         */

        public String tip;
        public String my_score;
        public String score;
        public String money;
    }
}
