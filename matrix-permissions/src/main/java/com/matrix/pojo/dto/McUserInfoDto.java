package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McUserInfoDto extends BaseDto{
	
	private static final long serialVersionUID = 4629549271113492123L;
	
    private Long cid;
    private String type;
    private String platform;	 
    private String orgIds;
    private String userName;
    private String mobile;
    private Integer sex;
    
  


}