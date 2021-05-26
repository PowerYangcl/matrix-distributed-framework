package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class McOrganizationDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long cid;
    private String name;
    private Long parentId;
    private Integer type;
    private String platform;
    private Long managerId;
    private String managerName;
    private Integer storeType;
    private Integer seqnum;
    private String mobile;
    private String address;
}
