package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.ApiExampleDto;
import com.matrix.pojo.dto.UserDemoDto;
import com.matrix.pojo.entity.UserDemo;
import com.matrix.pojo.view.UserDemoView;

public interface IExampleService  extends IBaseService<Long , UserDemo, UserDemoDto , UserDemoView> {

	public JSONObject addInfo(UserDemo entity , HttpSession session);
	
	public JSONObject deleteOne(UserDemo entity);

	public JSONObject ajaxUploadFileCfile(String type , HttpServletRequest request);

	public JSONObject apiHttpClientTest(HttpServletRequest request);   
	
	public JSONObject apiProcessorTest(ApiExampleDto dto);

}
