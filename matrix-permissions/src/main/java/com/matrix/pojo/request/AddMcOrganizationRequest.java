package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

/**
 * @description: 暂时用不上，字段约束后面再说
 * 
 * @author Yangcl
 * @date 2022-4-14 20:52:36
 * @home https://github.com/PowerYangcl
 * @path matrix-permissions / com.matrix.pojo.request.AddMcOrganizationRequest.java
 * @version 1.6.0.8-validation
 */
@Data
public class AddMcOrganizationRequest implements Serializable{

	private static final long serialVersionUID = 8165766178972722923L;

	
    private Long cid;
    
    
    private String name;
    
    private Long parentId;
    
    private Integer type;			// 门店类型：1加盟，2直营|type=3则此字段必填
    
    @NotBlank(message = "101010002")		// 101010002=平台唯一标识码不得为空
    private String platform;
    
    private Long managerId;
    
    private String managerName;
    
    private Integer storeType;
    
    private Integer seqnum;
    
    private String mobile;
    
    private String address;
    
    private String remark;
    
	private McUserInfoView userCache;
	
	public McOrganization buildAddOrganizationInfo() {
		McOrganization e = new McOrganization();
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
		e.buildAddCommon(userCache);
		return e;
	}
}














