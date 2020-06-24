package com.asia.paint.base.util;

import com.asia.paint.base.network.bean.CartGoods;

import java.util.List;

/**
 * @author by chenhong14 on 2019-11-20.
 */
public final class CartUtils {

    private CartUtils() {

    }

    public static String getRecIds(List<CartGoods> goods) {
        StringBuilder builder = new StringBuilder();
        if (goods != null && !goods.isEmpty()) {
            for (CartGoods bean : goods) {
                if (bean != null) {
                    builder.append(bean.rec_id).append(",");
                }
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
