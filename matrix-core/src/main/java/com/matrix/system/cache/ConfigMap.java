package com.matrix.system.cache;

import org.apache.commons.lang.StringUtils;

import com.matrix.cache.RootCache;
import com.matrix.map.MStringMap;

 
/**
 * @descriptions 获取initclass[]中要实例化的类
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月12日 下午6:43:22
 * @version 1.0.1
 */
public class ConfigMap extends RootCache<String, MStringMap> {

	public synchronized void refresh() {
		PropConfig config = PropConfig.Instance;
		if (config.getKeys().size() == 0){
			config.refresh();
		}
		for (String s : config.getKeys()) {
			if (StringUtils.indexOf(s, "[") > -1) {
				String key = StringUtils.substringBefore(s, "[");
				if (!this.containsKey(key)) {
					this.addElement(key, new MStringMap());
				}
				String subkey = StringUtils.substringBetween(s, "[", "]");
				this.getValue(key).put(subkey, config.getValue(s));  
			}
		}
	}

	@Override
	public MStringMap getOneSetCatch(String k) {
		// TODO Auto-generated method stub
		return null;
	}
}



















