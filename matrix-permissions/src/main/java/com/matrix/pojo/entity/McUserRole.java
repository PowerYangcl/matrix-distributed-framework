package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserRole extends BaseEntity{
	
	private static final long serialVersionUID = 7648418132780885394L;
	
	private Long id;
    private Long mcUserId;
    private Long mcRoleId; 
    private String remark;
    
}