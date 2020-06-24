package com.asia.paint.base.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 金额转化工具，long <-> String之间的转化
 * PS:下一代均使用Long表示金额
 */
public final class MoneyUtils {

    public static final String PRICE_SYMBOL = "￥";
    public static final String MINUS_SIGN = "-";
    public static final String DECIMAL_POINT = ".";

    private MoneyUtils() {
    }

    /**
     * 将金额元转换成单位分,存进数据库
     *
     * @param price double类型价格
     * @return string
     */
    public static long getPriceToCent(double price) {
        return getPriceToCent(Double.toString(price));
    }

    /**
     * 将金额元转换成单位分
     *
     * @param price float类型价格
     */
    public static long getPriceToCent(float price) {
        return getPriceToCent(Float.toString(price));
    }

    public static long getPriceToCent(String price) {
        try {
            BigDecimal priceBigDecimal = new BigDecimal(price);
            BigDecimal result = priceBigDecimal.multiply(new BigDecimal("100"));
            return result.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 将金额转换成单位
     *
     * @param price 价格
     * @return string
     */
    public static String getPrice(long price) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2); // 小数点后最大位数为2位
        nf.setGroupingUsed(false);  // 不按照科学计数法显示
        return nf.format((double) price / 100); // 无float型输入参数
    }

    public static long getPrice(String priceWithSymbol) {
        if (TextUtils.isEmpty(priceWithSymbol)) {
            return 0;
        }
        if (priceWithSymbol.contains(PRICE_SYMBOL)) {
            priceWithSymbol = priceWithSymbol.replace(PRICE_SYMBOL, "");
        }
        return getPriceToCent(priceWithSymbol);
    }


    /**
     * 获取优惠价格展示，如：-￥10， +￥10
     */
    public static String getPriceWithSymbolAndPrefix(long money) {
        String prefix = money == 0 ? "" :
                money > 0 ? "-" : "+";
        return prefix + getPriceWithSymbol(Math.abs(money));
    }

    /**
     * 获取优惠价格展示，如：-10， +10
     */
    public static String getPriceWithPrefix(long money) {
        String prefix = money == 0 ? "" :
                money > 0 ? "-" : "+";
        return prefix + getPrice(Math.abs(money));
    }

    public static long multiplyWithInt(BigDecimal bigDecimal, long longValue) {
        return bigDecimal != null && longValue != 0 ?
                bigDecimal.multiply(new BigDecimal(longValue)).setScale(0, 4).longValue() : 0;
    }

    public static long multiplyWithIntResult(BigDecimal bigDecimal, long longValue) {
        return bigDecimal != null && longValue != 0 ?
                bigDecimal.multiply(new BigDecimal(longValue)).setScale(0, 4).longValue() : 0;
    }


    /**
     * 获取金额描述 如： ￥10
     *
     * @param price 价格，单位 分
     * @return 字符串
     */
    public static String getPriceWithSymbol(Long price) {
        if (price == null) {
            return PRICE_SYMBOL + "0";
        }

        return getLongPriceWithSymbol(price);
    }

    /**
     * 获取金额描述 如： ￥10
     *
     * @param price 价格，单位 分
     * @return 字符串
     */
    public static String getLongPriceWithSymbol(long price) {
        if (price == 0) {
            return PRICE_SYMBOL + "0";
        }

        boolean isNegative = price < 0;
        price = Math.abs(price);

        long yuan = price / 100;
        long jiao = price % 100 / 10;
        long fen = price % 10;

        StringBuilder sb = new StringBuilder(10);
        if (isNegative) {
            sb.append(MINUS_SIGN);
        }
        if (jiao == 0 && fen == 0) {
            return sb.append(PRICE_SYMBOL).append(yuan).toString();
        }

        if (fen == 0) {
            return sb.append(PRICE_SYMBOL).append(yuan).append(DECIMAL_POINT).append(jiao).toString();
        }

        return sb.append(PRICE_SYMBOL).append(yuan).append(DECIMAL_POINT).append(jiao).append(fen).toString();
    }

    /**
     * 格式化价格,无货币符号，千分位为逗号分开，保留两个小数，
     * 如： 500000 --> 5,000.00
     *
     * @param price
     * @return
     */
    public static String formatNoSignalKeepPercentile(long price) {
        return formatKeepPercentile(price).replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), "");
    }

    /**
     * 格式化价格,有货币符号，千分位为逗号分开，保留两个小数，
     * 如： 500000 --> ￥5,000.00
     *
     * @param price
     * @return
     */
    public static String formatKeepPercentile(long price) {
        return NumberFormat.getCurrencyInstance().format((double) price / 100);
    }

    /**
     * 测试字符串是否像金额输入
     * 以1-9头小数点后，最多两位小数，或者0.xxx 或者自然数据
     * 如： 1.01， 0.1，0.11，1，0.，1. 都符合
     * 00,0.001,002 则不符合
     *
     * @param matchString
     * @return
     */
    public static boolean isLikeMoney(String matchString) {
        if (matchString == null || "".equals(matchString)) {
            return true;
        }
        return matchString.matches("(^[1-9]\\d*[.]?[0-9]{0,2})|(^0[.][0-9]{0,2})|(\\d)|(^[1-9]\\d+)");
    }
}
