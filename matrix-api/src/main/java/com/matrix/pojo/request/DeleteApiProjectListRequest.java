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
public class DeleteApiProjectListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6402609961440816319L;

	private Long id;
    
	private McUserInfoView userCache;
	
	public Result<?> validate(){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
}
