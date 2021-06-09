package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class JobInfo extends BaseEntity{
	private static final long serialVersionUID = 8848133833768861972L;
	private Long id;
    private String jobName;
    private String jobTitle;
    private String jobClass;
    private String jobTriger;
    private Long runGroupId;
    private Date beginTime;
    private Date endTime;
    private Date nextTime;
    private Integer pause;
    private String lockKey;
    private Integer expireTime;
    private Integer timeOut;
    private String jobList; 
    private Integer trigerType;
    private Integer logType;
    private String remark;
    
    // 目前不用的字段，但保留扩展功能
    private Integer concurrentType;
    private Integer maxExecTime;
    
    
}