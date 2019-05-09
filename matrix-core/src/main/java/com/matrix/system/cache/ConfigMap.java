package com.matrix.system.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.matrix.base.BaseEhcache;
import com.matrix.base.BaseLog;

 
/**
 * @descriptions 获取initclass[]中要实例化的类|此缓存只有一个Key：matrix-core.initclass
 *		此处将读取config.*****.properties配置文件中的@add$matrix-core.initclass[*******(注：此处的值唯一)]对应的值
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月12日 下午6:43:22
 * @version 1.0.1
 */
public class ConfigMap extends BaseEhcache<String, Map<String , String>> {

	private ConfigMap(){
		super("ConfigMap");
	}
	private static class LazyHolder{
		private static final ConfigMap INSTANCE = new ConfigMap();
	}
	public static final ConfigMap getInstance() {
		return LazyHolder.INSTANCE; 
	}

	public synchronized void refresh() {
		PropConfig config = PropConfig.getInstance();
		if (config.getKeys().size() == 0){
			config.refresh();
		}
		BaseLog.getInstance().sysoutInfo("System beginning organiza the class" , this.getClass());
		for (String s : config.getKeys()) {
			if (StringUtils.indexOf(s, "[") > -1) {
				String key = StringUtils.substringBefore(s, "[");
				if (!this.containsKey(key)) {
					this.addElement(key, new HashMap<String , String>());
				}
				String subkey = StringUtils.substringBetween(s, "[", "]");
				this.getValue(key).put(subkey, config.getValue(s));  
			}
		}
	}

	@Override
	public Map<String , String> find(String k) {
		return null;
	}
}



















