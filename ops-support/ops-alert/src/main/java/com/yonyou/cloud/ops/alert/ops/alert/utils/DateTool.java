package com.yonyou.cloud.ops.alert.ops.alert.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTool {

	private static final Logger log = LoggerFactory.getLogger(DateTool.class);

	public static final String DAYTYPE = "yyyy-MM-dd";
	private static SimpleDateFormat SHORT = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat NORMAL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

	public static String getDate(String format, String date) throws Exception {

		if (StringUtils.isBlank(date)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date d = df.parse(date);
			return df.format(d);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static boolean judgeDate(String checkin, String star, String end) throws Exception {

		try {
			Date starDate = SHORT.parse(star);
			Date endDate = SHORT.parse(end);
			Date checkinData = SHORT.parse(checkin);
			if (checkinData.getTime() >= starDate.getTime() && checkinData.getTime() <= endDate.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public static String getNextDate(String startDate) {

		try {
			SHORT.parse(startDate);
			Calendar clStart = SHORT.getCalendar();
			clStart.add(Calendar.DAY_OF_MONTH, +1);
			return SHORT.format(clStart.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	public static int getBetweenDays(String t1, String t2) {
		int betweenDays = 0;
		try {
			Date d1 = SHORT.parse(t1);
			Date d2 = SHORT.parse(t2);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(d1);
			c2.setTime(d2);
			if (c1.after(c2)) {
				c1 = c2;
				c2.setTime(d1);
			}
			int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
			betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
			for (int i = 0; i < betweenYears; i++) {
				c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
				betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
			}
			return betweenDays;
		} catch (Exception e) {
			return -1;
		}

	}

	public static Date toDate(String date) {
		try {
			return SHORT.parse(date);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * @return 返回 2012-05-12格式的日期字符串
	 */
	public static String getNowShort() {
		Date date = new Date();
		return SHORT.format(date);
	}

	/**
	 * @return 返回 2012-08-22 17:56:13格式的时间日期
	 */

	public static String getDate(Date date) {
		return NORMAL.format(date);
	}

	/**
	 * @return 返回 20120822格式的时间日期
	 */
	public static String getNowDate() {
		Date date = new Date();
		return sf.format(date);
	}

	/**
	 * @return 返回 2012-08-22格式的时间日期
	 */
	public static String getShortDate() {
		Date date = new Date();
		return SHORT.format(date);
	}

	/**
	 * @return 返回 2012-08-22 17:56:13格式的时间日期
	 */
	public static String getNormal() {
		return NORMAL.format(new Date());
	}

	/**
	 * 获得两个日期之间的日期列表
	 * 
	 * @param start 开始日期
	 * @param end 结束日期
	 * @param flag 0：包含开始结束，1：包含开始，2：包含结束，3：都不包含
	 * @return
	 */
	public static List<String> getDay(String start, String end, int flag) {
		List<String> list = new ArrayList<String>();
		try {
			Calendar startCa = new GregorianCalendar();
			startCa.setTime(SHORT.parse(start));
			Calendar endCa = new GregorianCalendar();
			endCa.setTime(SHORT.parse(end));
			if (flag == 0 || flag == 1) {
				list.add(start);
			}
			while (endCa.compareTo(startCa) > 0) {
				startCa.add(Calendar.DAY_OF_MONTH, 1);
				list.add(SHORT.format(startCa.getTime()));
			}
			if (flag == 1 || flag == 3) {
				list.remove(list.size() - 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
}
