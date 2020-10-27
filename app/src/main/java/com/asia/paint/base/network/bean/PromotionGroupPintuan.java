package com.asia.paint.base.network.bean;

import java.io.Serializable;

public class PromotionGroupPintuan implements Serializable {

	/**
	 * log_id : 12
	 * groupbuy_id : 9
	 * order_id : 1028
	 * pid : 0
	 * user_id : 10
	 * endtime : 2020-03-31 00:00:00
	 * paystatus : 1
	 * status : 0
	 * number : 2
	 * add_time : 1584201571
	 * need : 1
	 * second : 893014
	 * avatar : https://store.asia-paints.com/uploads/head/5cd1bd36d31c4698ef49bfe2dd4ecb24.png
	 * nickname : fly one1
	 */

	public int log_id;
	public String groupbuy_id;
	public String order_id;
	public String pid;
	public String user_id;
	public String endtime;
	public String paystatus;
	public String status;
	public String number;
	public String add_time;
	public String need;
	public String second;
	public String avatar;
	public String nickname;

	// 列表中展示的倒计时(非服务器返回字段)
	public String showHour;
	public String showMinute;
	public String showSecond;
}
