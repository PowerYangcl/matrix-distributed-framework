package com.matrix.processor.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.dto.FindConfigInfoDto;

/**
 * @description: 获取API接口项目中的Config配置信息|如果部署多个API接口服务器，
 * 		则可能随机获取其中的一个，但会返回一个标识的IP地址作为区分依据
 *			{"key":"matrix-api.default_leader_service_list_prod"}
 * @tag API-COMMON-CONFIG-INFO
 * 
 * @author Yangcl
 * @date 2018年11月26日 下午7:12:44 
 * @version 1.0.0.1
 */
public class ApiFindConfigInfoProcessor extends BaseClass implements IBaseProcessor {

	public Result<String> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		FindConfigInfoDto dto = param.getData().toJavaObject(FindConfigInfoDto.class);
		String value = this.getConfig(dto.getKey());
		if(StringUtils.isBlank(value)) {
			value = "未发现对应的值";
		}
		return Result.SUCCESS(value); 
	}
}































