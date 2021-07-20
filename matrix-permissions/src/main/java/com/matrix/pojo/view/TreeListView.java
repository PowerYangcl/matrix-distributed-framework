package com.matrix.pojo.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.entity.McSysFunction;

import lombok.Data;

@Data
public class TreeListView implements Serializable{
	private static final long serialVersionUID = 7270006990187541177L;

	private List<McSysFunction> list = new ArrayList<McSysFunction>();
	
	private List<McRoleCache> roles = new ArrayList<McRoleCache>();
	
}
