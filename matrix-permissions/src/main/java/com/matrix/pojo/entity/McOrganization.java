package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McOrganization extends BaseEntity{
	
	private static final long serialVersionUID = -8081431101895080124L;
	private Long id;
    private Long cid;
    private String name;
    private Long parentId;
    private Integer type;
    private String platform;
    private Long managerId;
    private String managerName;
    private Integer storeType;
    private Integer seqnum;
    private String mobile;
    private String address;
    private String remark;
}

























