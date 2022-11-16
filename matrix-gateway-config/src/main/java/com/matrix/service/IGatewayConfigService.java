package com.matrix.service;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.pojo.request.AddGatewayRouteRequest;
import com.matrix.pojo.request.EditGatewayRouteRequest;
import com.matrix.pojo.request.FindGatewayRouteListRequest;
import com.matrix.pojo.response.GwRouteResponse;

public interface IGatewayConfigService {
	
	public Result<?> ajaxBtnGatewayRouteAdd(AddGatewayRouteRequest param, HttpServletRequest request);

	public Result<PageInfo<GwRouteResponse>> ajaxGatewayRouteList(FindGatewayRouteListRequest param, HttpServletRequest request);

	public Result<?> ajaxBtnGatewayRouteEdit(EditGatewayRouteRequest param, HttpServletRequest request);
	
}
