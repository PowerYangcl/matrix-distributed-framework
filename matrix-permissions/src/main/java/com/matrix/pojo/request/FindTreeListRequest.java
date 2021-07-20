package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindTreeListRequest implements Serializable{

	private static final long serialVersionUID = 5240425040265804380L;
	
	private McUserInfoView userCache;
	
	private String platform;		// 主Sql查询
	
	private String type;		// 配合查询
	
	private Long roleId;   // 配合查询
	
	
	public McSysFunctionDto buildTreeList() {
		McSysFunctionDto dto = new McSysFunctionDto();
		dto.setPlatform(platform);
		dto.setType(type);
		dto.setRoleId(roleId);
		return dto;
	}
}














