package com.matrix.pojo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FindValueByKeyDto {
	@NotBlank(message = "100010126")		// 100010126=请求参数不允许为空
	private String key;
}
