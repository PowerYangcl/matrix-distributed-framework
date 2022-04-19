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
import com.matrix.pojo.request.UpdateUserAddOrgRequest;
import com.matrix.service.IMcOrganizationService;

/**
 * @description: 用户列表书库权限
 * @tag MANAGER-API-134
 *
 * @author Yangcl
 * @date 2018年11月28日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.UpdateUserAddOrgRequest.class)
public class ManagerApi134Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcOrganizationService mcOrganizationService;
	
	@Override
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response,  HttpSession session) {
		UpdateUserAddOrgRequest dto = param.getData().toJavaObject(UpdateUserAddOrgRequest.class);
		dto.setUserCache(param.getUserCache());
		return mcOrganizationService.ajaxUserAddOrgRequest(dto);
	}

}
