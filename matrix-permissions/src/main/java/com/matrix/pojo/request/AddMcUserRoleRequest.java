package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class AddMcUserRoleRequest implements Serializable{

	private static final long serialVersionUID = -2492719286454481964L;

	private McUserInfoView userCache;
	
    private Long mcUserId;
    private Long mcRoleId; 
	
	public McUserRole buildAllotUserRole() {
		McUserRole e = new McUserRole();
		e.setMcRoleId(mcRoleId);
		e.setMcUserId(mcUserId);
		e.buildAddCommon(userCache);
		return e;
	}
}














