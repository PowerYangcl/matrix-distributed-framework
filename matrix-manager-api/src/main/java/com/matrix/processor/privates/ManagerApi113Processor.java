package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.request.FindMcRoleRequest;
import com.matrix.service.IMcRoleService;

/**
 * @description: 角色详情|唯一参数：info.id mc_role表自增id
 * @tag MANAGER-API-113
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindMcRoleRequest.class)
public class ManagerApi113Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public Result<McRole> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		FindMcRoleRequest info = JSONObject.parseObject(param.getString("data"), FindMcRoleRequest.class);
		return mcRoleService.ajaxFindRoleInfo(info);
	}

}








































