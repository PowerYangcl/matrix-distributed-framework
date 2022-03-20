package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
	
	@NotBlank(message = "100020111")	   // 100020111=主键丢失
	private Long id;
	
	@NotBlank(message = "101010027")	   // 101010027=角色名称不得为空
	private String roleName;
	
    private String roleDesc;
    private String platform;
    
    @NotBlank(message = "100020103")	   // 100020103=参数缺失：{0}	TODO "oldRoleName" 暂时不做处理
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
		if(userCache.getType().equals("leader") && StringUtils.isBlank(platform)) {
			// 101010002=平台唯一标识码不得为空
			return Result.ERROR(this.getInfo(101010002), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














