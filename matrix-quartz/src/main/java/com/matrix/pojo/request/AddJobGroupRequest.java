package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IJobGroupMapper;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddJobGroupRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 2769728427390227256L;

	@Inject
	private IJobGroupMapper jobGroupMapper;
	
	private McUserInfoView userCache;
	
	private String ip;
	private String groupName;
    private String remark;
	
    public Result<?> validateAjaxBtnJobGroupAdd() {
		if(StringUtils.isAnyBlank(groupName, ip)){  
			// 200010045=定时任务分组必填字段验证失败!弹窗中的字段均为必填项，请核查!
			return Result.ERROR(this.getInfo(200010045), ResultCode.MISSING_ARGUMENT);
		}
		
		JobGroup e = new JobGroup();
		e.setGroupName(groupName);
		List<JobGroup> ishas = jobGroupMapper.findList(e);
		if(ishas != null && ishas.size() != 0) {
			// 200010044=定时任务分组添加失败，分组名称已经存在
			return Result.ERROR(this.getInfo(200010044), ResultCode.ALREADY_EXISTS);
		}
		return Result.SUCCESS();
	}
    
	public JobGroup buildAjaxBtnJobGroupAdd() {
		JobGroup e = new JobGroup();
		e.setIp(ip);
		e.setGroupName(groupName);
		e.setRemark(remark);
		e.buildAddCommon(userCache);
		return e;
	}
}














