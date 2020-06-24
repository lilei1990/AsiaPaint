package com.asia.paint.base.network.bean;

import java.util.List;

/**
 * @author by chenhong14 on 2020-01-07.
 */
public class CashInfoRsp {


    /**
     * myscore : 0
     * account : [{"id":9,"type":1,"account":"123456789","name":"李四","bank":null,"bank_name":null,"user_id":11,"add_time":1578383199}]
     * score_info : {"id":1,"name":"score","score":1,"max":999999,"point":"5.00","money":"1.00","add_time":1574041785}
     * month : 0
     * is_payword : 1
     */

    public int myscore;
    public ScoreInfo score_info;
    public int month;
    public int is_payword;
    public List<CashAccount> account;

    public static class ScoreInfo {
        /**
         * id : 1
         * name : score
         * score : 1
         * max : 999999
         * point : 5.00
         * money : 1.00
         * add_time : 1574041785
         */

        public int id;
        public String name;
        public int score;
        public int max;
        public String point;
        public String money;
        public int add_time;
    }

    public boolean hasPayword(){
        return is_payword == 1;
    }

}
