package com.matrix.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.matrix.dao.IMcRoleMapper;
import com.matrix.dao.IMcRoleFunctionMapper;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.service.IManagerCenterService;

/**
 * @description: 系统权限服务类
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年3月1日 上午10:18:11 
 * @version 1.0.0
 */
@Service("managerCenterService")
public class ManagerCenterServiceImpl implements IManagerCenterService{
	@Resource
	private IMcUserInfoMapper mcUserInfoMapper;
	@Resource
	private IMcRoleMapper mcRoleMapper;
	@Resource
	private IMcRoleFunctionMapper mcRoleFunctionMapper;
	@Resource
	private IMcSysFunctionMapper mcSysFunctionMapper;
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper ;
	
	
	
	
}

























































