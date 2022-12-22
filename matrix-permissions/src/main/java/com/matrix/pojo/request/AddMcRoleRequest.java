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
public class AddMcRoleRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 1165452897209143404L;

	private McUserInfoView userCache;
	
	@NotBlank(message = "101010002")		// 101010002=平台唯一标识码不得为空
	private String platform;
	
	@NotBlank(message = "101010027")		// 101010027=角色名称不得为空
    private String roleName;
	
    private String roleDesc;
	
	public McRole buildAddMcRole() {
		McRole e = new McRole();
		e.setRoleName(roleName);
		e.setRoleDesc(roleDesc);
		if(userCache.getType().equals("leader") ) {
			e.setType("leader");
		}else {
			// 其他平台系统管理员或拥有分配权限的用户创建的角色，类型固定为admin
			e.setType("admin");
			// leader后台创建的角色会传入平台码(如：133C9CB27E18)|其他平台则默认使用用户所在平台的数据
//			e.setPlatform(userCache.getPlatform());
		}
		e.setPlatform(platform);
		e.buildAddCommon(userCache);
		return e;
	}
	
    public Result<?> validate() {
    	if(userCache.getType().equals("leader") && StringUtils.isBlank(platform)) {	// 100020103=参数缺失：{0}
    		return Result.ERROR(this.getInfo(100020103, "platform"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(StringUtils.isNotBlank(platform) && !StringUtils.contains(userCache.getPlatform(), this.platform)) {
    		// 101010028=您没有此平台的创建权限：{0}
    		return Result.ERROR(this.getInfo(101010028, this.platform), ResultCode.ILLEGAL_ARGUMENT);
    	}
    	
		return Result.SUCCESS();
	}
}














