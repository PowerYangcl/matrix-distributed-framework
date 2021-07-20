package com.matrix.pojo.request;

import java.io.Serializable;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindJobGroupRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 4260193563920773946L;

	private McUserInfoView userCache;
	
	private Long id;
	
    public Result<JobGroup> validateAjaxJobGroupDetail() {
		if(id == null){  
			// 200010048=定时任务分组详情获取失败, 主键丢失, 请重试!
			return Result.ERROR(this.getInfo(200010048), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
    
}














