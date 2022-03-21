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
import com.matrix.pojo.request.FindLoginRequest;
import com.matrix.pojo.view.ClientLoginView;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 验证用户登录信息|客户端用户：nodejs/IOS平板等
 * @tag MANAGER-API-100
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindLoginRequest.class)
public class ManagerApi100Processor extends BaseClass implements IBaseProcessor<FindLoginRequest> {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	@Override
	public Result<ClientLoginView> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, BaseApiDto<FindLoginRequest> param) {
		return mcUserInfoService.ajaxClientLogin(param.getData(), request);
	}

}























