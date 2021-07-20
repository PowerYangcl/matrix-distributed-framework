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
import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.request.AddMcOrganizationRequest;
import com.matrix.service.IMcOrganizationService;

/**
 * @description: 添加一个组织结构节点到数据库
 * @tag MANAGER-API-125
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.AddMcOrganizationRequest.class)
public class ManagerApi125Processor extends BaseClass implements IBaseProcessor {
	
	@Inject
	private IMcOrganizationService mcOrganizationService;
	
	@Override
	public Result<McOrganization> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		AddMcOrganizationRequest e = JSONObject.parseObject(param.getString("data"), AddMcOrganizationRequest.class);
		return mcOrganizationService.addOrganizationInfo(e);
	}

}






























