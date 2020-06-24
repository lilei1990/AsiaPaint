package com.asia.paint.base.network.core;

import java.util.List;

/**
 * 解决后台直接返回集合解析
 *
 * @author tangkun
 */
public class BaseListRespose<T> {
	public int code;
	public String msg;
	public long time;
	public List<T> data;

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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseListRespose{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", time=" + time +
				", data=" + data +
				'}';
	}
}
