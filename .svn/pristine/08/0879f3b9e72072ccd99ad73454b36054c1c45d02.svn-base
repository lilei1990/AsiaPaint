package com.asia.paint.base.network.bean;

import java.util.List;

public class ApplyTaskRsp {

	/**
	 * task : {"id":1,"goods_ids":"168,171","type_ids":"45,43","price":"100.00"}
	 * goods : [{"goods_id":168,"goods_name":"净味三合一内墙水漆·白杨 2L","default_image":["https://store.asia-paints.com/uploads/goods/20200103/47431158b2b8725fe9161dc85e84967a.jpg"],"price":"60.00","number":1},{"goods_id":171,"goods_name":"净味竹炭全效内墙水漆·蒲公英 2L","default_image":["https://store.asia-paints.com/uploads/goods/20191231/f597460683c1ea4cb1a15c8c38308938.jpg"],"price":"99.00","number":1}]
	 * bonus : [{"type_id":43,"name":"50减10","image":"/uploads/color/20191211/7ac507a5888f2b0e259add14826dd000.jpg","desc":"","url":"50减10","bonus_type":1,"type":1,"strtime":"0","endtime":"0","min_money":"50.00","money":"10.00","status":1,"num":20,"limit":2,"group":1,"ids":"","add_time":"2019-12-11 10:16:14"},{"type_id":45,"name":"购物返券","image":"","desc":"购物返券","url":"购物返券","bonus_type":1,"type":1,"strtime":"0","endtime":"0","min_money":"100.00","money":"55.00","status":1,"num":100,"limit":1,"group":1,"ids":"0","add_time":"2020-03-02 22:26:30"}]
	 */

	public TaskBean task;
	public List<GoodsBean> goods;
	public List<BonusBean> bonus;

	public static class TaskBean {
		/**
		 * id : 1
		 * goods_ids : 168,171
		 * type_ids : 45,43
		 * price : 100.00
		 */

		public int id;
		public String goods_ids;
		public String type_ids;
		public String price;
	}

	public static class GoodsBean {
		/**
		 * goods_id : 168
		 * goods_name : 净味三合一内墙水漆·白杨 2L
		 * default_image : ["https://store.asia-paints.com/uploads/goods/20200103/47431158b2b8725fe9161dc85e84967a.jpg"]
		 * price : 60.00
		 * number : 1
		 */

		public int goods_id;
		public String goods_name;
		public String price;
		public int number;
		public List<String> default_image;
	}

	public static class BonusBean {
		/**
		 * type_id : 43
		 * name : 50减10
		 * image : /uploads/color/20191211/7ac507a5888f2b0e259add14826dd000.jpg
		 * desc :
		 * url : 50减10
		 * bonus_type : 1
		 * type : 1
		 * strtime : 0
		 * endtime : 0
		 * min_money : 50.00
		 * money : 10.00
		 * status : 1
		 * num : 20
		 * limit : 2
		 * group : 1
		 * ids :
		 * add_time : 2019-12-11 10:16:14
		 */

		public int type_id;
		public String name;
		public String image;
		public String desc;
		public String url;
		public int bonus_type;
		public int type;
		public String strtime;
		public String endtime;
		public String min_money;
		public String money;
		public int status;
		public int num;
		public int limit;
		public int group;
		public String ids;
		public String add_time;
	}
}
