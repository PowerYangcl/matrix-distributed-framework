package com.matrix.pojo.view;

import com.matrix.base.BaseView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McRoleView  extends BaseView{
	
	private static final long serialVersionUID = -7615697912055073094L;
	
	private Long id;
	private Long cid;
	private String type;				// leader Leader后台创建的角色|admin 其他平台管理员创建的角色
	private String platform;
    private String roleName;
    private String roleDesc; 
    private String remark;
	
	private Long userId = -1L;  // 一个标识，默认为-1
	private String platformName; // mc_sys_function表的name字段，此时nav_type=0
	
}
