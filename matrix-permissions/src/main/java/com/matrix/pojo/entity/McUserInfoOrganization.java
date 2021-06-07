package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserInfoOrganization extends BaseEntity{
   
	private static final long serialVersionUID = 517936742086410180L;
	private Long id;
    private Long cid;
    private Long mcUserInfoId;
    private Long mcOrganizationId;
    private String platform;
}

















