package com.matrix.base;

import javax.validation.Valid;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;

@Data
public class BaseApiDto<T> {
	
	@Valid
	private ApiHead head;
	
	private T data;
	
	private McUserInfoView userCache;
}
