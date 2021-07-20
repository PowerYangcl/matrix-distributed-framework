package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McRoleDto extends BaseDto{
	
	private static final long serialVersionUID = 6134529834950320057L;
	
	private Long cid;
	private String type;	// leader Leader后台创建的角色|admin 其他平台管理员创建的角色
	private String platform;
    private String roleName;
    
}
