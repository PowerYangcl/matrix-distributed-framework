package com.matrix.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.dao.IMcRoleFunctionMapper;
import com.matrix.dao.IMcSellerCompanyMapper;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.entity.McSellerCompany;
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
	private IMcSellerCompanyMapper mcSellerCompanyMapper; 
	@Resource
	private IMcSysFunctionMapper mcSysFunctionMapper;
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper ;
	
	
	/**
	 * @description: 获取公司列表 
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2017年3月1日 上午10:55:33 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject companyList() {
		JSONObject result = new JSONObject();
		McSellerCompany entity = new McSellerCompany();
		List<McSellerCompany> list = mcSellerCompanyMapper.findList(entity);
		if(list != null && list.size() > 0){
			result.put("status" , "success");
			result.put("list" , list);
			return result;
		}else{
			result.put("status", "error");
			result.put("msg", "结果集为空");
			return result;
		}
	}
	
	
}

























































