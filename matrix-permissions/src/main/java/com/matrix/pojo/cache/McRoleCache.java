package com.matrix.pojo.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McRoleCache {
	private Long mcRoleId;
	private String type;
	private String platform;
	private String roleName;
	private String roleDesc;
	private String ids;
	
}
