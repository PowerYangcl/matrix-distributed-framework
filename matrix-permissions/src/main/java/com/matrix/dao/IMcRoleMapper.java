package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McRoleView;

public interface IMcRoleMapper extends IBaseDao<Long , McRole , McRoleDto , McRoleView>{
	
	public List<McRoleCache> findMcRoleDtoList();
	
	public McRoleCache findRoleCache(Long id);
	
	public List<McRoleView> queryPageView(McRoleDto e);
}