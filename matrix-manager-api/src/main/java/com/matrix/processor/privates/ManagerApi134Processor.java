package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.service.IMcOrganizationService;

/**
 * @description: 用户列表书库权限
 * @tag MANAGER-API-134
 *
 * @author mashaohua
 * @date 2018年11月28日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.entity.McOrganization.class)
public class ManagerApi134Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcOrganizationService mcOrganizationService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			JSONObject param) {
		McOrganizationDto dto = JSONObject.parseObject(param.getString("data"), McOrganizationDto.class);
		return mcOrganizationService.ajaxUserAddOrganization(dto);
	}

}
