package com.matrix.processor.privates.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.ProcessorTestDto;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.service.IApiCenterService;

/**
 * @description: 这是一个测试私有接口处理的类 
 * 	每个实现了IBaseProcessor接口的处理类，都需要添加@MatrixRequest标签，来标注出该类的DTO对象
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月13日 下午12:41:42 
 * @version 1.0.0
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.ProcessorTestDto.class) 
public class TestPrivateProcessor extends BaseClass implements IBaseProcessor {

	@Inject 
	private IApiCenterService service;  
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response , HttpSession session, JSONObject data) {
		ProcessorTestDto dto = JSONObject.parseObject(data.getString("data"), ProcessorTestDto.class); 
		
		
		return service.ajaxApiProjectList(null, request, null); 
	}

}

























