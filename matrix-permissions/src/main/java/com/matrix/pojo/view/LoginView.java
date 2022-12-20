package com.matrix.pojo.view;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoginView implements Serializable{

	private static final long serialVersionUID = 1635992313735978268L;

	private String info;

	private String pageJson;
	
	private String uploadUrl;

}
