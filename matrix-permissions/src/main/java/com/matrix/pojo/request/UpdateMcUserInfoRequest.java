package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.SignUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 4307560107936755283L;

	private McUserInfoView userCache;
	
	private Long id;
    private String userName;
    private String idcard;
    private Integer sex;
    private String mobile;
    private String email;
    private String qq;
    private String remark;
    private String userNameOld;

    private String password;				// 更新密码作为单独的权限进行剥离 - Yangcl
    private String oldPassWord;  	// 页面修改密码时传入原始的密码。如果页面传入的旧密码和原始密码不相等则返回错误提示
	
	public McUserInfo buildEditSysUser() {
		McUserInfo e = new McUserInfo();
		e.setId(id);
		e.setUserName(userName);
		e.setMobile(mobile);
		e.setEmail(email);
		e.setSex(sex);  
		e.setIdcard(idcard);
		e.setQq(qq);
		e.setRemark(remark);
		e.buildUpdateCommon(userCache);
		return e;
	}
	
	public Result<?> validateEditSysUser() {
		if(id == null) {		// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if (StringUtils.isBlank(userName)) {
			// 101010019=用户名不得为空
			return Result.ERROR(this.getInfo(101010019), ResultCode.MISSING_ARGUMENT);
		}
		if (StringUtils.isBlank(mobile)) {
			// 101010020=用户手机号码不得为空
			return Result.ERROR(this.getInfo(101010020), ResultCode.MISSING_ARGUMENT);
		}
		if (StringUtils.isBlank(email)) {
			// 101010021=用户电子邮箱不得为空
			return Result.ERROR(this.getInfo(101010020), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
	
	public McUserInfo buildAjaxPasswordReset() {
		McUserInfo e = new McUserInfo();
		e.setId(id);
		e.setPassword(SignUtil.md5Sign(password));
		e.buildUpdateCommon(userCache);
		return e;
	}
	
	public Result<?> validateAjaxPasswordReset() {
		if(id == null) {		// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if (StringUtils.isBlank(password)) {
			// 101010037=用户密码不得为空
			return Result.ERROR(this.getInfo(101010037), ResultCode.MISSING_ARGUMENT);
		}
		if(password.length() < 6) {			// 101010047=新密码长度不得小于6位
			return Result.ERROR(this.getInfo(101010047), ResultCode.INVALID_ARGUMENT);
		}
		if (StringUtils.isBlank(oldPassWord)) {
			// 101010016=密码更新失败，原密码缺失
			return Result.ERROR(this.getInfo(101010016), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














