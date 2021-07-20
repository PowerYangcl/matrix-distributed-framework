package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.view.McSysFunctionView;

public interface IMcSysFunctionMapper extends IBaseDao<Long , McSysFunction , McSysFunctionDto , McSysFunctionView>{
	
	
	
    public List<McSysFunction> getSysFuncList(McSysFunction e);

	public Integer deleteByIds(List<Long> list);   
}