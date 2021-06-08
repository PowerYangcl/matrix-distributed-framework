package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserRoleDto extends BaseDto{
	
	private static final long serialVersionUID = -5683799321939616614L;
	
	private Long userId;
	private Long mcRoleId;
	
}
