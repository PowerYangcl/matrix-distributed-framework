package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcRequestInfo extends BaseEntity{
	private static final long serialVersionUID = 7057314343184384965L;
	private Long id;
    private String organization;
    private String key;
    private String value;
    private String atype;
    private Integer flag;
    
}