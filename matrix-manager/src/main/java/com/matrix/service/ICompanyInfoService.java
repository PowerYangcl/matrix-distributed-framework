package com.matrix.service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.CompanyInfoDto;
import com.matrix.pojo.entity.CompanyInfo;
import com.matrix.pojo.view.CompanyInfoView;

public interface ICompanyInfoService extends IBaseService<Long , CompanyInfo , CompanyInfoDto , CompanyInfoView> {
	/**
	 * @description: 添加公司信息
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午7:52:50 
	 * @version 1.0.0.1
	 */
	public JSONObject addCompanyInfo(CompanyInfo e); 
	/**
	 * @description: 编辑公司信息
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:01:35 
	 * @version 1.0.0.1
	 */
	public JSONObject editCompanyInfo(CompanyInfo e); 
	/**
	 * @description: 删除公司信息
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:05:52 
	 * @version 1.0.0.1
	 */
	public JSONObject delCompanyInfo(CompanyInfo e); 
	/**
	 * @description: 查询公司信息
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:18:57 
	 * @version 1.0.0.1
	 */
	public JSONObject getCompanyInfo(CompanyInfoDto dto); 
	/**
	 * @description: 租户端查询公司信息
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:18:57 
	 * @version 1.0.0.1
	 */
	public JSONObject selectCompanyInfo(CompanyInfoDto dto); 
	/**
	 * @description: 租户端设置公司信息
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:18:57 
	 * @version 1.0.0.1
	 */
	public JSONObject updateCompanyInfo(CompanyInfoDto dto); 
}
