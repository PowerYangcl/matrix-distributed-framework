package com.matrix.pojo.request;

import java.io.Serializable;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindLoginRequest implements Serializable{

	private static final long serialVersionUID = 7174953732796704401L;

	private McUserInfoView userCache;
	
	private String userName;
	
	private String password;
	
	private String platform;
	
}














