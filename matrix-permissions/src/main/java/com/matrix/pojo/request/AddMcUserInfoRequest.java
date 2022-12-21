package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.SignUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: leader Leader后台用户|admin 其他平台管理员
 * 
 * @author Yangcl
 * @date 2021-6-4 16:33:42
 * @home https://github.com/PowerYangcl
 * @path matrix-permissions / com.matrix.pojo.request.AddMcUserInfoRequest.java
 * @version 1.0.0.1
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AddMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 760590835631623894L;

	private McUserInfoView userCache;
	
	private Long cid;
	
	@NotBlank(message = "101010001")			// 101010001=用户名或密码不得为空
    private String userName;
	
	@NotBlank(message = "101010001")			// 101010001=用户名或密码不得为空
    private String password;
	
    private String idcard;
    private Integer sex;
    
    @NotBlank(message = "101010020")			// 101010020=用户手机号码不得为空
    private String mobile;
    
    @NotBlank(message = "101010021")		// 101010021=用户电子邮箱不得为空
    private String email;
    
    private String qq;
    private String remark;
    private String platform;
    
//    private String type;	leader Leader后台用户|admin 其他平台管理员|user其他平台用户
//    private Integer flag;	启用状态；1启用 2停用|type=user 则此字段生效
	
	public McUserInfo buildAddSysUser() {
		McUserInfo e = new McUserInfo();
		e.setUserName(userName);
		e.setPassword(SignUtil.md5Sign(password));
		e.setType(platform.split("@")[0]);
		e.setPlatform(platform.split("@")[1]);
		e.setQq(qq);
		e.setMobile(mobile);
		e.setEmail(email);
		e.setSex(sex);  
		e.setRemark(remark);
		e.setIdcard(idcard);
		e.buildAddCommon(userCache);
		return e;
	}
	
	
	/**
	 * @description: 由于仅限于【添加系统类型用户：leader 或 admin】故此处验证逻辑进行修改
	 * 
	 * @author Yangcl
	 * @date 2022-11-27 23:13:20
	 * @home https://github.com/PowerYangcl
	 * @version v1.6.1.6-multiple-jspweb
	 */
	public Result<?> validate(IMcUserInfoMapper mcUserInfoMapper) {
		if ((userCache.getType().equals("leader") || userCache.getType().equals("admin") )&& StringUtils.isBlank(platform)) {   
			// 101010018=平台识别码错误
			return Result.ERROR(this.getInfo(101010018), ResultCode.MISSING_ARGUMENT);
		}
		if(userCache.getType().equals("admin") && !StringUtils.contains(platform, "user@")) {
			// 101010069=系统用户类型错误，用户类型不得为{0}
			return Result.ERROR(this.getInfo(101010069, platform.split("@")[0]), ResultCode.MISSING_ARGUMENT);
		}
		if(!StringUtils.contains(platform, "@")) { // Leader平台传入的标识码会有 'leader@' + code (Leader平台用户)或者 'admin@' + code的情况(其他平台管理员由Leader创建);
			// 101010018=平台识别码错误
			return Result.ERROR(this.getInfo(101010018), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.contains(platform, "leader") && StringUtils.contains(platform, ",")) {
			// 101010067=Leader底层管理系统不得与业务系统共同勾选
			return Result.ERROR(this.getInfo(101010067), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.contains(platform, "leader") && !"133C9CB27E18".equals(platform.split("@")[1])) {
			// 101010068=Leader底层管理系统平台唯一标识码错误
			return Result.ERROR(this.getInfo(101010068), ResultCode.INVALID_ARGUMENT);
		}
		
		
		McUserInfo e = new McUserInfo();
		e.setUserName(userName);
		List<McUserInfo> list = mcUserInfoMapper.queryPage(e);
		if(list != null && list.size() != 0) { 		// 101010029=用户名已存在
			return Result.ERROR(this.getInfo(101010029), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		return Result.SUCCESS();
	}
}














