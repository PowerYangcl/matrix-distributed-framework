package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddMcUserRoleRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -2492719286454481964L;

	private McUserInfoView userCache;
	
	@NotBlank(message = "100010126")	   // 100010126=请求参数不允许为空
    private Long mcUserId;
	
	@NotBlank(message = "100010126")	   // 100010126=请求参数不允许为空
    private Long mcRoleId; 
	
	public McUserRole buildAllotUserRole() {
		McUserRole e = new McUserRole();
		e.setMcRoleId(mcRoleId);
		e.setMcUserId(mcUserId);
		e.buildAddCommon(userCache);
		return e;
	}
	
}














