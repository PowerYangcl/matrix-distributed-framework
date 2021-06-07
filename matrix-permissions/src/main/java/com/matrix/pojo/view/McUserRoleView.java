package com.matrix.pojo.view;

import com.matrix.base.BaseView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserRoleView extends BaseView{
	
	private static final long serialVersionUID = 824016156470367214L;
	private Long id;   // managercenter.`mc_user_role` id
	private Long userId;
	private Long roleId;
	private String userName;
	private String roleName;
	private String roleDesc;
	
}
