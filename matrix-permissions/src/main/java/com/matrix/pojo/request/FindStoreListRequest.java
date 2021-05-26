package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindStoreListRequest implements Serializable{

	private static final long serialVersionUID = -6092666198816213055L;

	private McUserInfoView userCache;
	
}














