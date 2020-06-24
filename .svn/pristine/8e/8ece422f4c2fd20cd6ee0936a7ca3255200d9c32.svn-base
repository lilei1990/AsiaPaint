package com.asia.paint.base.network.bean;

import java.util.List;

public class Post {
    /**
     * id : 1
     * user_id : 9
     * content : 春暖花甲
     * images : ["http://01bug.com/000/yashiqi/public/uploads/goods/20191106/7d73831ae9b4e7941caa00d543892fda.jpg"]
     * add_time : 2019-11-06 10:13:21
     * _user : {"id":9,"nickname":"18782922932","avatar":"http://01bug.com/000/yashiqi/public/uploads/head/2a2150f19fd5232c8bf14206f7879ccf.png"}
     * praise : 2
     * comment : 7
     * is_praise : 0
     */

    public int id;
    public int user_id;
    public String content;
    public String add_time;
    public User _user;
    public int praise;
    public int comment;
    public int is_praise;
    public int is_care;
    public List<String> images;

    public static class User {
        /**
         * id : 9
         * nickname : 18782922932
         * avatar : http://01bug.com/000/yashiqi/public/uploads/head/2a2150f19fd5232c8bf14206f7879ccf.png
         */

        public int id;
        public String nickname;
        public String avatar;
    }

    public boolean isPraise() {
        return is_praise == 1;
    }

    public boolean isCare() {
        return is_care == 1;
    }

    public void reversePraise() {
        is_praise = isPraise() ? 0 : 1;
        praise = isPraise() ? praise + 1 : praise - 1;
    }

    public void reverseCare() {
        is_care = isCare() ? 0 : 1;
    }
}