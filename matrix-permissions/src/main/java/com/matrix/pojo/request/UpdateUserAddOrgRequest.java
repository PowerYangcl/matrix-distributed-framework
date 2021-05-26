package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class UpdateUserAddOrgRequest implements Serializable{


	private static final long serialVersionUID = 1428113650410318406L;

	private String ids;
	
	private Long userId; // mc_user_info 主键
    
	private McUserInfoView userCache;
	
}














