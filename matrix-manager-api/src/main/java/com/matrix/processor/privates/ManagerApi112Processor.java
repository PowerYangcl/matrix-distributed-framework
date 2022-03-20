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
import com.matrix.pojo.request.AddMcRoleRequest;
import com.matrix.service.IMcRoleService;

/**
 * @description: 添加一个角色，不勾选系统功能
 * @tag MANAGER-API-112
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.AddMcRoleRequest.class)
public class ManagerApi112Processor extends BaseClass implements IBaseProcessor<AddMcRoleRequest> {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, BaseApiDto<AddMcRoleRequest> param) {
		return mcRoleService.addMcRole(param.getData());
	}

}





































