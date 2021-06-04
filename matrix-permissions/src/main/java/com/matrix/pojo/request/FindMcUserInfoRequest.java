package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindMcUserInfoRequest implements Serializable{

	private static final long serialVersionUID = 2414317381118804109L;

	private McUserInfoView userCache;
	
	private String userName;
	
	private String mobile;
	
	public McUserInfoDto buildAjaxSystemUserList() {
		McUserInfoDto dto = new McUserInfoDto();
		dto.setUserName(userName);
		dto.setMobile(mobile);
		return dto;
	}
	
}














