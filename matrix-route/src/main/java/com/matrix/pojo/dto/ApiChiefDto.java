package com.matrix.pojo.dto;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class ApiChiefDto {
	private List<JSONObject> list;

	public List<JSONObject> getList() {
		return list;
	}
	public void setList(List<JSONObject> list) {
		this.list = list;
	}

}
