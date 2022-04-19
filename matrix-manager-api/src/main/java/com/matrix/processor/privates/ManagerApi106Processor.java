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
import com.matrix.pojo.request.UpdateMcUserInfoRequest;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 修改用户信息
 * @tag MANAGER-API-106
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.UpdateMcUserInfoRequest.class)
public class ManagerApi106Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	
	@Override
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		UpdateMcUserInfoRequest dto = param.getData().toJavaObject(UpdateMcUserInfoRequest.class);
		dto.setUserCache(param.getUserCache());
		return mcUserInfoService.editSysUser(dto);
	}

}
