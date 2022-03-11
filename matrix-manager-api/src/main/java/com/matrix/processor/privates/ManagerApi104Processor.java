package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.request.AddMcUserInfoRequest;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 添加用户
 * @tag MANAGER-API-104
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.AddMcUserInfoRequest.class)
public class ManagerApi104Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		AddMcUserInfoRequest dto = JSONObject.parseObject(param.getString("data"), AddMcUserInfoRequest.class);
		return mcUserInfoService.addSysUser(dto); 
	}

}
























