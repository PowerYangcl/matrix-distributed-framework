package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateMcRoleRequest extends BaseClass implements Serializable{

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
	
	public Result<?> validateEditMcRole() {
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(roleName)){  // 101010027=角色名称不得为空
			return Result.ERROR(this.getInfo(101010027), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(oldRoleName)){  // 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103, "oldRoleName"), ResultCode.MISSING_ARGUMENT);
		}
		if(userCache.getType().equals("leader") && StringUtils.isBlank(platform)) {
			// 101010002=平台唯一标识码不得为空
			return Result.ERROR(this.getInfo(101010002), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














