package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcApiDomainDto extends BaseDto{
	private static final long serialVersionUID = 6363122362845818357L;
	private Long id;
    private Long acApiInfoId;
    private Long acIncludeDomainId;
}



































