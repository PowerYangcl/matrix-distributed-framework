package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class DeleteMcRoleRequest implements Serializable{

	private static final long serialVersionUID = -5903075944968697619L;

	private McUserInfoView userCache;
	
	private Long mcRoleId;
	
}














