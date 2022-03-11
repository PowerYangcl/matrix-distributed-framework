package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.request.FindUserRoleListRequest;
import com.matrix.pojo.view.McRoleView;
import com.matrix.service.IMcRoleService;

/**
 * @description: 展示权限列表|如果用户已经有权限了则标识出来
 * @tag MANAGER-API-111
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindUserRoleListRequest.class)
public class ManagerApi111Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public Result<PageInfo<McRoleView>> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		FindUserRoleListRequest dto = JSONObject.parseObject(param.getString("data"), FindUserRoleListRequest.class);
		return mcRoleService.userRoleList(dto , request);
	}

}







































