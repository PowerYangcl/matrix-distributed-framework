package com.matrix.base;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;
import com.matrix.aspectj.IdempotentRequest;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseApiDto extends IdempotentRequest{
	
	@NotNull(message = "600010004")   // 600010004=open-api请求头不得为空
	private ApiHead head;
	
	private JSONObject data;
	
	private McUserInfoView userCache;
}
