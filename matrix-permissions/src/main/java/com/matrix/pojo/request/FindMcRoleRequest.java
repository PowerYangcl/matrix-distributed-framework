package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McRoleView;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindMcRoleRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -6092666198816213055L;

	private McUserInfoView userCache;
	
	private Long id;
	
	private String roleName;	// 模糊查询
	
	public Result<PageInfo<McRoleView>> validateAjaxSystemRoleList() {
		if(StringUtils.isAnyBlank(userCache.getPlatform() , userCache.getCid().toString() , userCache.getType())) {   
			// 用户会话异常! platform cod or cid is null
			return Result.ERROR(this.getInfo(101010013), ResultCode.INVALID_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
	public McRoleDto buildAjaxSystemRoleList() {
		McRoleDto dto = new McRoleDto();
		dto.setRoleName(roleName);
		return dto;
	}
	
	public McRole buildAjaxFindRoleInfo() {
		McRole e = new McRole();
		e.setId(id);
		return e;
	}
}














