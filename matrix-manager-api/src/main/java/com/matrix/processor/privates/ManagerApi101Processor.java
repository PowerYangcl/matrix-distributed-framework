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
import com.matrix.pojo.request.FindLogoutRequest;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 退出系统登录|客户端用户：nodejs/IOS平板等
 * @tag MANAGER-API-101
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindLogoutRequest.class)
public class ManagerApi101Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		JSONObject head = JSONObject.parseObject(param.getString("head"));
		FindLogoutRequest dto = JSONObject.parseObject(param.getString("data"), FindLogoutRequest.class);
		dto.setAccessToken(head.getString("accessToken"));
		return mcUserInfoService.ajaxClientLogout(dto);
	}

}
