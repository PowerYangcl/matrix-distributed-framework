package com.matrix.pojo.cache;

import java.util.ArrayList;
import java.util.List;

import com.matrix.pojo.entity.McSysFunction;

public class McUserRoleCache {
	private Long mcUserId; 
	private List<McSysFunction> msfList = new ArrayList<McSysFunction>();
	
	public Long getMcUserId() {
		return mcUserId;
	}
	public void setMcUserId(Long mcUserId) {
		this.mcUserId = mcUserId;
	}
	public List<McSysFunction> getMsfList() {
		return msfList;
	}
	public void setMsfList(List<McSysFunction> msfList) {
		this.msfList = msfList;
	}
}
