package com.matrix.helper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @description: GsonHelper废弃，使用fastjson替换
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月23日 上午10:46:45 
 * @version 1.0.0
 */
public class JsonHelper {

	public static String toJson(Object oValue) {
		return JSONObject.toJSONString(oValue,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String sJson, T t) {
		return (T) JSONObject.parseObject(sJson, t.getClass());
	}
}