package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteJobGroupRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6538647396303321837L;

	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	private McUserInfoView userCache;
	
	private Long id;
	private String ip;
	private String groupName;
    private String remark;
	
    public Result<?> validateAjaxBtnJobGroupDelete() {
    	if(id == null) {	// 100020111=主键丢失 200010051=定时任务分组删除失败, 请重试!
    		return Result.ERROR(this.getInfo(100020111) + this.getInfo(200010051), ResultCode.MISSING_ARGUMENT);
    	}
    	
    	JobInfoDto dto = new JobInfoDto();
		dto.setRunGroupId(id);
		List<JobInfo> correlation = jobInfoMapper.findListByDto(dto);
		if(correlation != null && correlation.size() != 0) {
			// 200010047=定时任务分组删除失败, 已有{0}个定时任务与该分组关联，请解除
			return Result.ERROR(this.getInfo(200010047 , correlation.size()) , ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
    
	public JobGroup buildAjaxBtnJobGroupDelete() {
		JobGroup e = new JobGroup();
		e.setId(id);
		e.setDeleteFlag(0);
		e.buildUpdateCommon(userCache);
		return e;
	}
}














