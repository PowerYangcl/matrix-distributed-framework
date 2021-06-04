package com.matrix.pojo.view;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoginView implements Serializable{

	private static final long serialVersionUID = -8690053213886245890L;

	private String pageJson;
	
	private String info;
	
	private String uploadUrl;

}
