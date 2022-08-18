package com.matrix.service;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.pojo.request.AddGatewayRouteRequest;
import com.matrix.pojo.request.FindGatewayRouteListRequest;
import com.matrix.pojo.response.GwRouteListResponse;

public interface IGatewayConfigService {
	
	public Result<?> ajaxBtnGatewayRouteAdd(AddGatewayRouteRequest param, HttpServletRequest request);

	public Result<PageInfo<GwRouteListResponse>> ajaxGatewayRouteList(FindGatewayRouteListRequest param, HttpServletRequest request);
	
}
