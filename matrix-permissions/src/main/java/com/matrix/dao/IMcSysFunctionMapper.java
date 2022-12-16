package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.view.McSysFunctionView;

public interface IMcSysFunctionMapper extends IBaseDao<Long , McSysFunction , McSysFunctionDto , McSysFunctionView>{
	
	/**
	 * @description: 获取平台列表
	 * 
	 * @author Yangcl
	 * @date 2022-11-26 23:30:38
	 * @home https://github.com/PowerYangcl
	 * @version v1.6.1.6-multiple-jspweb
	 */
    public List<McSysFunction> findPlatformInfoList(McSysFunctionDto e);

	public Integer deleteByIds(List<Long> list);   
}