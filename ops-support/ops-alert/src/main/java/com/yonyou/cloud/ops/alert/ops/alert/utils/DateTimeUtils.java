
package com.yonyou.cloud.ops.alert.ops.alert.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author daniell
 *
 */
public class DateTimeUtils {

	private static final Logger log = LoggerFactory.getLogger(DateTimeUtils.class);

	public static final String DAYTYPE = "yyyy-MM-dd";

	public static final String DAYTIMETYPE = "yyyy-MM-dd HH:mm";

	public static final String TIMETYPE = "yyyy-MM-dd HH:mm:ss";
	
	public static final String TIMETYPESSS = "yyyy-MM-dd HH:mm:ss.sss";

	public static final String DATETYPE = "yyyy年MM月dd日";

	public static final String DateTypeYear = "yyyy";

	public static final String DateTypeMonth = "yyyyMM";

	public static final String Date4ExportType = "yyyyMMdd";

	public static final String DateDetail4ExportType = "yyyyMMddHHmmss";

	public static Long parseTime(Date date) {

		if (null == date) {
			return null;
		}
		return date.getTime();
	}

	public static Long parseTime(String date) {

		return parseTime(date, DAYTYPE);
	}

	public static Long parseDateTime(String date) {
		return parseTime(date, TIMETYPESSS);
	}

	public static Long parseTime(String date, String time, String format) {

		if (StringUtils.isBlank(date)) {
			return null;
		}
		return parseTime(date + " " + time, format);
	}

	public static Long parseTime(String date, String format) {

		if (StringUtils.isBlank(date)) {
			return null;
		}
		try {
			return parseDate(date.trim(), format).getTime();
		} catch (Exception e) {
			log.error("parseTime error : ", e);
			return null;
		}
	}

	public static Date parseDate(String date) {

		return parseDate(date, DAYTYPE);
	}

	public static Date parseDate(String date, String format) {

		if (StringUtils.isBlank(date)) {
			return null;
		}
		try {
			return DateUtils.parseDate(date.trim(), format);
		} catch (ParseException e) {
			log.error("parseDate error : ", e);
			return null;
		}
	}

	public static String formatDate(Long date) {

		return formatDate(date, DAYTYPE);
	}

	public static String formatDateType(Long date) {

		return formatDate(date, DATETYPE);
	}

	public static String formatTimeType(Long date) {

		return formatDate(date, TIMETYPE);
	}

	public static String formatDate(Long date, String format) {

		if (null == date) {
			return null;
		}
		return DateFormatUtils.format(date, format);
	}

	public static String formatDate(Date date) {

		return formatDate(date, DAYTYPE);
	}

	public static String formatDateTime(Long date) {
		return formatDate(date, DAYTIMETYPE);
	}

	public static String formatDate(Date date, String format) {

		if (null == date) {
			return null;
		}
		return DateFormatUtils.format(date, format);
	}

	public static String formatDate(String date) throws Exception {

		return formatDate(date, DAYTYPE);
	}

	public static String formatDate(String date, String format) throws Exception {

		if (StringUtils.isBlank(date)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date d = df.parse(date);
			return df.format(d);
		} catch (Exception e) {
			log.error("formatDate error ：", e);
			throw new Exception();
		}
	}

	public static Long formatSecond(Integer dayNumber) {

		if (null == dayNumber) {
			return 100 * 24 * 60 * 60 * 1000L;
		}
		return dayNumber * 24 * 60 * 60 * 1000L;
	}

	/**
	 * 季度开始时间，如2014-01-1 00:00:00
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getCurrentQuarterStartTime(int year, int quarter) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_MONTH, 1);

		switch (quarter) {
		case 1:// 一季度
			c.set(Calendar.MONTH, 0);
			break;
		case 2:// 二季度
			c.set(Calendar.MONTH, 3);
			break;
		case 3:// 三季度
			c.set(Calendar.MONTH, 6);
			break;
		case 4:// 四季度
			c.set(Calendar.MONTH, 9);
			break;
		default:
			c.set(Calendar.MONTH, 0);
		}
		

		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 0);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 3);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 6);
			}

			else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 9);
			}

			System.out.println(c.getTime());

			now = DateUtils.parseDate(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 00:00:00",
					new String[] { "yyyy-MM-dd HH:mm:ss" });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 * 
	 * 
	 */
	public static Date getCurrentQuarterEndTime(int year, int quarter) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_MONTH, 1);

		switch (quarter) {
		case 1:// 一季度
			c.set(Calendar.MONTH, 0);
			break;
		case 2:// 二季度
			c.set(Calendar.MONTH, 3);
			break;
		case 3:// 三季度
			c.set(Calendar.MONTH, 6);
			break;
		case 4:// 四季度
			c.set(Calendar.MONTH, 9);
			break;
		default:
			c.set(Calendar.MONTH, 0);
		}

		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = DateUtils.parseDate(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " 23:59:59",
					new String[] { "yyyy-MM-dd HH:mm:ss" });
		} catch (Exception e) {
			e.printStackTrace();
		}

		return now;
	}

	/**
	 * 秒转换成时分秒
	 * 
	 * @param seconds
	 *            Integer
	 * @return
	 */
	public static String formatSeconds(Integer seconds) {
		Integer theTime = seconds;// 秒
		Integer theTime1 = 0;// 分
		Integer theTime2 = 0;// 小时
		if (theTime > 60) {
			theTime1 = theTime / 60;
			theTime = theTime % 60;
			if (theTime1 > 60) {
				theTime2 = theTime1 / 60;
				theTime1 = theTime1 % 60;
			}
		}
		String result = "" + theTime + "秒";
		if (theTime1 > 0) {
			result = "" + theTime1 + "分" + result;
		}
		if (theTime2 > 0) {
			result = "" + theTime2 + "小时" + result;
		}
		return result;
	}

	public static String getNowDate(String format) {

		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 秒转换成时分秒
	 * 
	 * @param seconds
	 *            Long
	 * @return
	 */
	public static String formatSeconds(long seconds) {
		Long theTime = seconds;// 秒
		Long theTime1 = 0L;// 分
		Long theTime2 = 0L;// 小时
		if (theTime > 60) {
			theTime1 = theTime / 60;
			theTime = theTime % 60;
			if (theTime1 > 60) {
				theTime2 = theTime1 / 60;
				theTime1 = theTime1 % 60;
			}
		}
		String result = "" + theTime + "秒";
		if (theTime1 > 0) {
			result = "" + theTime1 + "分" + result;
		}
		if (theTime2 > 0) {
			result = "" + theTime2 + "小时" + result;
		}
		return result;
	}
}
