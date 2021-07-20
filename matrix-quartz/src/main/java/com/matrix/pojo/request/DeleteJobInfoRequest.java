package com.matrix.pojo.request;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteJobInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 3042529468554322608L;

	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	private McUserInfoView userCache;
	
    private String jobName;
	
    public Result<?> validate() {
		if(StringUtils.isBlank(jobName)){  
			// 200010033=定时任务删除失败, job-name为空, 请重试
			return Result.ERROR(this.getInfo(200010033), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
    
	public JobInfo build() {
		JobInfo e = new JobInfo();
		e.setJobName(jobName);
		e.setDeleteFlag(0);
		e.buildUpdateCommon(userCache);
		return e;
	}
}














