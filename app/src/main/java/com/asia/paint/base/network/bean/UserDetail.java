package com.asia.paint.base.network.bean;

public class UserDetail {
    /**
     * id : 11
     * group_id : 0
     * username : 15196611757
     * nickname : 15196611757
     * password : 7c204e4f063d68430694b2fb4f78e6ca
     * salt : u3sdyb
     * email :
     * mobile : 15196611757
     * avatar :
     * level : 1
     * sex : 0
     * birthday : null
     * bio :
     * money : 10000.00
     * score : 0
     * successions : 1
     * maxsuccessions : 1
     * prevtime : 1575082558
     * logintime : null
     * loginip : 182.138.180.247
     * loginfailure : 0
     * joinip :
     * jointime : null
     * createtime : 2019-11-16 22:17:08
     * updatetime : 2019-11-16 23:59:44
     * token :
     * status : normal
     * pid : null
     * parent : 0
     * verification : {"email":0,"mobile":0}
     * payword : null
     * is_seller : 0
     */

    public int id;
    public String username;
    public String nickname;
    public String password;
    public String mobile;
    public String avatar;
    /**
     * 性别【1:男，0女】
     */
    public int sex;
    public String birthday;
    public String money;
    public String score;
    public String token;
    public String status;
    public int parent;
    public int is_seller;
    public String ysh;
    public String code;
    //1：Vip    0：非Vip
    private int is_vip;

    public boolean isSeller() {
        return is_seller > 0;
    }

    public boolean isVip() {
        return is_vip == 1;

    }
    public boolean isMale() {
        return sex == 1;
    }
}