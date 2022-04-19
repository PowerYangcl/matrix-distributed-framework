package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;

/**
 * @description: 添加用户界面-绘制所属公司和平台分配|TODO 接口功能已移除，等待另作他用
 * @tag MANAGER-API-103
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=String.class)
public class ManagerApi103Processor extends BaseClass implements IBaseProcessor {
	
	@Override
	public Result<?> processor(BaseApiDto data, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return null;
	}

}
