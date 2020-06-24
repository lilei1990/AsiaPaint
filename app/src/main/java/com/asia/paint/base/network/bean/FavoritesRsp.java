package com.asia.paint.base.network.bean;


/**
 * @author by chenhong14 on 2019-12-02.
 */
public class FavoritesRsp extends BaseListRsp<FavoritesRsp.Favorite> {


	/**
	 * result : [{"id":33,"goods_id":142,"user_id":11,"add_time":1575212210,"goods_status":"normal","_goods":{"goods_id":142,"goods_sn":"2",
	 * "goods_name":"亚士漆高级油漆","goods_number":94,"content":"<p><img src=\"/000/yashiqi/public/uploads/images/157442150760.png\" style=\"float:none;\"
	 * title=\"5.png\"/><\/p><p><img src=\"/000/yashiqi/public/uploads/images/15744215073341.png\" style=\"float:none;\" title=\"13.png\"/><\/p>",
	 * "cate_id":1,"is_spec":1,"spec_qty":1,"spec_name_1":"规格","spec_name_2":null,"status":"normal","is_best":"1","add_time":1573006401,"last_update":0,
	 * "default_spec":796,"default_image":["http://01bug.com/000/yashiqi/public/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg"],
	 * "market_price":"8.00","price":"2.00","sort":50,"sid":null,"desc":null,"sales":14,"weight":"0.0","level_1":10,"level_2":20,"level_3":30,"is_receipt":0,
	 * "is_return":0,"is_service":0,"is_promote":0,"promote_type":1,"promote_price":"0.00","promote_strtime":0,"promote_endtime":0,"promote_status":1,
	 * "view":236,"video":""}}]
	 * totalpage : 1
	 */


	public static class Favorite {
		/**
		 * id : 33
		 * goods_id : 142
		 * user_id : 11
		 * add_time : 1575212210
		 * goods_status : normal
		 * _goods : {"goods_id":142,"goods_sn":"2","goods_name":"亚士漆高级油漆","goods_number":94,"content":"<p><img
		 * src=\"/000/yashiqi/public/uploads/images/157442150760.png\" style=\"float:none;\" title=\"5.png\"/><\/p><p><img
		 * src=\"/000/yashiqi/public/uploads/images/15744215073341.png\" style=\"float:none;\" title=\"13.png\"/><\/p>","cate_id":1,"is_spec":1,"spec_qty":1,
		 * "spec_name_1":"规格","spec_name_2":null,"status":"normal","is_best":"1","add_time":1573006401,"last_update":0,"default_spec":796,
		 * "default_image":["http://01bug.com/000/yashiqi/public/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg"],"market_price":"8.00",
		 * "price":"2.00","sort":50,"sid":null,"desc":null,"sales":14,"weight":"0.0","level_1":10,"level_2":20,"level_3":30,"is_receipt":0,"is_return":0,
		 * "is_service":0,"is_promote":0,"promote_type":1,"promote_price":"0.00","promote_strtime":0,"promote_endtime":0,"promote_status":1,"view":236,
		 * "video":""}
		 */

		public int id;
		public int goods_id;
		public int user_id;
		public int add_time;
		public String goods_status;
		public Goods _goods;

		public boolean isSelected;
	}
}
