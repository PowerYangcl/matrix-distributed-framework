package com.matrix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.dto.AcRequestInfoDto;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.AcApiInfoView;
import com.matrix.pojo.view.AcApiProjectView;
import com.matrix.pojo.view.AcIncludeDomainView;
import com.matrix.pojo.view.AcRequestInfoView;
import com.matrix.pojo.view.ApiTreeView;

public interface IApiCenterService extends IBaseService<Long , AcApiInfo, AcApiInfoDto , AcApiInfoView>{

	// ac_api_project 表
	public Result<PageInfo<AcApiProjectView>> ajaxApiProjectList(AcApiProject entity, HttpServletRequest request);
	public Result<?> ajaxBtnApiProjectAdd(AcApiProject entity, HttpSession session);
	public Result<?> ajaxBtnApiProjectEdit(AcApiProject entity, HttpSession session);
	public Result<?> ajaxBtnApiProjectDelete(AcApiProject entity, HttpSession session);
	
	// ac_include_domain 表
	public Result<PageInfo<AcIncludeDomainView>> ajaxIncludeDomainPageList(AcIncludeDomain entity, HttpServletRequest request, HttpSession session);
	public Result<List<AcIncludeDomainView>> ajaxIncludeDomainList(AcIncludeDomain entity, HttpServletRequest request, HttpSession session);         
	public Result<?> ajaxBtnApiDomainAdd(AcIncludeDomain entity, HttpSession session);
	public Result<?> ajaxBtnApiDomainEdit(AcIncludeDomain entity, HttpSession session);
	public Result<?> ajaxBtnApiDomainDelete(AcIncludeDomain entity, HttpSession session);
	
	// ac_api_info 表
	public Result<List<ApiTreeView>> ajaxApiInfoList(AcApiInfo e, HttpSession session);
	public Result<AcApiInfo> ajaxApiInfoAdd(AcApiInfoDto d, HttpSession session);
	public Result<AcApiInfoCache> ajaxApiInfoFind(AcApiInfoDto dto);
	public Result<AcApiInfoCache> ajaxApiInfoEdit(AcApiInfoDto d, HttpSession session);
	public Result<?> ajaxApiInfoRemove(AcApiInfoDto d, HttpSession session);
	public Result<?> ajaxApiInfoDiscard(AcApiInfo e, HttpSession session);     
	
	// ac_request_info 表 
	public String requestInfoList();
	public Result<PageInfo<AcRequestInfoView>> ajaxRequestInfoList(AcRequestInfo entity, HttpServletRequest request, HttpSession session);
	public Result<?> ajaxRequestInfoAdd(AcRequestInfo entity, HttpServletRequest request, HttpSession session);
	public Result<?> ajaxRequestInfoEdit(AcRequestInfoDto dto, HttpServletRequest request, HttpSession session);
	
	
	// 页面接口测试
	public String pageApicenterApiTest();
	public Result<String> ajaxFindRequestValue(String key);
	public Result<Object> ajaxFindRequestDto(String target);			// controller层未引用
 
}
