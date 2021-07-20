package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteStoreInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 2644257081368116723L;

	private McUserInfoView userCache;
	
    private Long id;
	
    
    public StoreInfo buildDeleteStoreInfo() {
    	StoreInfo e = new StoreInfo();
    	e.setId(id);
    	e.setDeleteFlag(1);
    	e.buildUpdateCommon(userCache);
    	return e;
    }
    
    public Result<?> validateDeleteStoreInfo() {
    	if(id == null) {		// 100020111=主键丢失
    		return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
    	}
		return Result.SUCCESS();
	}
}














