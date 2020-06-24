package com.asia.paint.base.network.core;

/**
 * @author by chenhong14 on 2019-11-16.
 */
public enum NetworkCode {
    /**
     * code：返回识别码1成功，0失败, 401:未登录,403无权限访问,500:无响应。。
     */
    FAIL(0, "失败"),
    SUCCESS(1, "成功"),
    NO_LOGIN(401, "未登录"),
    NO_PERMISSION(500, "无响应");

    private int code;

    private String description;

    NetworkCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
