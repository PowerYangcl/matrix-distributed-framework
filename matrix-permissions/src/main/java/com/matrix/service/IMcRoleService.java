package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McRoleView;

public interface IMcRoleService extends IBaseService<Long , McRole , McRoleDto , McRoleView> {

	public JSONObject addMcRole(McRole info);

	public JSONObject editSysRole(McRoleDto dto);

	/**
	 * @descriptions 展示权限列表|如果用户已经有权限了则标识出来
	 * 
	 * @date 2017年5月24日 上午12:05:56
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject userRoleList(McRoleDto dto , HttpServletRequest request);

	/**
	 * @description: 系统角色列表数据
	 *
	 * @param dto 
	 * 
	 * @author Yangcl
	 * @date 2018年9月24日 下午3:58:48 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxSystemRoleList(McRoleDto dto, HttpServletRequest request);

	/**
	 * @description: 权限详情
	 *
	 * @param info.id mc_role表自增id
	 * 
	 * @author Yangcl
	 * @date 2018年10月13日 下午3:37:05 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindRoleInfo(McRole info);

	/**
	 * @Description //获取用户的角色（用户登录以后查自身的角色）
	 * @Author mashaohua
	 * @Date 2018年12月19日 下午3:37:05
	 * @Param [dto, request]
	 * @version 1.0.0.1
	 **/
	JSONObject userRoleListByid(McRoleDto dto, HttpServletRequest request);
}
