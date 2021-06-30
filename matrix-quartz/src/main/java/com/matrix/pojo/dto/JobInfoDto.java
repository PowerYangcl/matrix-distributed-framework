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
    
    
    
}
