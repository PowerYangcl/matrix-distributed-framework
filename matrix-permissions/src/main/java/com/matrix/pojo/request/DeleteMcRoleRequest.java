package com.matrix.pojo.request;

import java.io.Serializable;


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
	
	private Long mcRoleId;
	
	public Result<?> validateDeleteMcRole() {
		if(mcRoleId == null){  // 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103, "mcRoleId"), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
}














