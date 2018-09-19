package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.ApiExampleDto;
import com.matrix.service.IExampleService;

/**
 * @description: 这是一个测试私有接口处理的类 
 * 	每个实现了IBaseProcessor接口的处理类，都需要添加@MatrixRequest标签，来标注出该类的DTO对象
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月13日 下午12:41:42 
 * @version 1.0.0
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.ApiExampleDto.class) 
public class ExamplePrivateProcessor extends BaseClass implements IBaseProcessor {

	@Inject 
	private IExampleService service;   
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, JSONObject data) {
		ApiExampleDto dto = JSONObject.parseObject(data.getString("data"), ApiExampleDto.class); 
		return service.apiProcessorTest(dto);
	}

}

























