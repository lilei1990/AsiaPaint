package com.asia.paint.utils.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author by chenhong14 on 2019-11-30.
 */
public class DateUtils {

	public static final String DATE_PATTERN_1 = "yyyy-MM-dd";
	public static final String DATE_PATTERN_2 = "yyyy-MM-dd HH:mm";
	public static final String DATE_PATTERN_3 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN_4 = "yyyy/MM/dd";
	public static final String DATE_PATTERN_5 = "HH:mm:ss";


	public static Date getDate(String date, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
			return format.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date(System.currentTimeMillis());
	}

	public static String dateToString(Date date, String pattern) {
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
			result = format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String timeToString(long time, String pattern) {
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
			result = format.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取现在时间 年月日时分秒
	 *
	 * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 比较两个日期大小
	 *
	 * @param startDate 开始日期 格式yyyy-MM-dd HH:mm:ss
	 * @param endDate   结束日期 格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean compareDate(String startDate, String endDate) {
		if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
			return false;
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(startDate);
			Date dt2 = df.parse(endDate);
			if (dt1.getTime() > dt2.getTime()) {
				//2017-10-10大于2017-10-11
				System.out.println("dt1大于dt2");
				return true;
			} else if (dt1.getTime() < dt2.getTime()) {
				//2017-10-10小于2017-10-11
				System.out.println("dt1小于dt2");
				return false;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取两个时间相差分钟数
	 */
	public static long getMillisecond(String oldTime, String newTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long newL = 0;
		long oldL = 0;
		try {
			newL = df.parse(newTime).getTime();
			oldL = df.parse(oldTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diff = (newL - oldL);
		return diff;
	}
}
