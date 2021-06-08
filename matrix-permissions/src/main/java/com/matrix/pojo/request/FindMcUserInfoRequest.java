package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 2414317381118804109L;

	private McUserInfoView userCache;
	
	private Long id;
	
	private String userName;
	
	private String mobile;
	
	public McUserInfoDto buildAjaxSystemUserList() {
		McUserInfoDto dto = new McUserInfoDto();
		dto.setUserName(userName);
		dto.setMobile(mobile);
		return dto;
	}
	
	public Result<PageInfo<McUserInfoView>> validateAjaxSystemUserList() {
		if(StringUtils.isAnyBlank(userCache.getPlatform() , userCache.getCid().toString()  , userCache.getType() )) {   
			// 用户会话异常! platform cod or cid is null
			return Result.ERROR(this.getInfo(101010013), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		return Result.SUCCESS();
	}
	
	
	
	public Result<McUserInfoView> validateAjaxFindSysUser() {
		if (id == null) {// 101010030=获取用户详情失败，用户id为空
			return Result.ERROR(this.getInfo(101010030), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














