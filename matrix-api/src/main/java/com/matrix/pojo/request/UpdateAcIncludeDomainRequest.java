package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateAcIncludeDomainRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6443281296084711495L;
	private Long id;
    private String domain;
    private String companyName;
    private String project;
    private Integer flag;
    private String remark;
	private McUserInfoView userCache;
	
	public Result<?> validate(){
		if(id == null){ 	// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(domain)) {
			// 600010066=域名不得为空!
			return Result.ERROR(this.getInfo(600010066), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(companyName)) {
			// 600010067=所属公司不得为空!
			return Result.ERROR(this.getInfo(600010067), ResultCode.MISSING_ARGUMENT);
		}
		return Result.SUCCESS();
	}
	
	public AcIncludeDomain buildAjaxBtnAcIncludeDomainEdit() {
		AcIncludeDomain e = new AcIncludeDomain();
		e.setId(id);
		e.setDomain(domain);
		e.setCompanyName(companyName);
		e.setProject(project);
		e.setFlag(flag);
		e.setRemark(remark);
		e.buildUpdateCommon(userCache);
		return e;
	}
}
