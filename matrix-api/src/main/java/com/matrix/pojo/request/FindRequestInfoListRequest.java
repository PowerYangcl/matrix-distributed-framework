package com.matrix.pojo.request;

import java.io.Serializable;
import com.matrix.base.BaseClass;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindRequestInfoListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6167831064779815765L;

	private IBaseLaunch<ICacheFactory> launch;
	
	private McUserInfoView userCache;
	
    private String organization;
    private String key;
	
	
	public AcRequestInfo build() {
		AcRequestInfo e = new AcRequestInfo();
		e.setOrganization(organization);
		e.setKey(key);
		return e;
	}
}




















