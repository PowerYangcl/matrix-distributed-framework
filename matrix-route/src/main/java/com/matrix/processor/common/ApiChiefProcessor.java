package com.matrix.processor.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.dto.ApiChiefDto;

/**
 * @description: 维护系统核心
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年10月22日 上午10:36:04 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.ApiChiefDto.class) 
public class ApiChiefProcessor  extends BaseClass implements IBaseProcessor {

	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		ApiChiefDto dto = JSONObject.parseObject(param.getString("data"), ApiChiefDto.class); 
		// TODO invoke chiefCommand()
		return null;
	}

}






































