package com.matrix.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 高级时间工具类，此处将操作更多的时间业务行为。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年4月23日 上午11:06:07 
 * @version 1.0.0.1
 */
public class DeluxeDateUtils {
	/**
	 * @description: 返回第几周的开始与结束日期
	 *
	 * @param year
	 * @param month
	 * @param weeks
	 * @author Yangcl
	 * @date 2020年4月23日 上午9:59:27 
	 * @version 1.0.0.1
	 */
	public Map<String, String> getScopeForweeks(int year, int month, int weeks) {
		Map<String, String> map = new HashMap<String, String>();
		String time = year + "-" + getMonthToStr(month);
		Map<String, Object> result = getDateScope(time);
		// 获取天数和周数
		int resultDays = Integer.parseInt(result.get("days").toString());
		int resultWeeks = Integer.parseInt(result.get("weeks").toString());
		
		if (weeks > resultWeeks) {	// 如果参数周数大于实际周数 则返回一个不存在的日期 默认设置为当前 天数+1
			int days = resultDays + 1;
			String beginDate = year + "-" + getMonthToStr(month) + "-" + days;
			map.put("beginDate", beginDate);
			map.put("endDate", beginDate);
		} else {	// 获取当月第一天属于周几
			map = this.getScope(time, resultDays, resultWeeks).get(weeks);
		}
		return map;
	}

	/**
	 * @description: 获取 某年某月 的天数以及周数
	 *
	 * @param time 格式：yyyy-MM
	 * @author Yangcl
	 * @date 2020年4月23日 上午10:28:45 
	 * @version 1.0.0.1
	 */
	public Map<String, Object> getDateScope(String time) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (time.length() <= 7) {  // 保证日期位数
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(time + "-01");
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				// 获取天数
				int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// 获取周数
				int weeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
				map.put("days", days);
				map.put("weeks", weeks);
			} else {
				throw new RuntimeException("日期格式不正确");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @description: 获取日期属于周几
	 *
	 * @param time 格式：yyyy-MM-dd 
	 * @author Yangcl
	 * @date 2020年4月23日 上午9:47:15 
	 * @version 1.0.0.1
	 */
	public String getWeekOfDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String week = "";
		try {
			Date date = sdf.parse(time);
			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0) {
				w = 0;
			}
			week = weekDays[w];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return week;
	}

	/**
	 * @description: 根据当前星期几，判断这周剩余天数(不包括当天)
	 *
	 * @param week  "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"
	 * @author Yangcl
	 * @date 2020年4月23日 上午9:51:31 
	 * @version 1.0.0.1
	 */
	public int getSurplusDays(String week) {
		int surplusDays = 0;  // surplusDays 
		if ("星期日".equals(week)) {
			surplusDays = 0;
		} else if ("星期一".equals(week)) {
			surplusDays = 6;
		} else if ("星期二".equals(week)) {
			surplusDays = 5;
		} else if ("星期三".equals(week)) {
			surplusDays = 4;
		} else if ("星期四".equals(week)) {
			surplusDays = 3;
		} else if ("星期五".equals(week)) {
			surplusDays = 2;
		} else if ("星期六".equals(week)) {
			surplusDays = 1;
		}
		return surplusDays;
	}
	
	/**
	 * @description: 获取本月周区间
	 *
	 * @param date   yyyy-mm 日期
	 * @param days   天数
	 * @param weeks 天数
	 * 
	 * @return 
	 * 	{
	 *			1 = { beginDate = 2020 - 04 - 01, endDate = 2020 - 04 - 05 }, 
	 *			2 = { beginDate = 2020 - 04 - 06, endDate = 2020 - 04 - 12 }, 
	 *			3 = { beginDate = 2020 - 04 - 13, endDate = 2020 - 04 - 19 }, 
	 *			4 = { beginDate = 2020 - 04 - 20, endDate = 2020 - 04 - 26 }, 
	 *			5 = { beginDate = 2020 - 04 - 27, endDate = 2020 - 04 - 30 }
	 *		}
	 * 
	 * @author Yangcl
	 * @date 2020年4月23日 上午10:01:07 
	 * @version 1.0.0.1
	 */
	public Map<Integer, Map<String, String>> getScope(String date, int days, int weeks) {
		Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();   // key为周数，数字1到5之间；value为当前第几周所对应的开始日期和结束日期
		int midNum = 0; 	// 记录本周周日那天的数字
		for (int i = 1; i <= weeks; i++) {	// 遍历周数
			// 第一周
			if(i == 1) {
				String time = date + "-01";
				String week = this.getWeekOfDate(time);
				// 获取第一周还有多少天
				int firstDays = this.getSurplusDays(week);
				// 获取第一周结束日期
				int endDays = 1 + firstDays;
				String time2 = date + "-0" + endDays;
				Map<String, String> data = new HashMap<String, String>();
				data.put("beginDate", time);
				data.put("endDate", time2);
				map.put(i , data);
				midNum = endDays;
			}else {
				if ( (midNum + 7) <= days) {  // 当前日期数+7 比较 当月天数
					int beginNum = midNum + 1;
					int endNum = midNum + 7;
					String time1 = date + "-" + this.getNum(beginNum);
					String time2 = date + "-" + this.getNum(endNum);
					Map<String, String> data = new HashMap<String, String>();
					data.put("beginDate", time1);
					data.put("endDate", time2);
					map.put(i, data);
					midNum = endNum;
				} else {
					int beginNum = midNum + 1;
					int endNum = days;
					String time1 = date + "-" + this.getNum(beginNum);
					String time2 = date + "-" + this.getNum(endNum);
					Map<String, String> data = new HashMap<String, String>();
					data.put("beginDate", time1);
					data.put("endDate", time2);
					map.put(i , data);
					midNum = endNum;
				}
			}
		}
		
		return map;
	}
	
	/**
	 * @description: 输入一个月份，返回其对应的字符串
	 *
	 * @param month
	 * @author Yangcl
	 * @date 2020年4月23日 上午10:04:36 
	 * @version 1.0.0.1
	 */
	public String getMonthToStr(int month) {
		String str = "";
		switch (month) {
			case 1:
				str = "01";
				break;
			case 2:
				str = "02";
				break;
			case 3:
				str = "03";
				break;
			case 4:
				str = "04";
				break;
			case 5:
				str = "05";
				break;
			case 6:
				str = "06";
				break;
			case 7:
				str = "07";
				break;
			case 8:
				str = "08";
				break;
			case 9:
				str = "09";
				break;
			case 10:
				str = "10";
				break;
			case 11:
				str = "11";
				break;
			case 12:
				str = "12";
				break;
		}
		return str;
	}
	
	private String getNum(int num) {
		int result = num / 10;
		if (result == 0) {
			return "0" + num;
		} else {
			return num + "";
		}
	}

//	public static void main(String[] args) {
//		Map<String, String> map = new DeluxeDateUtils().getScopeForweeks(2020, 4, 5);
//		System.out.println("返回第几周的开始时间与结束时间：" + map.toString());
//	}

}
