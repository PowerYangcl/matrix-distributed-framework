package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.request.FindMcOrganizationRequest;
import com.matrix.pojo.view.UserOrgTreeListView;
import com.matrix.service.IMcOrganizationService;

/**
 * @description: 公司组织架构书列表数据
 * @tag MANAGER-API-124
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindMcOrganizationRequest.class)
public class ManagerApi124Processor extends BaseClass implements IBaseProcessor {
	
	@Inject
	private IMcOrganizationService mcOrganizationService;
	
	@Override
	public Result<UserOrgTreeListView> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		FindMcOrganizationRequest dto = JSONObject.parseObject(param.getString("data"), FindMcOrganizationRequest.class);
		return mcOrganizationService.ajaxTreeList(dto);
	}

}






























