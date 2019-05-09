package com.matrix.processor.privates;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.support.ValidateCodeSupport;

/**
 * @description: 获取验证码接口
 *
 * @author wanghao
 * @date 2018年11月27日 下午2:42:08 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi140Processor extends BaseClass implements IBaseProcessor {
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		ValidateCodeSupport code = new ValidateCodeSupport(100,30,4,30,25);
		return code.createValidateCode();
		
	}
}






























