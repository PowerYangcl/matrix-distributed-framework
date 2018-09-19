package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McRoleFunctionDto;
import com.matrix.pojo.entity.McRoleFunction;
import com.matrix.pojo.view.McRoleFunctionView;

public interface IMcRoleFunctionMapper extends IBaseDao<Long , McRoleFunction , McRoleFunctionDto , McRoleFunctionView>{
	
	public Integer deleteByMcRoleId(Long mcRoleId);
	
}
