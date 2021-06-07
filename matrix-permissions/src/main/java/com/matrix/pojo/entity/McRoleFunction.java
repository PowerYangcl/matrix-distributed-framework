package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McRoleFunction extends BaseEntity{
	
	private static final long serialVersionUID = 3264614761879499258L;
	private Long id;
    private Long mcRoleId;
    private Long mcSysFunctionId; 
    private String remark;
    
}