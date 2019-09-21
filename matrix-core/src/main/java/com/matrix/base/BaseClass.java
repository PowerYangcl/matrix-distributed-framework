package com.matrix.base;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.matrix.annotation.Inject;
import com.matrix.helper.FormatHelper;
import com.matrix.system.SpringCtxUtil;
import com.matrix.system.cache.PropVisitor;

/**
 * @description: 顶层基类，提供多样化的注解功能、配置文件访问等等 
 * 
 * @author 张海涛
 * @date 2016年9月29日 下午2:40:02 
 * @version 1.0.0.1
 */
public class BaseClass {
	private static Logger logger = Logger.getLogger(BaseClass.class);

	public BaseClass() {
		inject(this.getClass());
	}

	public void inject(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Inject.class)) {
				Inject inject = field.getAnnotation(Inject.class);
				String className = inject.className();
				try {
					if (StringUtils.isNotBlank(className)) {
						Object obj = this.getBean(className);
						field.setAccessible(true);
						field.set(this, obj);
					} else {
						Object obj = this.getBean(field.getType());
						field.setAccessible(true);
						field.set(this, obj);
					}
				} catch (NoSuchBeanDefinitionException e) {
					e.printStackTrace();
					getLogger(logger).logError(e.getMessage());
				} catch(IllegalAccessException e) {
					e.printStackTrace();
					getLogger(logger).logError(e.getMessage()); 
				}
			}
		}
		Class<?> parentClazz = clazz.getSuperclass();
		if(parentClazz != null)
			inject(parentClazz);
	}
	
	public <T> T getBean(Class<T> clazz) throws BeansException {
		return (T) SpringCtxUtil.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) throws BeansException {
		return (T) SpringCtxUtil.getBean(beanName);
	}
	
	/**
	 * @description: 为子类提供日志访问 
	 * 
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:37:29 
	 * @version 1.0.0.1
	 */ 
	public BaseLog getLogger(Logger logger) {
		return BaseLog.getInstance().setLogger(logger); 
	}
	
	/**
	 * @description: 通过访问每一个项目的 config.*****.properties文件，获取其配置内容
	 * 
	 * @param key 配置主键
	 * @return 配置内容字符串
	 * 
	 * @author Yangcl 
	 * @date 2016年9月29日 下午2:42:48 
	 * @version 1.0.0.1
	 */
	public String getConfig(String key) {
		return PropVisitor.getConfig(key);                 
	}

	/**
	 * @description: 通过访问每一个项目的 info.****.****.properties文件，获取其消息提示内容
	 * 
	 * @param infoCode 文本编号
	 * @param parms  拼接字符串 此字段可以为空
	 * 
	 * @author Yangcl 
	 * @date 2016年9月29日 下午2:42:48 
	 * @version 1.0.0.1
	 */
	public String getInfo(long infoCode, Object... parms) {
		return FormatHelper.formatString(PropVisitor.getInfo(infoCode), parms);
	}
	
	/**
	 * @description: 时间操作|返回字符串
	 *		String dateStr = super.getDate(new Date(), -1 , "yyyy-MM-dd-HH");  // 获取上一小时时间
	 *
	 * @param date
	 * @param flag 减一 或 加一
	 * @param format  如：yyyy-MM-dd-HH
	 * @param type Calendar.HOUR | Calendar.DATE | Calendar.MONTH
	 * @author Yangcl
	 * @date 2019年9月9日 下午5:58:22 
	 * @version 1.0.0.1
	 */
	public String getDate(Date date , int flag , String format , int type){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 
		 calendar.add(type , flag); 
		 date=calendar.getTime();  
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 return formatter.format(date);
	}
	
	/**
	 * @description: 时间操作|返回java.util.Date
	 *		String dateStr = super.getDate(new Date(), -1 , "yyyy-MM-dd-HH");  // 获取上一小时时间
	 *
	 * @param date
	 * @param flag 减一 或 加一
	 * @param type Calendar.HOUR | Calendar.DATE | Calendar.MONTH
	 * @author Yangcl
	 * @date 2019年9月9日 下午5:58:22 
	 * @version 1.0.0.1
	 */
	public Date getDate(Date date , int flag ,  int type){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(type , flag); 
		 return date=calendar.getTime();
	}
	
	/**
	 * @descriptions 比较两个时间的大小 如果两个时间相等则返回false
	 * @tips 如果两个时间相等则a.compareTo(b) = 0
	 * 
	 * 	String a = "2016-09-18 15:00:00";
	 * 	String b = "2016-09-18 16:00:00";
	 * 	System.out.println(compareDate(a, b)); 	// true
	 * 
	 * @param a not null
	 * @param b not null 
	 * @return boolean 
	 * 
	 * @author Yangcl
	 * @date 2016-5-5-下午2:52:13
	 * @version 1.0.0.1
	 */
	public boolean compareDate(String a , String b){
		if(StringUtils.isAnyBlank(a , b)){
			return false;
		}
		return a.compareTo(b) < 0;
	}
	
	/**
	 * @描述: 比较两个时间的大小
	 * @作者: Yangcl
	 * @时间: 2015-11-09
	 * @param null
	 */
	public boolean compareDate(Date date1 , Date date2) {
	   return date1.getTime() > date2.getTime();
	}
	
	/**
	 * @description: 两天之间的时间差 
	 *
	 * @param date1 起始日期
	 * @param date2 结束日期
	 * @param flag 是否包含结束的那天
	 * 
	 * @author Yangcl
	 * @date 2019年9月12日 上午10:43:08 
	 * @version 1.0.0.1
	 */
	public int differenceDays(Date date1 , Date date2 , boolean flag){
		if(!compareDate(date1, date2)) {
			return 0;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		try {
			int days = (int) ((sdf.parse(sdf.format(date2)).getTime() - sdf.parse(sdf.format(date1)).getTime()) / (1000*3600*24));
			if(flag) {
				days = days +1;
			}
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
    }
}



































