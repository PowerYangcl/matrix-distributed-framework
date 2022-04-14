package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateUserAddOrgRequest extends BaseClass implements Serializable{


	private static final long serialVersionUID = 1428113650410318406L;

	@NotBlank(message = "101010066")		// 101010066=用户数据权限ID不得为空
	private String ids;
	
	@NotBlank(message = "101010065")		// 101010065=用户ID丢失：userId
	private Long userId; // mc_user_info 主键
    
	private McUserInfoView userCache;
	
}














