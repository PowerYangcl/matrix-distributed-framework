package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 关联用户与某一个角色
 * @tag MANAGER-API-122
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */

/**
 * 
 * request 实体   McUserRole
 * {
	 "mcRoleId": "62"
	 "mcUserId": "1997"
   }
   response返回类型
  {"status":"success"}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.entity.McUserRole.class)
public class ManagerApi122Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserRole entity = JSONObject.parseObject(param.getString("data"), McUserRole.class);
		return mcSysFunctionService.addUserRole(entity);
	}

}






























