package com.matrix.support;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.system.cache.PowerCache;
import com.matrix.util.ExceptionUtils;

/**
 * @description: 一级缓存支持
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年10月12日 下午4:32:18 
 * @version 1.0.0.1
 */
public class PowerSupport extends BaseClass{

	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		try {
			String type = param.getString("type");
			String name = param.getString("name");
			switch (type) {
				case "s":
					String value = (String) PowerCache.getInstance().find(name , param.getString("key"));
					if(StringUtils.isBlank(value)) {
						value = "未找到对应的缓存";
					}
					result.put("data", value );
					break;
				case "i":
					PowerCache.getInstance().compelPut(name , param.getString("key"), param.getString("value"));
					break;
				case "d":
					boolean remove = PowerCache.getInstance().remove(name , param.getString("key"));
					if(remove) {
						result.put("data", "Ehcache has removed" );
					}else {
						result.put("data", "This operation fails, check your key or cache name" );
					}
					break;
				case "u":
					PowerCache.getInstance().reset(name , param.getString("key"), param.getString("value"));
					break;
				default:
					break;
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "Ehcache Exception!");
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
			return result;
		}
		
		result.put("status", "success");
		result.put("msg", "命令执行成功!");
		result.put("dto", param);
		return result;
	}

}
























