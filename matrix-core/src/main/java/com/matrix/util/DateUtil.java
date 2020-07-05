package com.matrix.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @description: 基本时间操作工具类
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年12月24日 上午10:17:26 
 * @version 1.0.0.1
 */
public class DateUtil {

	/**
	 * @description: 两个日期的时间差计算
	 *			int num = du.timeInterval("2020-06-02 00:00:00", "2020-06-03 00:00:00", 1);
	 *			System.out.println(num); 输出1
	 *
	 * @param startTime 需要精确到时分秒
	 * @param endTime 需要精确到时分秒
	 * @param type	1：天数差  2：小时差  3：分钟差
	 * @author Yangcl
	 * @date 2020年5月21日 上午11:52:31 
	 * @version 1.0.0.1
	 */
	public Integer timeInterval(String startTime , String endTime , int type) {
		if(!this.compareDate(startTime, endTime)) {
			return 0;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long start = format.parse(startTime).getTime();
			long end = format.parse(endTime).getTime();
			if(type == 1) {	// 天数差
				return (int) ((end - start) / (1000 * 60 * 60 * 24));
			}
			if(type == 2) {	// 小时差
				return (int) ((end - start) / (1000 * 60 * 60));
			}
			if(type == 3) {	// 分钟差
				return (int) ((end - start) / (1000 * 60));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @description: 指定一个日期，获取其属于当前月的第几周，以及这一周的开始和结束时间
	 *
	 * @param date
	 * @author Yangcl
	 * @date 2020年4月28日 下午3:07:49 
	 * @version 1.0.0.1
	 */
	public Map<String, String> getWeekMonToSun(Date date){
		if(date == null) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf.format(date);
		String month = day.substring(0 , 7);
		Map<Integer, JSONObject> monthWeeks = this.getMonthWeeks(month);
		Map<String , String > map  = new HashMap<String, String>();
		monthWeeks.forEach((key , value) -> {
			if(this.compareDate(value.getString("startTime"), day) && this.compareDate(day , value.getString("endTime"))) {
				map.put("startTime", value.getString("startTime"));
				map.put("endTime" , value.getString("endTime"));
			}
		});
		return map;
	}
	
	/**
	 * @description: 比较两个时间大小
	 * 	String a = "2016-09-18 15:00:00";   String b = "2016-09-18 16:00:00"; 
	 * 	System.out.println(compareDate(a, b)); // true
	 *
	 * @param a
	 * @param b
	 * @author Yangcl
	 * @date 2020年4月28日 下午3:17:23 
	 * @version 1.0.0.1
	 */
	public boolean compareDate(String a , String b){
		if(StringUtils.isAnyBlank(a , b)){
			return false;
		}
		return a.compareTo(b) < 0;
	}
	
	/**
	 * @description: String 转化Date
	 *
	 * @param str 2020-04-28
	 * @param format_ 	24小时制：yyyy-MM-dd HH:mm:ss  12小时制：yyyy-MM-dd hh:mm:ss
	 * @author Yangcl
	 * @date 2020年4月28日 下午3:45:28 
	 * @version 1.0.0.1
	 */
	public Date stringToDate(String str , String format_) {
		SimpleDateFormat sdf = new SimpleDateFormat(format_);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public String dateToString(Date date , String format_) {
		SimpleDateFormat sdf = new SimpleDateFormat(format_);
		String str = sdf.format(date);
		return str;
	}
	
	/**
	 * @description: 获取第N天的时间
	 *
	 * @param date 日期 Date
	 * @param num	把日期往后增加N天； .整数往后推 , 负数往前移动。
	 * @param format yyyy-MM-dd 
	 * 
	 * @author Yangcl
	 * @date 2020年4月30日 下午5:14:06 
	 * @version 1.0.0.1
	 */
	public String getDate(Date date , int num , String format){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE , num);   // 
		 date=calendar.getTime();      // 这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 
		 return formatter.format(date);
	}
	
	/**
	 * @description: 获取第N天的时间
	 *
	 * @param date 日期  String
	 * @param num	把日期往后增加N天； .整数往后推 , 负数往前移动。
	 * @param format yyyy-MM-dd 
	 * 
	 * @author Yangcl
	 * @date 2020年4月30日 下午5:14:06 
	 * @version 1.0.0.1
	 */
	public String getDateByString(String date , int num , String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		 Calendar calendar = new GregorianCalendar();
		 try {
			calendar.setTime(formatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 calendar.add(calendar.DATE , num); 
		 return formatter.format(calendar.getTime());
	}
	
	/**
	 * @description: 获取当前天的开始时间和结束时间
	 *
	 * @author Yangcl
	 * @date 2020年4月27日 下午3:59:08 
	 * @version 1.0.0.1
	 */
	public Map<String, String> getCurrDayStarttimeAndEndtime(){
		Map<String, String> map = new HashMap<String, String>(2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String startTime = sdf.format(new Date());
		sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String endTime = sdf.format(new Date());;
		map.put("startTime" , startTime);
		map.put("endTime" , endTime);
		return map;
	}
	
	/**
	 * @description: 格式化当前服务器时间为字符串
	 *
	 * @param pattern	24小时制：yyyy-MM-dd HH:mm:ss | 12小时制：yyyy-MM-dd hh:mm:ss
	 * @author Yangcl
	 * @date 2020年4月26日 下午7:02:12 
	 * @version 1.0.0.1
	 */
	public String formatDate(String pattern) {
		return this.formatDate(pattern , new Date());
	}
	
	/**
	 * @description: 格式化一个指定的时间为字符串
	 *
	 * @param pattern	24小时制：yyyy-MM-dd HH:mm:ss | 12小时制：yyyy-MM-dd hh:mm:ss
	 * @param date
	 * @author Yangcl
	 * @date 2020年4月26日 下午7:04:06 
	 * @version 1.0.0.1
	 */
	public String formatDate(String pattern ,  Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * @descriptions 获取16进制表示的当前时间
	 *
	 * @date 2017年8月2日 下午3:32:12
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public String getDateHex() {
		return Integer.toHexString(Integer.valueOf(this.formatDate("yyMMdd")));
	}
	
	public String getDateLongHex(String format) {
		return Long.toHexString(Integer.valueOf(this.formatDate(format)));
	}
	
	/**
	 * @description: 返回第几周的开始与结束日期
	 *			Map<String, String> map = new DeluxeDateUtils().getScopeForweeks(2020, 4, 5);
	 *
	 * @param year
	 * @param month
	 * @param weeks
	 * @author Yangcl
	 * @date 2020年4月23日 上午9:59:27 
	 * @version 1.0.0.1
	 */
	public Map<String, String> getScopeForWeeks(int year, int month, int weeks) {
		Map<String, String> map = new HashMap<String, String>();
		String time = year + "-" + getMonthToStr(month);
		Map<String, Integer> result = this.getDateScope(time);  // 获取天数和周数
		int totalDays = result.get("days");
		int totalWeeks = result.get("weeks");
		
		if (weeks > totalWeeks) {	// 如果参数周数大于实际周数 则返回一个不存在的日期 默认设置为当前 天数+1
			int days = totalDays + 1;
			String beginDate = year + "-" + getMonthToStr(month) + "-" + days;
			map.put("beginDate", beginDate);
			map.put("endDate", beginDate);
		} else {	// 获取当月第一天属于周几
			map = this.getScope(time, totalDays, totalWeeks).get(weeks);
		}
		return map;
	}

	/**
	 * @description: 获取当前月份下，每一周的开始日期和结束日期
 			 {
			    1={
			        "startTime":"2020-04-01",  04-01：星期三
			        "endTime":"2020-04-05"
			    },
			    2={
			        "startTime":"2020-04-06",
			        "endTime":"2020-04-12"
			    },
			    3={
			        "startTime":"2020-04-13",
			        "endTime":"2020-04-19"
			    },
			    4={
			        "startTime":"2020-04-20",
			        "endTime":"2020-04-26"
			    },
			    5={
			        "startTime":"2020-04-27",
			        "endTime":"2020-04-30"  04-30：星期四
			    }
			}
	 * @param date 2020-04
	 * @author Yangcl
	 * @date 2020年4月24日 下午3:56:35 
	 * @version 1.0.0.1
	 */
	public Map<Integer , JSONObject> getMonthWeeks(String date){
		if(StringUtils.isBlank(date)) {
			return null;
		}
		Map<String, Integer> result = this.getDateScope(date);		// 获取天数以及周数
		Map<Integer, Map<String, String>> scope = this.getScope(date, result.get("days"), result.get("weeks"));
		
		Map<Integer , JSONObject> info = new TreeMap<Integer, JSONObject>();
		scope.forEach((key , value) -> {
			JSONObject o = new JSONObject();
			o.put("startTime", value.get("beginDate"));
			o.put("endTime", value.get("endDate"));
			info.put(key, o) ;
		}); 
		return info;
	}
	
	/**
	 * @description: 获取当前月份下，每一周的开始日期和结束日期（跨月）。如果第一周的开始日期不是星期一，则此周不计入返回结果
	 * {
	 * 	1 = { "startTime": "2020-04-06", "endTime": "2020-04-12" }, 
	 * 	2 = { "startTime": "2020-04-13", "endTime": "2020-04-19" }, 
     * 	3 = { "startTime": "2020-04-20", "endTime": "2020-04-26" }, 
     * 	4 = { "startTime": "2020-04-27", "endTime": "2020-05-03" }         2020-05-03是跨月的信息
     * }
	 *
	 * @param date 2020-04
	 * @author Yangcl
	 * @date 2020年4月24日 下午3:56:35 
	 * @version 1.0.0.1
	 */
	public Map<Integer , JSONObject> getMonthWeeksBeginMonday(String date){
		if(StringUtils.isBlank(date)) {
			return null;
		}
		Map<String, Integer> result = this.getDateScope(date);		// 获取天数以及周数
		Map<Integer, Map<String, String>> scope = this.getScope(date, result.get("days"), result.get("weeks"));
		Map<Integer , JSONObject> info = new TreeMap<Integer, JSONObject>();
		int i = 1;
		for(Integer key : scope.keySet()){
			Map<String, String> value = scope.get(key);
			if(!this.getWeekOfDate(value.get("beginDate")).equals("星期一")) {
				continue;
			}
			JSONObject o = new JSONObject();
			o.put("startTime", value.get("beginDate"));
			o.put("endTime", value.get("endDate"));
			if( !this.getWeekOfDate(value.get("endDate")).equals("星期日") ) {
				String endDate = this.getDateByString(value.get("beginDate") , 6, "yyyy-MM-dd");
				o.put("endTime", endDate);
			}
			info.put( i , o) ;
			i ++;
		}
		return info;
	}
	
	/**
	 * @description: 获取当前月份下，每一周的开始日期和结束日期（跨月）。如果第一周的开始日期不是星期一，依然计入返回结果
	 * {
	 * 	1 = { "startTime": "2020-03-30", "endTime": "2020-04-05" },		 2020-03-30是跨月的信息
	 * 	2 = { "startTime": "2020-04-06", "endTime": "2020-04-12" }, 
	 * 	3 = { "startTime": "2020-04-13", "endTime": "2020-04-19" }, 
     * 	4 = { "startTime": "2020-04-20", "endTime": "2020-04-26" }, 
     * 	5 = { "startTime": "2020-04-27", "endTime": "2020-05-03" }         2020-05-03是跨月的信息
     * }
	 *
	 * @param date 2020-04
	 * @author Yangcl
	 * @date 2020年7月5日 下午8:31:46 
	 * @version 1.0.0.1
	 */
	public Map<Integer , JSONObject> getWeeksInMonth(String date){
		if(StringUtils.isBlank(date)) {
			return null;
		}
		Map<String, Integer> result = this.getDateScope(date);		// 获取天数以及周数
		Map<Integer, Map<String, String>> scope = this.getScope(date, result.get("days"), result.get("weeks"));
		Map<Integer , JSONObject> info = new TreeMap<Integer, JSONObject>();
		int i = 1;
		for(Integer key : scope.keySet()){
			Map<String, String> value = scope.get(key);
			JSONObject o = new JSONObject();
			o.put("startTime", value.get("beginDate"));
			o.put("endTime", value.get("endDate"));
			
			if(!this.getWeekOfDate(value.get("beginDate")).equals("星期一")) {
				// 如果开始日期不是星期一，则取星期日的日期向前推6天，则为跨月的日期，星期一。
				String startDate = this.getDateByString(value.get("endDate") , -6, "yyyy-MM-dd");
				o.put("startTime", startDate);
			}
			if(!this.getWeekOfDate(value.get("endDate")).equals("星期日")) {
				// 如果结束日期不是星期日，则取星期一的日期向后推6天，则为跨月的日期，星期日。
				String endDate = this.getDateByString(value.get("beginDate") , 6, "yyyy-MM-dd");
				o.put("endTime", endDate);
			}
			info.put( i , o) ;
			i ++;
		}
		return info;
	}
	
	/**
	 * @description: 指定一个日期，获取其周一对应的日期和周日对应的日期
	 *
	 * @param date
	 * @return 
	 * @author Yangcl
	 * @date 2020年7月5日 下午9:57:27 
	 * @version 1.0.0.1
	 */
	public Map<String , String> getWeeksStartEnd(String date){
		if(StringUtils.isBlank(date)) {
			return null;
		}
		Map<String , String> map = new HashMap<String, String>(1);
		map.put("startTime", "");
		map.put("endTime", "");
		String month = date.split("-")[0] + "-"+date.split("-")[1];
		Map<Integer, JSONObject> maps = this.getWeeksInMonth(month);
		for(Integer key : maps.keySet()) {
			JSONObject value = maps.get(key);
			String startTime = value.getString("startTime");
			String endTime = value.getString("endTime");
			if(date.equals(startTime) || date.equals(endTime)) {
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				return map;
			}
			if(this.compareDate(startTime, date) && this.compareDate(date, endTime)) {
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				return map;
			}
		}
		
		return map;
	}
	
	/**
	 * @description: 获取 某年某月 的【总天数】以及【总周数】
	 *			返回：{weeks=5, days=31}
	 * @param time 格式：yyyy-MM，2020-07
	 * @author Yangcl
	 * @date 2020年4月23日 上午10:28:45 
	 * @version 1.0.0.1
	 */
	public Map<String, Integer> getDateScope(String time) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			if (time.length() <= 7) {  // 保证日期位数
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(time + "-01");
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);		// 获取天数
				int weeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);		// 获取周数
				if(weeks > 5) {
					weeks = 5;
				}
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
	 * @description: 获取 某年某月 的【总天数】
	 *
	 * @param year 2020
	 * @param month 7
	 * @return 31
	 * @author Yangcl
	 * @date 2020年5月12日 下午3:36:47 
	 * @version 1.0.0.1
	 */
	public Integer daysOfMonth(int year , int month) {
		LocalDate startDay = LocalDate.of(year , month, 1);
		LocalDate endDay = LocalDate.of(year , month, 1);
		endDay = endDay.with(TemporalAdjusters.lastDayOfMonth());
		return (int) (startDay.until(endDay, ChronoUnit.DAYS) + 1);
	}
	
	
	/**
	 * @description: 获取日期属于星期几
	 *
	 * @param time 格式：yyyy-MM-dd，2020-07-05
	 * @return 星期日
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
	 *			1={beginDate=2020-04-01,endDate=2020-04-05}, 
	 *			2={beginDate=2020-04-06,endDate=2020-04-12}, 
	 *			3={beginDate=2020-04-13,endDate=2020-04-19}, 
	 *			4={beginDate=2020-04-20,endDate=2020-04-26},
	 *			5={beginDate=2020-04-27,endDate=2020-04-30}
	 *		}
	 * 
	 * @author Yangcl
	 * @date 2020年4月23日 上午10:01:07 
	 * @version 1.0.0.1
	 */
	public Map<Integer, Map<String, String>> getScope(String date, int days, int weeks) {
		Map<Integer, Map<String, String>> map = new TreeMap<Integer, Map<String, String>>();   // key为周数，数字1到5之间；value为当前第几周所对应的开始日期和结束日期
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
	
	/**
	 * @description: 获取指定年份内的所有月份开始 / 结束时间信息
	 *
	 * @param year：2020
	 * @param format_：yyyy-MM-dd HH:mm:ss
	 * @author Yangcl
	 * @date 2020年4月24日 下午2:56:51 
	 * @version 1.0.0.1
	 */
	public Map<Integer , JSONObject> monthInfo(String year , String format_) {
		Map<Integer , JSONObject> info = new TreeMap<Integer, JSONObject>();
		SimpleDateFormat sdf = new SimpleDateFormat(format_);

		for(int i = 1; i <= 12 ; i ++) {
			Date startTime = getBeginTime(Integer.valueOf(year) , i);	
			Date endTime = getEndTime(Integer.valueOf(year) , i);
			JSONObject o = new JSONObject();
			o.put("startTime", sdf.format(startTime));
			o.put("endTime", sdf.format(endTime));
			info.put(i, o);
		}
        return info;
	}
	
	/**
	 * @description: 获取指定年月的开始日期
	 *
	 * @param year
	 * @param month
	 * @author Yangcl
	 * @date 2020年4月24日 下午2:49:55 
	 * @version 1.0.0.1
	 */
	public Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }
 
	/**
	 * @description: 获取指定年月的结束日期
	 *
	 * @param year
	 * @param month
	 * @author Yangcl
	 * @date 2020年4月24日 下午2:49:55 
	 * @version 1.0.0.1
	 */
    public Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }
    
    // return 2020-07-01 00:00:00
    public String getBeginTimeString(int year, int month , String format_) {
		return this.dateToString(this.getBeginTime(year, month), format_);
	}
    
    // return 2020-07-31 23:59:59    
    public String getEndTimeString(int year, int month , String format_) {
		return this.dateToString(this.getEndTime(year, month), format_);
	}
    
	/**
	 * @description: 获取时间段之内的月份
	 *
	 * @param beginMonth 2019-11
	 * @param endMonth 2020-04
	 * @return [2019-11, 2019-12, 2020-01, 2020-02, 2020-03, 2020-04]
	 * @author Yangcl
	 * @date 2020年4月26日 下午7:08:38 
	 * @version 1.0.0.1
	 */
	public List<String> getMonthList(String beginMonth, String endMonth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		List<String> list = new ArrayList<String>();
		try {
			Date begin = format.parse(beginMonth);
			Date end = format.parse(endMonth);
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(begin);
			endCalendar.setTime(end);
			int months = (endCalendar.get(Calendar.YEAR) - beginCalendar.get(Calendar.YEAR)) * 12 + (endCalendar.get(Calendar.MONTH) - beginCalendar.get(Calendar.MONTH));
			list.add(beginMonth);
			for (int i = 0; i < months; i++) {
				beginCalendar.add(Calendar.MONTH, 1);
				list.add(format.format(beginCalendar.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
    
	/**
	 * @description: 根据年月获取对应的月份工作日天数
	 *
	 * @param year
	 * @param month
	 * @author Yangcl
	 * @date 2020年6月24日 下午11:32:03 
	 * @version 1.0.0.1
	 */
	public int workDayCount(int year , int month) {
		Calendar c  = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		// 当月最后一天日期
		int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 开始日期为1号
		int start = 1;
		// 计数
		int count = 0;
		while(start < max) {
			c.set(Calendar.DAY_OF_MONTH , start);
			if(this.isWorkDay(c)) {
				count ++;
			}
			start ++;
		}
		return count;
	}

	/**
	 * @description: 判断是否为工作日。周六日返回false
	 *
	 * @author Yangcl
	 * @date 2020年6月24日 下午11:30:30 
	 * @version 1.0.0.1
	 */
	public boolean isWorkDay(Calendar c) {
		int week = c.get(Calendar.DAY_OF_WEEK);
		return week != Calendar.SUNDAY && week != Calendar.SATURDAY;
	}
	
	private String getNum(int num) {
		int result = num / 10;
		if (result == 0) {
			return "0" + num;
		} else {
			return num + "";
		}
	}
	
}






















