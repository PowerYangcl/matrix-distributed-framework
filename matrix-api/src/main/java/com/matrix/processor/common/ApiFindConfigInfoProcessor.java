package com.matrix.processor.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;

/**
 * @description: 获取API接口项目中的Config配置信息|如果部署多个API接口服务器，
 * 		则可能随机获取其中的一个，但会返回一个标识的IP地址作为区分依据
 *			{"key":"matrix-api.default_leader_service_list_master"}
 * @author Yangcl
 * @date 2018年11月26日 下午7:12:44 
 * @version 1.0.0.1
 */
public class ApiFindConfigInfoProcessor extends BaseClass implements IBaseProcessor {

	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		JSONObject result = new JSONObject();
		param = param.getJSONObject("data");
		String value = this.getConfig(param.getString("key"));
		if(StringUtils.isBlank(value)) {
			value = "未发现对应的值";
		}
		result.put("value", value);
		result.put("status", "success");
		return result;
	}

}
