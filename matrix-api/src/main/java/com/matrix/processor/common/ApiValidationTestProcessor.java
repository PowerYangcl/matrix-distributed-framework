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
 * @description: validate 测试
 * @tag API-COMMON-FIND-VALUE-BY-KEY
 *
 * @author Yangcl
 * @date 2018年10月8日 上午10:34:58 
 * @version 1.0.0.1
 */
public class ApiValidationTestProcessor extends BaseClass implements IBaseProcessor {

	@Inject
	private IApiCenterService apiCenterService; 
	
	@Override
	public Result<String> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session,  JSONObject param) {
		ValidationTest dto = JSONObject.parseObject(param.getString("data"), ValidationTest.class);
		this.getLogger(null).sysoutInfo("validate 测试", this.getClass());
		return apiCenterService.ajaxValidationTest(dto);
	}
}
