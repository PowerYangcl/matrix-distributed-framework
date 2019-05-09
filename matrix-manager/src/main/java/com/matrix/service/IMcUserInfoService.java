package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseView;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;

public interface IMcUserInfoService extends IBaseService<Long , McUserInfo , McUserInfoDto , BaseView> { 
	
	public JSONObject login(McUserInfoDto userInfo , HttpSession session);

	public JSONObject addSysUser(McUserInfoDto info , HttpSession session);

	public JSONObject editSysUser(McUserInfoDto info , HttpSession session);

	public JSONObject deleteUser(McUserInfoDto dto);

	public JSONObject logout(HttpSession session);

	public JSONObject updatePageStyle(McUserInfoDto dto);

	public JSONObject ajaxDrawAddUserPage();

	public JSONObject ajaxSystemUserList(McUserInfoDto dto , HttpSession session , HttpServletRequest request);

	/**
	 * @description: 验证用户登录信息|客户端用户：nodejs/IOS平板等
	 *
	 * @param request
	 * @param dto
	 * 
	 * @author Yangcl
	 * @date 2018年10月10日 上午10:51:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxClientLogin(HttpServletRequest request, McUserInfoDto dto);

	/**
	 * @description: 退出系统登录|客户端用户：nodejs/IOS平板等
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月10日 下午7:29:38 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxClientLogout(McUserInfoDto dto);

	/**
	 * @description: 获取用户详情
	 *
	 * @param dto
	 * @param session 
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:42:13 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindSysUser(McUserInfoDto dto);

	/**
	 * @description: 修改用户密码
	 *
	 * @param info 
	 * @author Yangcl
	 * @date 2018年10月29日 上午11:05:07 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPasswordReset(McUserInfoDto info);          
	
}





























