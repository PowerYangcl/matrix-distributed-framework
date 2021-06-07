package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteMcUserRoleRequest extends BaseClass implements Serializable{


	private static final long serialVersionUID = 2801099208669420489L;

	private McUserInfoView userCache;
	
    private Long userId;
    private Long mcRoleId; 
	
	public McUserRoleDto buildDeleteUserRole() {
		McUserRoleDto dto = new McUserRoleDto();
		dto.setUserId(userId);
		dto.setMcRoleId(mcRoleId);
		return dto;
	}
	
	public Result<?> validateDeleteUserRole(){
		if(userId == null || mcRoleId == null) {
			// 100010126=请求参数不允许为空
			return Result.ERROR(this.getInfo(100010126), ResultCode.MISMATCH_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














