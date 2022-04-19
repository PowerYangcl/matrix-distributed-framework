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
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.support.ValidateCodeSupport;

/**
 * @description: 获取验证码接口
 *
 * @author Yangcl
 * @date 2018年11月27日 下午2:42:08 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi140Processor extends BaseClass implements IBaseProcessor {
	@Override
	public Result<JSONObject> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		McUserInfoDto dto = param.getData().toJavaObject(McUserInfoDto.class);
		dto.setUserCache(param.getUserCache());
		ValidateCodeSupport code = new ValidateCodeSupport(100,30,4,30,25);
		return code.createValidateCode();
		
	}
}






























