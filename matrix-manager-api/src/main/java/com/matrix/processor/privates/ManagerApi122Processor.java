package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
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
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		AddMcUserRoleRequest dto = param.getData().toJavaObject(AddMcUserRoleRequest.class);
		dto.setUserCache(param.getUserCache());
		return mcRoleService.allotUserRole(dto);
	}

}






























