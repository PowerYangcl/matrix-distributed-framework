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
public class DeleteMcOrganizationRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -7693960655794932375L;

	private String ids;
    
	private McUserInfoView userCache;
	
	public Result<?> validate(){
		if(StringUtils.isBlank(ids)) {  // 101010002=删除失败 + 101010012=节点id不得为空!
			return Result.ERROR(this.getInfo(101010002) + "," + this.getInfo(101010012), ResultCode.ERROR_DELETE);
		}
		return Result.SUCCESS();
	}
}














