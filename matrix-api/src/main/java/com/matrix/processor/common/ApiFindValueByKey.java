package com.matrix.processor.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.service.IApiCenterService;

/**
 * @description: 根据请求者的key，找到对应的value
 * @tag API-COMMON-FIND-VALUE-BY-KEY
 *
 * @author Yangcl
 * @date 2018年10月8日 上午10:34:58 
 * @version 1.0.0.1
 */
public class ApiFindValueByKey extends BaseClass implements IBaseProcessor {

	@Inject
	private IApiCenterService apiCenterService;  
	
	
	@Override
	public Result<String> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject data) {
		return apiCenterService.ajaxFindRequestValue(data.getString("key"));
	}

}
