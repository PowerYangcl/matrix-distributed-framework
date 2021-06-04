package com.matrix.pojo.dto;

import java.util.Date;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserInfoDto extends BaseDto{
	
	private static final long serialVersionUID = 4629549271113492123L;
	
	private Long id;
	private String userName;
	private String password;
	private String platform;	 
	private String mobile;
	
	private String userNameOld;
    private Long cid;
    private String userCode;
    private Long mcOrganizationId;
    private String type;
    private Integer flag;
    private String idcard;
    private Integer sex;
    private Date birthday;
    private String email;
    private String qq;
    private String remark;
    private String picUrl;
    private String accessToken;
    
    private String pageCss;
  
    private String orgIds;

    private String oldPassWord;  // 页面修改密码时传入原始的密码。如果页面传入的旧密码和原始密码不相等则返回错误提示

	private String validateCode;//验证码
    private String validateCodeKey;//验证码对应的redis key
}