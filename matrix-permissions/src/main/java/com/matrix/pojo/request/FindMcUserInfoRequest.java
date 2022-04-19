package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 2414317381118804109L;

	private McUserInfoView userCache;
	
	@NotNull(message = "101010030") // 101010030=获取用户详情失败，用户id为空
	private Long id;
	
}














