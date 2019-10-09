package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McUserRoleView;

public interface IMcUserRoleMapper extends IBaseDao<Long , McUserRole , McUserRoleDto , McUserRoleView >{

	public List<McUserRoleView> userRoleFuncList(McUserRoleDto dto);  
	
	public List<McUserRole> selectByMcRoleId(Long mcRoleId);
	
	public List<McUserRole> selectByMcUserId(Long mcUserId);
	
	public Integer deleteByUserId(Long userId);
}