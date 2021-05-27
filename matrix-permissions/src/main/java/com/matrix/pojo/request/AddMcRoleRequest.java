package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class AddMcRoleRequest implements Serializable{

	private static final long serialVersionUID = 1165452897209143404L;

	private McUserInfoView userCache;
	
	private String platform;	// 非Leader平台用户创建角色不得携带平台编码
    private String roleName;
    private String roleDesc;
	
	public McRole buildAddMcRole() {
		McRole e = new McRole();
		e.setPlatform(platform);
		e.setRoleName(roleName);
		e.setRoleDesc(roleDesc);
		e.setUserCache(userCache);
		return e;
	}
}














