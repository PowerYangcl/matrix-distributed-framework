package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.entity.McOrganization;

/**
 * @description: 暂时空闲，未使用
 * @tag MANAGER-API-130
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.entity.McOrganization.class)
public class ManagerApi130Processor extends BaseClass implements IBaseProcessor {

	
	@Override
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		McOrganization dto = param.getData().toJavaObject(McOrganization.class);
		dto.setUserCache(param.getUserCache());
		return null;
	}

}






























