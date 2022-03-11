package com.matrix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.request.AddAcIncludeDomainRequest;
import com.matrix.pojo.request.AddApiInfoRequest;
import com.matrix.pojo.request.AddApiProjectListRequest;
import com.matrix.pojo.request.AddRequestInfoRequest;
import com.matrix.pojo.request.DeleteAcIncludeDomainRequest;
import com.matrix.pojo.request.DeleteApiInfoRequest;
import com.matrix.pojo.request.DeleteApiProjectListRequest;
import com.matrix.pojo.request.FindAcIncludeDomainListRequest;
import com.matrix.pojo.request.FindApiInfoListRequest;
import com.matrix.pojo.request.FindApiInfoRequest;
import com.matrix.pojo.request.FindApiProjectListRequest;
import com.matrix.pojo.request.FindRequestInfoListRequest;
import com.matrix.pojo.request.UpdateAcIncludeDomainRequest;
import com.matrix.pojo.request.UpdateApiInfoDiscardRequest;
import com.matrix.pojo.request.UpdateApiInfoRequest;
import com.matrix.pojo.request.UpdateApiProjectListRequest;
import com.matrix.pojo.request.UpdateRequestInfoRequest;
import com.matrix.pojo.view.AcApiInfoView;
import com.matrix.pojo.view.AcApiProjectView;
import com.matrix.pojo.view.AcIncludeDomainView;
import com.matrix.pojo.view.AcRequestInfoView;
import com.matrix.pojo.view.ApiTreeView;
import com.matrix.processor.common.ValidationTest;

public interface IApiCenterService extends IBaseService<Long , AcApiInfo, AcApiInfoDto , AcApiInfoView>{

	// ac_api_project 表
	public Result<PageInfo<AcApiProjectView>> ajaxApiProjectList(FindApiProjectListRequest param, HttpServletRequest request);
	public Result<?> ajaxBtnApiProjectAdd(AddApiProjectListRequest param, HttpSession session);
	public Result<?> ajaxBtnApiProjectEdit(UpdateApiProjectListRequest param, HttpSession session);
	public Result<?> ajaxBtnApiProjectDelete(DeleteApiProjectListRequest param, HttpSession session);
	
	// ac_include_domain 表
	public Result<PageInfo<AcIncludeDomainView>> ajaxIncludeDomainPageList(FindAcIncludeDomainListRequest param, HttpServletRequest request, HttpSession session);
	public Result<List<AcIncludeDomainView>> ajaxIncludeDomainList(HttpServletRequest request, HttpSession session);         
	public Result<?> ajaxBtnApiDomainAdd(AddAcIncludeDomainRequest param, HttpSession session);
	public Result<?> ajaxBtnApiDomainEdit(UpdateAcIncludeDomainRequest param, HttpSession session);
	public Result<?> ajaxBtnApiDomainDelete(DeleteAcIncludeDomainRequest param, HttpSession session);
	
	// ac_api_info 表
	public Result<List<ApiTreeView>> ajaxApiInfoList(FindApiInfoListRequest param, HttpSession session);
	public Result<AcApiInfo> ajaxApiInfoAdd(AddApiInfoRequest param, HttpSession session);
	public Result<AcApiInfoCache> ajaxApiInfoFind(FindApiInfoRequest param);
	public Result<AcApiInfoCache> ajaxApiInfoEdit(UpdateApiInfoRequest param, HttpSession session);
	public Result<?> ajaxApiInfoRemove(DeleteApiInfoRequest param, HttpSession session);
	public Result<?> ajaxApiInfoDiscard(UpdateApiInfoDiscardRequest param, HttpSession session);     
	
	// ac_request_info 表 
	public String requestInfoList();
	public Result<PageInfo<AcRequestInfoView>> ajaxRequestInfoList(FindRequestInfoListRequest param, HttpServletRequest request, HttpSession session);
	public Result<?> ajaxRequestInfoAdd(AddRequestInfoRequest param, HttpServletRequest request, HttpSession session);
	public Result<?> ajaxRequestInfoEdit(UpdateRequestInfoRequest param, HttpServletRequest request, HttpSession session);
	
	
	// 页面接口测试
	public String pageApicenterApiTest();
	public Result<String> ajaxFindRequestValue(String key);
	public Result<Object> ajaxFindRequestDto(String target);			// controller层未引用
 
	
	public Result<String> ajaxValidationTest(@Valid ValidationTest dto);
}
