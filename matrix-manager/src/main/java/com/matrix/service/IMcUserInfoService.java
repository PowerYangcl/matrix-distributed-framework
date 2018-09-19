package com.matrix.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseView;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;

public interface IMcUserInfoService extends IBaseService<Long , McUserInfo , McUserInfoDto , BaseView> { 
	
	public JSONObject login(McUserInfo userInfo , HttpSession session);

	public JSONObject addSysUser(McUserInfo info);

	public JSONObject editSysUser(McUserInfo info , HttpSession session);

	public JSONObject deleteUser(Long id , HttpSession session);

	public JSONObject logout(HttpSession session);

	public JSONObject updatePageStyle(McUserInfoView dto);         
	
}
