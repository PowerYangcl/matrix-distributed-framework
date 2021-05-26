package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class UpdateMcOrganizationRequest implements Serializable{

	private static final long serialVersionUID = 5906142417951173008L;
	private Long id;
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
    private String remark;
    
	private McUserInfoView userCache;
	
	public McOrganization buildUpdateOrganizationInfo() {
		McOrganization e = new McOrganization();
		e.setId(id);
		e.setCid(userCache.getCid());
		e.setName(name);
		e.setParentId(parentId);
		e.setType(type);
		e.setPlatform(platform);
		e.setManagerId(managerId);
		e.setManagerName(managerName);
		e.setStoreType(storeType);
		e.setSeqnum(seqnum);
		e.setMobile(mobile);
		e.setAddress(address);
		e.setRemark(remark);
		e.buildUpdateCommon(userCache);
		return e;
	}
}














