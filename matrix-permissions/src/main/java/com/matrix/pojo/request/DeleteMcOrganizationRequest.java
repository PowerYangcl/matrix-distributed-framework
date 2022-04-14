package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteMcOrganizationRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -7693960655794932375L;

	@NotBlank(message = "101010064")		// 101010064=删除失败，节点id不得为空!
	private String ids;
    
	private McUserInfoView userCache;
	
}














