package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class JobExecLogDto extends BaseDto {
	private static final long serialVersionUID = -5714762865312148662L;
	private Long id;
    private String status;
    private String ip;
    private String runGroupName;
    
    private String jobName;
    private String jobTitle;
}
