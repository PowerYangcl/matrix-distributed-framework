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
import com.matrix.pojo.request.AddMcUserRoleRequest;
import com.matrix.service.IMcRoleService;

/**
 * @description: 关联用户与某一个角色
 * @tag MANAGER-API-122
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.AddMcUserRoleRequest.class)
public class ManagerApi122Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		AddMcUserRoleRequest dto = JSONObject.parseObject(param.getString("data"), AddMcUserRoleRequest.class);
		return mcRoleService.allotUserRole(dto);
	}

}






























