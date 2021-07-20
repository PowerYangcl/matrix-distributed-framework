package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateUserAddOrgRequest extends BaseClass implements Serializable{


	private static final long serialVersionUID = 1428113650410318406L;

	private String ids;
	
	private Long userId; // mc_user_info 主键
    
	private McUserInfoView userCache;
	
	public Result<?> validate(){
		if(StringUtils.isBlank(ids)) {   // 101010039=用户数据权限已经解除
			return Result.ERROR(this.getInfo(101010039), ResultCode.OPERATION_FAILED);
		}
		return Result.SUCCESS();
	}
}














