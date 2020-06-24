package com.asia.paint.base.network.bean;

public class UserInfo {
    /**
     * id : 11
     * username : 15196611757
     * nickname : 15196611757
     * mobile : 15196611757
     * avatar : data:image/svg+xml;base64,
     * PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgaGVpZ2h0PSIxMDAiIHdpZHRoPSIxMDAiPjxyZWN0IGZpbGw9InJnYigyMjksMTYwLDE3MykiIHg9IjAiIHk9IjAiIHdpZHRoPSIxMDAiIGhlaWdodD0iMTAwIj48L3JlY3Q+PHRleHQgeD0iNTAiIHk9IjUwIiBmb250LXNpemU9IjUwIiB0ZXh0LWNvcHk9ImZhc3QiIGZpbGw9IiNmZmZmZmYiIHRleHQtYW5jaG9yPSJtaWRkbGUiIHRleHQtcmlnaHRzPSJhZG1pbiIgYWxpZ25tZW50LWJhc2VsaW5lPSJjZW50cmFsIj4xPC90ZXh0Pjwvc3ZnPg==
     * money : 0.00
     * score : 0
     * status : normal
     * token : 0b867b7a-9777-4d78-be06-f0e983771543
     * user_id : 11
     * createtime : 1573913828
     * expiretime : 1576505828
     * expires_in : 2592000
     * is_seller : 0
     */

    public int id;
    public String username;
    public String nickname;
    public String mobile;
    public String avatar;
    public String money;
    public String score;
    public String status;
    public String token;
    public int user_id;
    public int createtime;
    public int expiretime;
    public int expires_in;
    public int is_seller;
    //是否绑定【0否，1：是】
    public int is_bind;

    public boolean isBind() {
        return is_bind == 1;
    }
}