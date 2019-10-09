package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class McRoleFunctionView extends BaseView {

	private static final long serialVersionUID = -6150256492091550718L;

	private Long id;
    private Long mcRoleId;
    private Long mcSysFunctionId; 
    private String remark;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMcRoleId() {
		return mcRoleId;
	}
	public void setMcRoleId(Long mcRoleId) {
		this.mcRoleId = mcRoleId;
	}
	public Long getMcSysFunctionId() {
		return mcSysFunctionId;
	}
	public void setMcSysFunctionId(Long mcSysFunctionId) {
		this.mcSysFunctionId = mcSysFunctionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}
