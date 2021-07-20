package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.DateUtil;
import com.matrix.util.UuidUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddRequestInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6463715430498727421L;
	private McUserInfoView userCache;
	
    private String organization;
    private String atype;
	
	public Result<?> validate(){
		if(StringUtils.isAnyBlank(organization , atype)) {
			return Result.ERROR(this.getInfo(600010081), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
	public AcRequestInfo buildAjaxRequestInfoAdd() {
		AcRequestInfo e = new AcRequestInfo();
		e.setOrganization(organization);
		e.setAtype(atype);
		DateUtil dateUtil = new DateUtil();
		e.setKey(dateUtil.getDateLongHex("yyyyMMdd").toUpperCase() + dateUtil.getDateLongHex("HHmmss").toUpperCase());  
		e.setValue(UuidUtil.uid().toUpperCase());  
		e.setFlag(1); 
		e.buildAddCommon(userCache);
		return e;
	}
}




















