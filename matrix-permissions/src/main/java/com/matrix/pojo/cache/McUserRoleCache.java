package com.matrix.pojo.cache;

import java.util.ArrayList;
import java.util.List;

import com.matrix.pojo.entity.McSysFunction;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserRoleCache {
	private Long mcUserId; 
	private List<McSysFunction> msfList = new ArrayList<McSysFunction>();
	
}
