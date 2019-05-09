package com.matrix.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	/**
	 * 定义默认的日期时间格式
	 */
	public final static String CONST_PARSE_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 定义月份的默认格式
	 */
	public final static String CONST_PARSE_MONTH = "yyyy-MM";

	/**
	 * 定义Code的起始值
	 */
	public final static String CONST_PARSE_CODE = "YYMMdd";

	/**
	 * 获取月度第一天
	 */
	public final static String CONST_PARSE_MONTH_FIRST_DAY = "yyyy-MM-01 00:00:00";

	/**
	 * 获取每天0时0分0秒
	 */
	public final static String CONST_PARSE_DATETIME_0 = "yyyy-MM-dd 00:00:00";

	public static final String DATE_FORMAT_DATEONLY = "yyyy-MM-dd"; // 年/月/日

	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 年/月/日

	public static final SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLY);

	public static final SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME);

	/**
	 * 强制转换日期
	 * 
	 * @param sDate
	 * @return
	 */
	public static Date parseDate(String sDate) {
		try {
			return DateUtils.parseDate(sDate, new String[] { CONST_PARSE_DATETIME });
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * alias upDate 获取日期的格式
	 * 
	 * @param dDate
	 * @return
	 */
	public static String formatDate(Date dDate) {

		return formatDate(dDate, CONST_PARSE_DATETIME);
	}

	/**
	 * 获取每天0时0分0秒
	 * 
	 * @param dDate
	 * @return
	 */
	public static String formatDateZero(Date dDate) {

		return formatDate(dDate, CONST_PARSE_DATETIME_0);
	}

	/**
	 * alias upDate 获取日期的格式
	 * 
	 * @param dDate
	 * @param sParse
	 *            规则表达式
	 * @return
	 */
	public static String formatDate(Date dDate, String sParse) {
		SimpleDateFormat sFormat = new SimpleDateFormat(sParse);
		return sFormat.format(dDate);
	}

	/**
	 * 根据时间返回月份
	 * 
	 * @param sDateTime
	 * @return
	 */
	public static String upMonth(String sDateTime) {
		return formatDate(parseDate(sDateTime), CONST_PARSE_MONTH);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String upNow() {
		return formatDate(new Date());
	}

	/**
	 * 获取当前时间的指定格式
	 * 
	 * @param sExp
	 *            表达式<br/>
	 *            d:表示日<br/>
	 *            s:表示秒<br/>
	 *            M:表示月<br/>
	 *            h:表示小时<br/>
	 * @return
	 */
	public static String upDateTimeAdd(String sExp) {

		String sEndType = sExp.substring(sExp.length() - 1);

		int iExp = Integer.valueOf(sExp.substring(0, sExp.length() - 1));

		String sExpString = "";

		if (sEndType.equals("d")) {
			sExpString = upDateTimeAdd(new Date(), Calendar.DATE, iExp);
		} else if (sEndType.equals("M")) {
			sExpString = upDateTimeAdd(new Date(), Calendar.MONTH, iExp);
		} else if (sEndType.equals("s")) {
			sExpString = upDateTimeAdd(new Date(), Calendar.SECOND, iExp);
		} else if (sEndType.equals("h")) {
			sExpString = upDateTimeAdd(new Date(), Calendar.HOUR, iExp);
		} else {
			sExpString = upNow();
		}

		return sExpString;

	}

	/**
	 * 获取制定日期的特定格式
	 * 
	 * @param date
	 * @param iType
	 * @param iAmount
	 * @return
	 */
	public static String upDateTimeAdd(Date date, int iType, int iAmount) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(iType, iAmount);

		return formatDate(cal.getTime());

	}

	/**
	 * 获取时间段之内的月份
	 * 
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public static List<String> getMonthList(String beginMonth, String endMonth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		List<String> monthList = new ArrayList<String>();
		try {
			Date begin = format.parse(beginMonth);
			Date end = format.parse(endMonth);
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(begin);
			endCalendar.setTime(end);
			int months = (endCalendar.get(Calendar.YEAR) - beginCalendar.get(Calendar.YEAR)) * 12
					+ (endCalendar.get(Calendar.MONTH) - beginCalendar.get(Calendar.MONTH));
			monthList.add(beginMonth);
			for (int i = 0; i < months; i++) {
				beginCalendar.add(Calendar.MONTH, 1);
				monthList.add(format.format(beginCalendar.getTime()));
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		return monthList;
	}

	/**
	 * 根据时间类型获取时间，比如：时间中的年，时间中的月，时间中的日
	 * 
	 * @param sDateTime
	 *            可以为null或""
	 * @param timeType
	 * @return
	 */
	public static int getTimeEleByType(String sDateTime, int timeType) {

		Calendar calendar = Calendar.getInstance();
		if (null == sDateTime || "" == sDateTime)
			calendar.setTime(new Date());
		else
			calendar.setTime(parseDate(sDateTime));

		int timeEle = 0;

		switch (timeType) {
		case Calendar.DAY_OF_MONTH:
			timeEle = calendar.get(Calendar.DAY_OF_MONTH);
			break;
		case Calendar.MONTH:
			timeEle = calendar.get(Calendar.MONTH) + 1;
			break;
		case Calendar.YEAR:
			timeEle = calendar.get(Calendar.YEAR);
			break;
		default:
			timeEle = -1;
			break;
		}

		return timeEle;
	}

	public static String upCodeStart() {
		SimpleDateFormat sFormat = new SimpleDateFormat(CONST_PARSE_CODE);
		return sFormat.format(new Date());
	}

	/**
	 * 判断时间是否在某个范围内 只精确到时分秒的判断 忽略日期
	 * 
	 * @param sTime
	 * @param sStart
	 * @param sEnd
	 * @return
	 */
	public static boolean upTimeBetween(String sTime, String sStart, String sEnd) {
		boolean bFlag = true;

		sTime = StringUtils.right(sTime, 8);
		if (StringUtils.isNotBlank(sStart)) {
			bFlag = sTime.compareTo(sStart) > 0;
		}

		if (StringUtils.isNotBlank(sEnd)) {
			bFlag = sTime.compareTo(sEnd) < 0;
		}

		return bFlag;
	}

	/**
	 * 
	 * 方法: getSysDate <br>
	 * 描述: 获取当前系统日期 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 上午7:26:21
	 * 
	 * @return
	 */
	public static String getSysDate() {
		return sdfDateOnly.format(new Date());
	}

	/**
	 * 
	 * 方法: getSysDateTime <br>
	 * 描述: 获取当前系统时间 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 上午7:26:54
	 * 
	 * @return
	 */
	public static String getSysDateTime() {
		return sdfDateTime.format(new Date());
	}

	public static String formatDate(String pattern) {
		SimpleDateFormat sFormat = new SimpleDateFormat(pattern);
		return sFormat.format(new Date());
	}
	
	/**
	 * @descriptions 获取16进制表示的当前时间
	 *
	 * @return
	 * @date 2017年8月2日 下午3:32:12
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public static String getDateHex() {
		return Integer.toHexString(Integer.valueOf(formatDate("yyMMdd")));
	}
	
	public static String getDateLongHex(String format) {
		return Long.toHexString(Integer.valueOf(formatDate(format)));
	}
}






















