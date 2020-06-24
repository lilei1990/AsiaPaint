package com.asia.paint.base.network.bean;


import java.util.List;

/**
 * @author by chenhong14 on 2019-11-22.
 */
public class CommentRsp extends BaseListRsp<Comment> {


    /**
     * result : [{"userid":9,"is_hide":0,"rec_id":801,"specification":"2","comment":"商品不错下次还买","comment_score":"5.0","comment_time":"2019-11-09 15:47:02",
     * "comment_video":"","comment_images":["http://01bug.com/000/yashiqi/public/uploads/goods/20191106/7d73831ae9b4e7941caa00d543892fda.jpg"],"reply":null,
     * "reply_time":"","_user":{"id":9,"nickname":"18782922932","avatar":"data:image/svg+xml;base64,
     * PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgaGVpZ2h0PSIxMDAiIHdpZHRoPSIxMDAiPjxyZWN0IGZpbGw9InJnYigxNjYsMTYwLDIyOSkiIHg9IjAiIHk9IjAiIHdpZHRoPSIxMDAiIGhlaWdodD0iMTAwIj48L3JlY3Q+PHRleHQgeD0iNTAiIHk9IjUwIiBmb250LXNpemU9IjUwIiB0ZXh0LWNvcHk9ImZhc3QiIGZpbGw9IiNmZmZmZmYiIHRleHQtYW5jaG9yPSJtaWRkbGUiIHRleHQtcmlnaHRzPSJhZG1pbiIgYWxpZ25tZW50LWJhc2VsaW5lPSJjZW50cmFsIj4xPC90ZXh0Pjwvc3ZnPg=="}}]
     * totalpage : 1
     * top : [1,0,1]
     */
    public List<Integer> top;
    public String score;
}
