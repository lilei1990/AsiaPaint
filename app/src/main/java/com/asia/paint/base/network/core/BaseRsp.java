package com.asia.paint.base.network.core;

/**
 * @author by chenhong14 on 2019-11-10.
 */
public class BaseRsp<T> {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private int code;
    private String msg;
    private long time;
    private T data;

    @Override
    public String toString() {
        return "BaseRsp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                ", data=" + data.toString() +
                '}';
    }
}
