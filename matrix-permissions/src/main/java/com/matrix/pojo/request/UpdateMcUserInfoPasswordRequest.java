package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.SignUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateMcUserInfoPasswordRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 4307560107936755283L;

	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	
	@NotBlank(message = "100020111")		// 100020111=主键丢失
	private Long id;

	@NotBlank(message = "101010037")		// 101010037=用户密码不得为空
	@Length(min=6, message = "101010047")		// 101010047=新密码长度不得小于6位
    private String password;				// 更新密码作为单独的权限进行剥离 - Yangcl
	
	@NotBlank(message = "101010016")		// 101010016=密码更新失败，原密码缺失
    private String oldPassWord;  	// 页面修改密码时传入原始的密码。如果页面传入的旧密码和原始密码不相等则返回错误提示
	
	private String userName;
	
	private McUserInfoView userCache;
	
	public McUserInfo buildAjaxPasswordReset() {
		McUserInfo e = new McUserInfo();
		e.setId(id);
		e.setPassword(SignUtil.md5Sign(password));
		e.buildUpdateCommon(userCache);
		return e;
	}
	
	public Result<?> validateAjaxPasswordReset() {
		McUserInfo user = mcUserInfoMapper.find(id);
		if(user == null) {			// 101010038=用户密码重置失败，未找到指定用户，请重试
			return Result.ERROR(this.getInfo(101010038), ResultCode.RESULT_NULL);
		}
		if (!user.getPassword().equals(SignUtil.md5Sign(oldPassWord))){
			// 101010042=用户密码重置失败，原始密码不正确
			return Result.ERROR(this.getInfo(101010042), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		// 重新赋值，避免使用页面传入的值。
		this.userName = user.getUserName();
		this.oldPassWord = user.getPassword();
		return Result.SUCCESS();
	}
}














