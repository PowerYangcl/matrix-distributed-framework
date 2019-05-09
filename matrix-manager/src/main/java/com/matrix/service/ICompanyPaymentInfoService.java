package com.matrix.service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.CompanyPaymentInfoDto;
import com.matrix.pojo.entity.CompanyPaymentInfo;
import com.matrix.pojo.view.CompanyPaymentInfoView;

/** @description: 
 *
 * @author wanghao
 * @date 2018年11月21日 下午4:33:15 
 * @version 1.0.0.1
 */
public interface ICompanyPaymentInfoService extends IBaseService<Long , CompanyPaymentInfo , CompanyPaymentInfoDto , CompanyPaymentInfoView>{
	
	public JSONObject addCompanyPaymentInfo(CompanyPaymentInfo e); 
	
	public JSONObject editCompanyPaymentInfo(CompanyPaymentInfo e); 
	
	public JSONObject delCompanyPaymentInfo(CompanyPaymentInfo e); 
	
	public JSONObject getCompanyPaymentInfo(CompanyPaymentInfoDto dto); 
}
