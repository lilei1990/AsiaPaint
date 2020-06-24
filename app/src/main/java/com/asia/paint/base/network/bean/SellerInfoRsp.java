package com.asia.paint.base.network.bean;

import java.util.List;

/**
 * @author by chenhong14 on 2019-12-11.
 */
public class SellerInfoRsp {


    /**
     * SELLER : false
     * adj : [{"id":2,"title":"2","image":"http://01bug.com/000/yashiqi/public/uploads/color/20191122/a1b4f2d30891cf10decf08a455b1ab53.jpg",
     * "status":"normal","sort":2,"add_time":1573903624},{"id":1,"title":"123","image":"http://01bug
     * .com/000/yashiqi/public/uploads/color/20191122/e663a8a5223eb9dc2ee2f927c5b247fd.jpg","status":"normal","sort":50,"add_time":1573903597}]
     * task : [{"id":1,"name":"11月任务","strtime":1573193072,"endtime":1574193072,"score":0,"type":1,"num":0,"sid":0,"group":1,"status":2,
     * "add_time":1573193072,"point":"100%","join_num":0}]
     * task_count : 1
     * ordres : [8,3,0,0]
     * coupun : 0
     * score : 0
     * collect : 2
     * money : 19805.00
     * sale : {"yesterday":15,"month":100}
     * msg : 16
     */

    public Seller seller;
    public int task_count;
    public int coupun;
    public String score;
    public int collect;
    public String money;
    public SaleInfo sale;
    public int msg;
    public String img;
    public List<Banner> adj;
    public List<Task> task;
    public List<Integer> ordres;

    public static class SaleInfo {
        /**
         * yesterday : 15
         * month : 100
         */

        public int yesterday;
        public int month;
        public int year;
    }

    public static class Seller {

        /**
         * id : 10014
         * name : 李四
         * shopname : 霸道油漆
         * runtime :
         * image :
         * address : 四川省成都市天府广场888号
         * status : 1
         * point_lng : 104.0886
         * point_lat : 30.656067
         * add_time : 1577201884
         */

        public int id;
        public String name;
        public String shopname;
        public String runtime;
        public String image;
        public String address;
        public int status;
        public double point_lng;
        public double point_lat;
        public int add_time;
    }

}
