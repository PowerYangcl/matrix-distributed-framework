package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McRoleView;

public interface IMcRoleService extends IBaseService<Long , McRole , McRoleDto , McRoleView> {

	public JSONObject addMcRole(McRole info , HttpSession session);

	public JSONObject editSysRole(McRole info, HttpSession session);

	/**
	 * @descriptions 展示权限列表|如果用户已经有权限了则标识出来
	 * 
	 * @date 2017年5月24日 上午12:05:56
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject userRoleList(McRoleDto dto , HttpServletRequest request); 

}
