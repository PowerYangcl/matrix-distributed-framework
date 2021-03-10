package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.service.IMcRoleService;
import com.matrix.service.IMcSysFunctionService;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 验证用户登录信息|客户端用户：nodejs/IOS平板等
 * @tag MANAGER-API-100
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi100Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserInfoDto dto = JSONObject.parseObject(param.getString("data"), McUserInfoDto.class);
		return mcUserInfoService.ajaxClientLogin(request , dto);
	}

}























