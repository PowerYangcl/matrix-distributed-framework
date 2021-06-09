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
import com.matrix.pojo.request.DeleteMcUserRoleRequest;
import com.matrix.service.IMcRoleService;

/**
 * @description: 解除角色绑定，同时删除缓存
 * @tag MANAGER-API-123
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.DeleteMcUserRoleRequest.class)
public class ManagerApi123Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		DeleteMcUserRoleRequest dto = JSONObject.parseObject(param.getString("data"), DeleteMcUserRoleRequest.class);
		return mcRoleService.deleteUserRole(dto);
	}

}






























