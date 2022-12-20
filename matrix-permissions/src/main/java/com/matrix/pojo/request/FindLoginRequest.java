package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.view.ClientLoginView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.support.ValidateCodeSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindLoginRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 7174953732796704401L;

	
	@NotBlank(message = "101010001")	// 101010001=用户名或密码不得为空
	private String userName;
	
	@NotBlank(message = "101010001")	// 101010001=用户名或密码不得为空
	private String password;
	
	@NotBlank(message = "101010002")	 // 101010002=平台唯一标识码不得为空
	private String platform;
	
	private String validateCode;				// 验证码
	
	private String validateCodeKey;		// 验证码对应的redis key

	private McUserInfoView userCache;
    
	public Result<ClientLoginView> validateAjaxClientLogin() {
		if(this.getConfig("matrix-core.model").equals("master") || this.getConfig("matrix-core.model").equals("prod")) {   // 线上环境验证码生效
			if(StringUtils.isBlank(validateCode)) {		// 101010024=验证码不得为空
				return Result.ERROR(this.getInfo(101010024), ResultCode.MISSING_ARGUMENT);
			}
			if(StringUtils.isBlank(validateCodeKey)) {		// 101010025=验证码Key不能为空
				return Result.ERROR(this.getInfo(101010025), ResultCode.MISSING_ARGUMENT);
			}
			String validateCode = ValidateCodeSupport.getCode(validateCodeKey);
			if(StringUtils.isBlank(validateCode)) {		// 101010026=验证码已过期
				return Result.ERROR(this.getInfo(101010026), ResultCode.INTERNAL_VALIDATION_FAILED);
			}
			if(!validateCode.equalsIgnoreCase(validateCode)) {		// 101010061=验证码错误
				return Result.ERROR(this.getInfo(101010061), ResultCode.INTERNAL_VALIDATION_FAILED);
			}
		}
		return Result.SUCCESS();
	}
}














