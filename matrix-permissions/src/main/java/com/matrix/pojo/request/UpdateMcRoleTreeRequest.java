package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateMcRoleTreeRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 643446152528585479L;

	private McUserInfoView userCache;
	
	private String ids;
	
	private Long mcRoleId;
	
	public McRole buildEditMcRole() {
		McRole role = new McRole();
		role.setId(mcRoleId); 
		role.buildUpdateCommon(userCache);
		return role;
	}
	
	public Result<McRoleCache> validateEditMcRole() {
		if(mcRoleId == null){  // 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103, "mcRoleId"), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(ids)){ // 101010003=请勾选系统功能
			return Result.ERROR(this.getInfo(101010003), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS(new McRoleCache());
	}
	
	public Result<?> validateRelieveMcRole() {
		if(mcRoleId == null){  // 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103, "mcRoleId"), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














