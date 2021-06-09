package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class JobInfoDto extends BaseDto{
	
	private static final long serialVersionUID = 2185381367306448191L;
	private Long id;
    private String jobName;
    private String jobTitle;
    private String jobClass;
    private Integer trigerType;
    private Long runGroupId;
    
    
    private Integer pause;

    private String pauseType;  // one 暂停一个定时任务  all 暂停所有定时任务
    
    private String ips;  // ip字符串，逗号分隔
    
    private String guardType; // JobExecGuard所触发的操作类型|triger：手动触发；add：添加一个定时任务到轮询器
    
}
