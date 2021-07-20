package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;

/**
 * @description:修改角色功能|【角色列表】->【角色功能】->【提交按钮】|TODO 接口功能已移除，等待另作他用
 * @tag MANAGER-API-120
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
public class ManagerApi120Processor extends BaseClass implements IBaseProcessor {
	
	@Override
	public Result<?> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		return null;
	}

}






























