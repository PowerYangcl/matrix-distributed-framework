package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcApiInfoDto extends BaseDto{
	private static final long serialVersionUID = 6736877578826656729L;
	
	private Long id;
    
    private String domainList; // ac_include_domain 列表
    
}
