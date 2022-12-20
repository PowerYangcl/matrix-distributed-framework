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
	private String platform;		// 多平台唯一标识 v1.6.1.6-multiple-jspweb
	private List<McSysFunction> msfList = new ArrayList<McSysFunction>();
	
}
