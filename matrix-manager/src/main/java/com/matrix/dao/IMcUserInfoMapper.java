package com.matrix.dao;

import java.util.List;

import com.matrix.base.BaseView;
import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;

public interface IMcUserInfoMapper extends IBaseDao<Long , McUserInfo , McUserInfoDto , BaseView>{ 
	
	public McUserInfoView login(McUserInfo entity);
	
	public McUserInfoView loadUserInfo(Long id); 
	
	public List<McUserInfoView> loadUserInfoList();

	/**
	 * @description: 显示没有关联任何角色的用户列表 |巧妙的左查询
	 * 
	 * @param entity
	 * @return
	 * @author Yangcl 
	 * @date 2017年5月19日 下午5:30:43 
	 * @version 1.0.0.1
	 */
	public List<McUserInfo> mcUserList(McUserInfo entity);  
	
	
	public Integer insertSelectiveGetZid(McUserInfo entity);  
}