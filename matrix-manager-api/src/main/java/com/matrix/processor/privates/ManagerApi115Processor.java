package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.request.AddMcSysFunctionRequest;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
 * @tag MANAGER-API-115
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.AddMcSysFunctionRequest.class)
public class ManagerApi115Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;  
	
	@Override
	public Result<McSysFunction> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		AddMcSysFunctionRequest info = JSONObject.parseObject(param.getString("data"), AddMcSysFunctionRequest.class);
		return mcSysFunctionService.addMcSysFunction(info);
	}

}








































