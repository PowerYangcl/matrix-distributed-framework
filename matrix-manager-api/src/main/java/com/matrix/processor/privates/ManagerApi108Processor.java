package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 用户自己选择后台页面样式风格
 * @tag MANAGER-API-108
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi108Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserInfoDto dto = JSONObject.parseObject(param.getString("data"), McUserInfoDto.class);
		return mcUserInfoService.updatePageStyle(dto);
	}

}
