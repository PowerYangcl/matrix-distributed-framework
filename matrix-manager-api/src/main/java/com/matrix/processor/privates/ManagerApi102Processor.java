package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageInfo;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.request.FindMcUserInfoListRequest;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 用户列表页数据展示
 * @tag MANAGER-API-102
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindMcUserInfoListRequest.class)
public class ManagerApi102Processor extends BaseClass implements IBaseProcessor {

	
	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	@Override
	public Result<PageInfo<McUserInfoView>> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		FindMcUserInfoListRequest dto = param.getData().toJavaObject(FindMcUserInfoListRequest.class);
		dto.setUserCache(param.getUserCache());
		return mcUserInfoService.ajaxSystemUserList(dto , request);
	}

}



















