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
import com.matrix.pojo.request.DeleteMcRoleRequest;
import com.matrix.service.IMcRoleService;

/**
 * @description: 删除一条角色信息
 *  需要判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除；
 *  如果想删除则必选先将用户与该角色解除绑定，即：删除mc_user_role表中的关联记录
 * @tag MANAGER-API-121
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.DeleteMcRoleRequest.class)
public class ManagerApi121Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;   
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		DeleteMcRoleRequest dto = JSONObject.parseObject(param.getString("data"), DeleteMcRoleRequest.class);
		return mcRoleService.deleteMcRole(dto);
	}

}






























