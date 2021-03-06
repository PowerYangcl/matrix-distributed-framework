package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.List;

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
 * @description: leader Leader后台用户|admin 其他平台管理员|user其他平台用户
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
    private String userName;
    private Long mcOrganizationId;
    private String password;
    private String idcard;
    private Integer sex;
    private String mobile;
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
		if(StringUtils.contains(platform, "@")) {
			e.setType(platform.split("@")[0]);
			// Leader平台传入的标识码会有 'leader@' + code (Leader平台用户)或者 'admin@' + code的情况(其他平台管理员由Leader创建);
			e.setPlatform(platform.split("@")[1]);
			e.setCid(cid); 		// Leader后台创建的admin类型用户
		}else {
			e.setType("user"); // 标识其他平台管理员所创建的用户
			e.setPlatform(userCache.getPlatform());	// 非Leader平台(具体某个平台)创建的用户，则继承其创建者的平台标识码
			e.setCid(userCache.getCid()); 		// 继承其创建者的cid
			e.setUserCode(String.valueOf(System.currentTimeMillis()).substring(5, 13));
			e.setMcOrganizationId(mcOrganizationId);		// 组织机构id|type=user 则此字段生效|默认0 代表无归属机构
		}
		e.setQq(qq);
		e.setMobile(mobile);
		e.setEmail(email);
		e.setSex(sex);  
		e.setRemark(remark);
		e.setIdcard(idcard);
		e.buildAddCommon(userCache);
		return e;
	}
	
	public Result<?> validateAddSysUser(IMcUserInfoMapper mcUserInfoMapper) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			// 101010001=用户名或密码不得为空
			return Result.ERROR(this.getInfo(101010001), ResultCode.MISSING_ARGUMENT);
		}
		if (StringUtils.isBlank(mobile)) {
			// 101010020=用户手机号码不得为空
			return Result.ERROR(this.getInfo(101010020), ResultCode.MISSING_ARGUMENT);
		}
		if (StringUtils.isBlank(email)) {
			// 101010021=用户电子邮箱不得为空
			return Result.ERROR(this.getInfo(101010020), ResultCode.MISSING_ARGUMENT);
		}
		if (userCache.getType().equals("leader") && StringUtils.isBlank(platform)) {   
			// 101010018=平台识别码错误
			return Result.ERROR(this.getInfo(101010018), ResultCode.MISSING_ARGUMENT);
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














