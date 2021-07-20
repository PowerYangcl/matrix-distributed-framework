package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteAcIncludeDomainRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 7534948719333228787L;
	private Long id;
	private McUserInfoView userCache;
	
	public Result<?> validate(){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
	public AcIncludeDomain buildAjaxBtnAcIncludeDomainDelete() {
		AcIncludeDomain e = new AcIncludeDomain();
		e.setId(id);
		e.setDeleteFlag(0);
		e.buildUpdateCommon(userCache);
		return e;
	}
}
