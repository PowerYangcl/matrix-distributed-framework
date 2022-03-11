package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.base.BaseClass;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeleteApiProjectListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6402609961440816319L;

	@NotBlank(message = "100020111")			// 100020111=主键丢失
	private Long id;
    
	private McUserInfoView userCache;
	
}
