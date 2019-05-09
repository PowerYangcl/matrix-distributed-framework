package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.entity.McRole;
import com.matrix.service.IMcRoleService;

/**
 * @description: 添加一个角色，不勾选系统功能
 * @tag MANAGER-API-112
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * 
 * request 实体 
 * {
	 "roleDesc": "观察员77",
	 "roleName": "3333377"
   }
   response返回类型
   {"msg":"添加成功","status":"success"}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.entity.McRole.class)
public class ManagerApi112Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McRole info = JSONObject.parseObject(param.getString("data"), McRole.class);
		return mcRoleService.addMcRole(info);
	}

}





































