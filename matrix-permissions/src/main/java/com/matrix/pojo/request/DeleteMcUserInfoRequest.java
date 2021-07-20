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
public class DeleteMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 9197658565913311629L;

	private McUserInfoView userCache;
	
    private Long id;
	
    
    public Result<?> validateDeleteUser() {
    	if(id == null) {		// 101010031=页面数据错误,用户id为空
    		return Result.ERROR(this.getInfo(101010031), ResultCode.MISSING_ARGUMENT);
    	}
		return Result.SUCCESS();
	}
}














