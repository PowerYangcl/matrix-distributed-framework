package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateTreeNodesRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -6092666198816213055L;

	@NotBlank(message = "100010126")		// 100010126=请求参数不允许为空
	private String ustring; // 同层节点更新功能
    
	private McUserInfoView userCache;
	
}














