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
public class UpdateJobGroupRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6538647396303321837L;

	@Inject
	private IJobGroupMapper jobGroupMapper;
	
	private McUserInfoView userCache;
	
	private Long id;
	private String ip;
	private String groupName;
    private String remark;
	
    public Result<?> validateAjaxBtnJobGroupEdit() {
    	if(id == null) {	// 200010024=信息更新失败，主键丢失
    		return Result.ERROR(this.getInfo(200010024), ResultCode.MISSING_ARGUMENT);
    	}
		if(StringUtils.isAnyBlank(groupName, ip)){  
			// 200010045=定时任务分组必填字段验证失败!弹窗中的字段均为必填项，请核查!
			return Result.ERROR(this.getInfo(200010045), ResultCode.MISSING_ARGUMENT);
		}
		
		JobGroup find = jobGroupMapper.find(id);
		if(find == null) {
			// 100020115=字段排重验证异常，请重试
			return Result.ERROR(this.getInfo(100020115), ResultCode.NOT_FOUND);
		}
		if(!find.getGroupName().equals(groupName)) {
			JobGroup e = new JobGroup();
			e.setGroupName(groupName);
			List<JobGroup> ishas = jobGroupMapper.findList(e);
			if(ishas != null && ishas.size() != 0) {
				// 200010046=定时任务分组修改失败, 分组名称已经存在
				return Result.ERROR(this.getInfo(200010044), ResultCode.ALREADY_EXISTS);
			}
		}
		return Result.SUCCESS();
	}
    
	public JobGroup buildAjaxBtnJobGroupEdit() {
		JobGroup e = new JobGroup();
		e.setId(id);
		e.setIp(ip);
		e.setGroupName(groupName);
		e.setRemark(remark);
		e.buildUpdateCommon(userCache);
		return e;
	}
}














