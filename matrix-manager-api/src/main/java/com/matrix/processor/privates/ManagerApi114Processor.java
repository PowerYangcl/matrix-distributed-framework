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

/**
 * @description: 修改角色名称和描述，不勾选系统功能
 * @tag MANAGER-API-114
 *
 * request 实体   McRoleDto
   {
	 "id": "62",
	 "roleDesc": "角色描述"，
	 "roleName": "角色名称",
	 "platform":"124as341"     // 平台标识码，用于效验
    }
  * response返回类型
   {
	"msg":"更新成功",
	"status":"success"
   }
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi114Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McRoleDto info = JSONObject.parseObject(param.getString("data"), McRoleDto.class);
		return mcRoleService.editSysRole(info);
	}

}












































