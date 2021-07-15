package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IAcRequestInfoMapper;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateRequestInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 1932147821220294066L;
	private McUserInfoView userCache;
	
	private Long id;
	private Integer isallot;  // 如果是第三方请求者，标识在编辑方法中是否为分配系统开放接口。0：否|1：是
	private String targets;		// API的自增id，逗号分隔
	
    private String organization;
    private String key;
    private String value;
    private String atype;
    private Integer flag;
	
    private AcRequestInfo old; 		// 可利用的返回值。
	
	public Result<?> validate(IAcRequestInfoMapper acRequestInfoMapper){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(isallot == null) {		// 100020103=参数缺失：{0}
			Result.ERROR(this.getInfo(100020103, "isallot"), ResultCode.MISSING_ARGUMENT);
		}
		if(isallot == 1 && StringUtils.isBlank(targets)) {	// 100020103=参数缺失：{0}
			Result.ERROR(this.getInfo(100020103, "targets"), ResultCode.MISSING_ARGUMENT);
		}
		AcRequestInfo e = acRequestInfoMapper.find(id);
		if(isallot ==1 && e.getAtype().equals("private")) {	// 600010084=内部接口请求者不可分配开放接口数据(open-api)!
			Result.ERROR(this.getInfo(600010084), ResultCode.OPERATION_FAILED);
		}
		old = e;
		return Result.SUCCESS();
	}
	
	public AcRequestInfo buildAjaxRequestInfoEdit() {
		AcRequestInfo e = new AcRequestInfo();
		e.setId(id);
		e.setOrganization(organization);
		e.setKey(key);
		e.setValue(value);
		e.setAtype(atype);
		e.setFlag(flag);
		e.buildUpdateCommon(userCache);
		return e;
	}
}




















