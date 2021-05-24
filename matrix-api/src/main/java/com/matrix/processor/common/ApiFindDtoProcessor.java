package com.matrix.processor.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.service.IApiCenterService;


/**
 * @description: 根据API缓存的target返回查询消息体
 * @tag API-COMMON-FIND-DTO
 *
 * @author Yangcl
 * @date 2018年10月8日 上午10:29:09 
 * @version 1.0.0.1
 */
public class ApiFindDtoProcessor  extends BaseClass implements IBaseProcessor {

	@Inject
	private IApiCenterService apiCenterService;  
	
	@Override
	public Result<Object> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject data) {
		return apiCenterService.ajaxFindRequestDto(data.getString("target"));
	}

}
