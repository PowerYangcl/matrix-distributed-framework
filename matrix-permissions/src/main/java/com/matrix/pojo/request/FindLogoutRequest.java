package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindLogoutRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 1849248942509567269L;
	
	private McUserInfoView userCache;
	
	@NotBlank(message = "101010014")		// 101010014=用户令牌(accessToken)为空
	private String accessToken;
}














