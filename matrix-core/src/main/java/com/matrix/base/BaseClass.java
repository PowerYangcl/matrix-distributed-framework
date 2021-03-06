package com.matrix.base;

import java.lang.reflect.Field;
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
}




