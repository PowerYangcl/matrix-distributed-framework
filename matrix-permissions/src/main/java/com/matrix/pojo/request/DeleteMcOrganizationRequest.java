package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class DeleteMcOrganizationRequest implements Serializable{

	private static final long serialVersionUID = -7693960655794932375L;

	private String ids;
    
	private McUserInfoView userCache;
	
}














