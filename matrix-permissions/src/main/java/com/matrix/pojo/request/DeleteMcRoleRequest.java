package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteMcRoleRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -5903075944968697619L;

	private McUserInfoView userCache;
	
	@NotBlank(message = "100020103")	   // 100020103=参数缺失：{0} TODO 后续解决
	private Long mcRoleId;
	
}














