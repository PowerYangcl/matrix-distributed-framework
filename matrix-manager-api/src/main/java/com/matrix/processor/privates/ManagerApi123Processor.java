package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 解除角色绑定，同时删除缓存
 * @tag MANAGER-API-123
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */

/**
 * 
 * request 实体   McUserRoleDto
 * {
	 "mcRoleId": "62"
	 "userId": "1997"
   }
   response返回类型
  {"status":"success"}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserRoleDto.class)
public class ManagerApi123Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserRoleDto dto = JSONObject.parseObject(param.getString("data"), McUserRoleDto.class);
		return mcSysFunctionService.deleteUserRole(dto);
	}

}






























