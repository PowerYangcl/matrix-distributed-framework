package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class AcRequestInfoDto extends BaseDto {
	
	private static final long serialVersionUID = 169833910879365053L;
	private Long id;
    private String organization;
    private String key;
    private String value;
    private String atype;
    private Integer flag;
	private Integer isallot;  // 如果是第三方请求者，标识在编辑方法中是否为分配系统开放接口。0：否|1：是
	private String targets;		// API的自增id，逗号分隔

}









































