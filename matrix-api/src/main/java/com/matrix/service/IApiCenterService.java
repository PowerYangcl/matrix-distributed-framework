package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.dto.AcRequestInfoDto;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.AcApiInfoView;

public interface IApiCenterService extends IBaseService<Long , AcApiInfo, AcApiInfoDto , AcApiInfoView>{

	// ac_api_project 表
	public String apiProjectList();
	public JSONObject ajaxApiProjectList(AcApiProject entity, HttpServletRequest request, HttpSession session);
	public JSONObject ajaxApiProjectAdd(AcApiProject entity, HttpSession session);
	public JSONObject ajaxApiProjectEdit(AcApiProject entity, HttpSession session);
	
	// ac_include_domain 表
	public String apiIncludeDomainList();
	public JSONObject ajaxIncludeDomainPageList(AcIncludeDomain entity, HttpServletRequest request, HttpSession session);
	public JSONObject ajaxIncludeDomainList(AcIncludeDomain entity, HttpServletRequest request, HttpSession session);         
	public JSONObject ajaxApiDomainAdd(AcIncludeDomain entity, HttpSession session);
	public JSONObject ajaxApiDomainEdit(AcIncludeDomain entity, HttpSession session);
	
	// ac_api_info 表
	public String apiInfoList();
	public JSONObject ajaxApiInfoList(AcApiInfo e, HttpSession session);
	public JSONObject ajaxApiInfoAdd(AcApiInfoDto d, HttpSession session);
	public JSONObject ajaxApiInfoFind(AcApiInfoDto dto);
	public JSONObject ajaxApiInfoEdit(AcApiInfoDto d, HttpSession session);
	
	// ac_request_info 表 
	public String requestInfoList();
	public JSONObject ajaxRequestInfoList(AcRequestInfo entity, HttpServletRequest request, HttpSession session);
	public JSONObject ajaxRequestInfoAdd(AcRequestInfo entity, HttpServletRequest request, HttpSession session);
	public JSONObject ajaxRequestInfoEdit(AcRequestInfoDto dto, HttpServletRequest request, HttpSession session);
	
	
	// 页面接口测试
	public String pageApicenterApiTest();
	public JSONObject ajaxFindRequestDto(String target);
	public JSONObject ajaxFindRequestValue(String key);        
 
}
