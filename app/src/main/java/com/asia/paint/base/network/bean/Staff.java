package com.asia.paint.base.network.bean;

import android.text.TextUtils;

import com.asia.paint.base.util.ILetter;

public class Staff extends ILetter implements Comparable<Staff> {
    /**
     * id : 9
     * group_id : 1
     * username : 18782922932
     * nickname : lee
     * email :
     * mobile : 18782922932
     * avatar : /uploads/poster/5e0e47a4ac0efbdbf2554fa7a7a8a6c4.jpg
     * level : 1
     * sex : 1
     * birthday : 1982-12-01
     * bio :
     * money : 18002.00
     * score : 500
     * successions : 2
     * maxsuccessions : 2
     * prevtime : 1577529843
     * logintime : 1577522468
     * loginip : 222.209.152.83
     * loginfailure : 0
     * joinip : 125.70.179.112
     * jointime : 1577096734
     * createtime : 1569396084
     * updatetime : 1577522468
     * token :
     * pid : 10014
     * status : normal
     * parent : 0
     * verification :
     * is_seller : 0
     * openid : oAOog5YjQZLPvMEeYXIFFvo0MH6E
     * wxopenid : null
     */

    public int id;
    public int group_id;
    public String username;
    public String nickname;
    public String email;
    public String mobile;
    public String avatar;
    public int level;
    public int sex;
    public String birthday;
    public String bio;
    public String money;
    public int score;
    public int successions;
    public int maxsuccessions;
    public int prevtime;
    public int logintime;
    public String loginip;
    public int loginfailure;
    public String joinip;
    public int jointime;
    public int createtime;
    public int updatetime;
    public String token;
    public int pid;
    public String status;
    public int parent;
    public String verification;
    public int is_seller;
    public String openid;
    public Object wxopenid;

    private String letter;
    private String content;

    @Override
    public String getLetter() {
        return letter;
    }

    @Override
    public String getContent() {
        return nickname;
    }

    @Override
    public void setContent(String content) {

    }


    @Override
    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public int compareTo(Staff staff) {
        if (staff == null) {
            return -1;
        }
        if (TextUtils.equals(staff.getLetter(), "#")) {
            return -1;
        } else if (TextUtils.equals(getLetter(), "#")) {
            return 1;
        } else {
            return getLetter().compareTo(staff.getLetter());
        }
    }
}