package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.matrix.base.BaseClass;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteMcUserRoleRequest extends BaseClass implements Serializable{


	private static final long serialVersionUID = 2801099208669420489L;

	private McUserInfoView userCache;
	
	@NotNull(message = "100010126")			// 100010126=请求参数不允许为空
    private Long userId;
	
	@NotNull(message = "100010126")			// 100010126=请求参数不允许为空
    private Long mcRoleId; 
	
	public McUserRoleDto buildDeleteUserRole() {
		McUserRoleDto dto = new McUserRoleDto();
		dto.setUserId(userId);
		dto.setMcRoleId(mcRoleId);
		return dto;
	}
}














