package com.asia.paint.utils.utils;

import android.text.TextUtils;

/**
 * @author by chenhong14 on 2019-11-19.
 */
public final class PriceUtils {

	private PriceUtils() {

	}

	private static final String RMB = "¥";

	public static String getPrice(String price) {
		return TextUtils.isEmpty(price) ? "" : String.format("%s %s", RMB, price);
	}

	public static String getPriceTrimZero(String price) {
		return TextUtils.isEmpty(price) ? "" : String.format("%s %s", RMB, price).replaceAll(".00", "");
	}
}
