package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McRole extends BaseEntity{
	
	private static final long serialVersionUID = 3626119718702587829L;
	private Long id;
	private String type;			// leader Leader后台创建的角色|admin 其他平台管理员创建的角色
	private String platform;
    private String roleName;
    private String roleDesc;
    private String remark;
    
}










