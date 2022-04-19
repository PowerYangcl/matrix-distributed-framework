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
import com.matrix.pojo.request.DeleteMcOrganizationRequest;
import com.matrix.service.IMcOrganizationService;

/**
 * @description: 删除多个组织机构节点
 * @tag MANAGER-API-127
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.DeleteMcOrganizationRequest.class)
public class ManagerApi127Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcOrganizationService mcOrganizationService;
	
	@Override
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		DeleteMcOrganizationRequest dto = param.getData().toJavaObject(DeleteMcOrganizationRequest.class);
		return mcOrganizationService.deleteOrganizationInfo(dto);
	}

}






























