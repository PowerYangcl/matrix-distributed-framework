package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class UpdateMcRoleRequest implements Serializable{

	private static final long serialVersionUID = 643446152528585479L;

	private McUserInfoView userCache;
	
	private Long id;
	private String roleName;
    private String roleDesc;
    private String platform;
    private String oldRoleName;
	
	public McRole buildEditSysRole() {
		McRole e = new McRole();
		e.setId(id);
		e.setRoleName(roleName);
		e.setRoleDesc(roleDesc);
		e.buildUpdateCommon(userCache);
		return e;
	}
}














