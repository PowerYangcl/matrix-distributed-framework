package com.matrix.helper;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.util.ExceptionUtils;

/**
 * @description:反射类支持
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年8月29日 下午8:22:09 
 * @version 1.0.0.1
 */
public class ClassHelper extends BaseClass implements IBaseExecute {
		
	@Override
	public String execute(String json) {
		JSONObject result = new JSONObject();
		JSONObject request = JSONObject.parseObject(json);
		if(StringUtils.isAnyBlank(request.getString("name") , request.getString("func"))) {
			result.put("status", "error");
			result.put("msg", "参数为空!");
			result.put("dto", request);
			return result.toJSONString();
		}
		
		try {
			Class<?> clazz = Class.forName(request.getString("name"));   
			if (clazz != null && clazz.getDeclaredMethods() != null) {
				Method m = clazz.getMethod(request.getString("func") , JSONObject.class);
				Object rs = m.invoke(clazz.newInstance() , request.getJSONObject("param"));
				return rs.toString();
			}
		}catch (Exception e) {
			result.put("status" , "error");
			result.put("msg" , "exception in you request");
			result.put("exception" , ExceptionUtils.getExceptionInfo(e));
			return result.toString();
		}  
		
		result.put("status" , "success");
		result.put("msg" , "类名反射异常");
		return result.toString();
	}

}


/**
 	{
		"name": "com.matirx.aaa.Btset.java",
		"func":"func name",
		"param":{}
	}
	
 */


































