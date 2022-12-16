package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindUserRoleListRequest implements Serializable{

	private static final long serialVersionUID = 8895224359209429359L;

	private McUserInfoView userCache;
	
	private Long userId;
	
	@NotBlank(message = "101010002")		// 101010002=平台唯一标识码不得为空
	private String platform;							// 改为逗号分隔 update in v1.6.1.6-multiple-jspweb
	
	private String roleName;
	private Integer startIndex = 1;
	private Integer pageSize = 10;
	
	public McRoleDto buildUserRoleList() {
		McRoleDto dto = new McRoleDto();
		if(StringUtils.isNotBlank(platform)) {
			String p = "";
			String[] split = platform.split(",");
			for(String s : split) {
				p = p + "'" + s + "',";
			}
			dto.setPlatform(p.substring(0, p.length() -1));
		}
		
		dto.setRoleName(roleName);
		dto.setStartIndex(startIndex);
		dto.setPageSize(pageSize);
		dto.setUserCache(userCache);
		return dto;
	}
}














