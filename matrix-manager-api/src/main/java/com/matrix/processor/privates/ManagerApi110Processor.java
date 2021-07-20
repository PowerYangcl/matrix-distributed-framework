package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.request.FindMcRoleRequest;
import com.matrix.pojo.view.McRoleView;
import com.matrix.service.IMcRoleService;

/**
 * @description: 系统角色列表数据
 * @tag MANAGER-API-110
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindMcRoleRequest.class)
public class ManagerApi110Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	
	@Override
	public Result<PageInfo<McRoleView>> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		FindMcRoleRequest dto = JSONObject.parseObject(param.getString("data"), FindMcRoleRequest.class);
		return mcRoleService.ajaxSystemRoleList(dto , request);
	}

}



































