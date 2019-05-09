package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.CompanyInfoDto;
import com.matrix.service.ICompanyInfoService;

/**
 * @description: 查询公司信息
 *
 * @author wanghao
 * @date 2018年11月22日 下午8:10:39 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=CompanyInfoDto.class)
public class ManagerApi132Processor extends BaseClass implements IBaseProcessor {
	
	@Inject
	private ICompanyInfoService companyInfoService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		CompanyInfoDto dto = JSONObject.parseObject(param.getString("data"), CompanyInfoDto.class);
		return companyInfoService.selectCompanyInfo(dto);
	}

}






























