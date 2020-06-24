package com.asia.paint.base.network.bean;

import com.asia.paint.utils.utils.CopyUtils;

import java.util.List;

public  class CartGoods extends CopyUtils.Copyable {
        /**
         * rec_id : 276
         * rec_type : 0
         * user_id : 11
         * goods_id : 142
         * goods_name : 亚士漆高级油漆
         * spec_id : 796
         * specification : 2
         * price : 2.00
         * quantity : 1
         * goods_image : http://01bug.com/uploads/goods/20191116/e6616ed70bd8f02f8d2b4cee4e6feb33.jpg
         * is_shipping : 1
         * sid : 0
         * act_id : 0
         * goods_status : normal
         */

        public int rec_id;
        public int rec_type;
        public int user_id;
        public int goods_id;
        public String goods_name;
        public int spec_id;
        public String specification;
        public String price;
        public int quantity;
        public String goods_image;
        public int is_shipping;
        public int sid;
        public int act_id;
        public String goods_status;
        public List<Gift> _gift;

        public boolean isCheck() {
            return is_shipping == 1;
        }

        @Override
        public CartGoods copy() {

            CartGoods newCartGoods = new CartGoods();
            newCartGoods.rec_id = rec_id;
            newCartGoods.rec_type = rec_type;
            newCartGoods.user_id = user_id;
            newCartGoods.goods_id = goods_id;
            newCartGoods.goods_name = goods_name;
            newCartGoods.spec_id = spec_id;
            newCartGoods.specification = specification;
            newCartGoods.price = price;
            newCartGoods.quantity = quantity;
            newCartGoods.goods_image = goods_image;
            newCartGoods.is_shipping = is_shipping;
            newCartGoods.sid = sid;
            newCartGoods.act_id = act_id;
            newCartGoods.goods_status = goods_status;
            return newCartGoods;
        }
    }