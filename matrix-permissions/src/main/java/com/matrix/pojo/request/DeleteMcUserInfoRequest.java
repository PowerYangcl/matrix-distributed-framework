package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteMcUserInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 9197658565913311629L;

	private McUserInfoView userCache;
	
	@NotNull(message = "101010031")   // 101010031=页面数据错误,用户id为空
    private Long id;
	
}














