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
import com.matrix.pojo.request.FindMcUserInfoRequest;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 获取用户详情
 * @tag MANAGER-API-105
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindMcUserInfoRequest.class)
public class ManagerApi105Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	
	@Override
	public Result<McUserInfoView> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		FindMcUserInfoRequest dto = JSONObject.parseObject(param.getString("data"), FindMcUserInfoRequest.class);
		return mcUserInfoService.ajaxFindSysUser(dto);
	}

}
