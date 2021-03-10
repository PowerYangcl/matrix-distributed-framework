package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.service.IMcRoleService;
import com.matrix.service.IMcSysFunctionService;
import com.matrix.service.IMcUserInfoService;

/**
 * @description:修改角色功能|【角色列表】->【角色功能】->【提交按钮】
 * @tag MANAGER-API-120
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * 
 * request 实体   McRoleDto
 * {
	 "ids": "22,33,33",
	 "mcRoleId": "62"
   }
   response返回类型
   {
	"cache": {
		"mcRoleId": 62,
		"cid": null,
		"type": null,
		"platform": null,
		"roleName": "会员管理-隆庆祥",
		"roleDesc": "会员管理-隆庆祥",
		"ids": "341,342,343,369,382,384,385,386,387,344,370,345,371,397,398,399,402,405,403,406,404,407,408"
	},
	"status": "success"
  }
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi120Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McRoleDto dto = JSONObject.parseObject(param.getString("data"), McRoleDto.class);
		return mcSysFunctionService.editMcRole(dto);
	}

}






























