package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class DeleteMcSysFunctionRequest implements Serializable{

	private static final long serialVersionUID = -6035709960466581742L;

	private McUserInfoView userCache;
	
	// 删除一个系统功能节点及其子节点，用于保存节点主键
	private String ids;             
	
    public List<Long> buildDeleteNode(){
    	String [] arr = ids.split(",");
    	List<Long> list = new ArrayList<Long>(arr.length);
    	for(String s : arr){
			list.add(Long.valueOf(s));
		}
    	return list;
    }
}















































