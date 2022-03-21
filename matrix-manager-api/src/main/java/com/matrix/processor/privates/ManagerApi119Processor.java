package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;

/**
 * @description: 创建系统角色|TODO 接口功能已移除，等待另作他用
 * @tag MANAGER-API-119
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi119Processor extends BaseClass implements IBaseProcessor<JSONObject> {

	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, BaseApiDto<JSONObject> param) {
		return null;
	}

}















































