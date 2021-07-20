package com.matrix.pojo.view;

import java.io.Serializable;
import lombok.Data;

@Data
public class ClientLoginView implements Serializable{

	private static final long serialVersionUID = 6561263865853109059L;

	private String pageJson;
	
	private String info;
	
	private String uploadUrl;
	
	private String accessToken;

}
