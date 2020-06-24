package com.asia.paint.base.network.bean;

import java.util.List;

/**
 * 组合列表
 * @author tangkun
 */
public class PromotionComposeRsp {

    /**
     * result : [{"compose_id":1,"name":"组合商品促销","price":"1400.00","ids":"199,175","status":1,"add_time":1582986219,"market_price":1508,"_goods":[{"goods_id":"199","goods_name":"净味五合一内墙水漆·鸢尾花 内墙乳胶漆面漆(18袋)+底漆(9袋)套装","num":1,"goods_number":997,"status":"normal","default_image":["https://store.asia-paints.com/uploads/goods/20191230/c1129eeed1c12d489b025af8fea6e552.jpg"]},{"goods_id":"175","goods_name":"净味竹炭抗甲醛全效内墙水漆·茉莉 2L","num":1,"goods_number":11987,"status":"normal","default_image":["https://store.asia-paints.com/uploads/goods/20191231/69c87a896e0a075f25a6910f137dfa31.jpg"]}]}]
     * totalpage : 1
     */

    public String totalpage;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * compose_id : 1
         * name : 组合商品促销
         * price : 1400.00
         * ids : 199,175
         * status : 1
         * add_time : 1582986219
         * market_price : 1508
         * _goods : [{"goods_id":"199","goods_name":"净味五合一内墙水漆·鸢尾花 内墙乳胶漆面漆(18袋)+底漆(9袋)套装","num":1,"goods_number":997,"status":"normal","default_image":["https://store.asia-paints.com/uploads/goods/20191230/c1129eeed1c12d489b025af8fea6e552.jpg"]},{"goods_id":"175","goods_name":"净味竹炭抗甲醛全效内墙水漆·茉莉 2L","num":1,"goods_number":11987,"status":"normal","default_image":["https://store.asia-paints.com/uploads/goods/20191231/69c87a896e0a075f25a6910f137dfa31.jpg"]}]
         */

        public int compose_id;
        public String name;
        public String price;
        public String ids;
        public String status;
        public String add_time;
        public String market_price;
        public List<GoodsBean> _goods;

        public static class GoodsBean {
            /**
             * goods_id : 199
             * goods_name : 净味五合一内墙水漆·鸢尾花 内墙乳胶漆面漆(18袋)+底漆(9袋)套装
             * num : 1
             * goods_number : 997
             * status : normal
             * default_image : ["https://store.asia-paints.com/uploads/goods/20191230/c1129eeed1c12d489b025af8fea6e552.jpg"]
             */

            public String goods_id;
            public String goods_name;
            public String num;
            public String goods_number;
            public String status;
            public List<String> default_image;
        }
    }
}
