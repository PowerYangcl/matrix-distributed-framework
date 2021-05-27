package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class UpdateMcRoleTreeRequest implements Serializable{

	private static final long serialVersionUID = 643446152528585479L;

	private McUserInfoView userCache;
	
	private String ids;
	
	private Long mcRoleId;
}














