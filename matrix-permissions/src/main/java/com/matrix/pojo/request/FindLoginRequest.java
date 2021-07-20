package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.view.ClientLoginView;
import com.matrix.pojo.view.LoginView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.support.ValidateCodeSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindLoginRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 7174953732796704401L;

	private McUserInfoView userCache;
	
	private String userName;
	
	private String password;
	
	private String platform;
	
	private String validateCode;				// 验证码
	private String validateCodeKey;		// 验证码对应的redis key

	public Result<LoginView> validateLogin() {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			// 101010001=用户名或密码不得为空
			return Result.ERROR(this.getInfo(101010001), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(platform)) {		// 101010002=平台唯一标识码不得为空
			return Result.ERROR(this.getInfo(101010002), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
	
    
	public Result<ClientLoginView> validateAjaxClientLogin() {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			// 101010001=用户名或密码不得为空
			return Result.ERROR(this.getInfo(101010001), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(platform)) {		// 101010002=平台唯一标识码不得为空
			return Result.ERROR(this.getInfo(101010002), ResultCode.MISSING_ARGUMENT);
		}
		
		if(this.getConfig("matrix-core.model").equals("master")) {   // 线上环境验证码生效
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














