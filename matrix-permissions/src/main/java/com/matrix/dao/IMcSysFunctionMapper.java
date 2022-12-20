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
    public List<McSysFunction> findPlatformInfoList(McSysFunctionDto dto);

	public Integer deleteByIds(List<Long> list);   
	
	
	/**
	 * @description: 根据mc_sys_function表id列表查询数据
	 * 
	 * @author Yangcl
	 * @date 2022-12-19 18:41:26
	 * @home https://github.com/PowerYangcl
	 * @version v1.6.1.6-multiple-jspweb
	 */
	public List<McSysFunction> findListByIds(McSysFunctionDto e);
}