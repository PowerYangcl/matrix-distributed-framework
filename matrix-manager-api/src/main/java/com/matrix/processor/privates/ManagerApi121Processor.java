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
 * @description: 删除一条角色信息
 *  需要判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除；
 *  如果想删除则必选先将用户与该角色解除绑定，即：删除mc_user_role表中的关联记录
 * @tag MANAGER-API-121
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * 
 * request 实体   McRoleDto
 * {
	 "mcRoleId": "62"
   }
   response返回类型
  {"status":"success"}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi121Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McRoleDto dto = JSONObject.parseObject(param.getString("data"), McRoleDto.class);
		return mcSysFunctionService.deleteMcRole(dto);
	}

}






























