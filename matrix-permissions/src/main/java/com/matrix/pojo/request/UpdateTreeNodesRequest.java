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
public class UpdateTreeNodesRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = -6092666198816213055L;

	private String ustring; // 同层节点更新功能
    
	private McUserInfoView userCache;
	
	public Result<?> validate(){
		if(StringUtils.isBlank(ustring)) {   // 100020103=参数缺失：{0}
			return Result.ERROR(this.getInfo(100020103,  "ustring") , ResultCode.OPERATION_FAILED);
		}
		return Result.SUCCESS();
	}
}














