package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindAcIncludeDomainListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 7289604206509437208L;

    private String domain;
    private String companyName;
    
	private McUserInfoView userCache;
	
	public AcIncludeDomain buildAjaxIncludeDomainPageList() {
		AcIncludeDomain e = new AcIncludeDomain();
		e.setDomain(domain);
		e.setCompanyName(companyName);
		return e;
	}
}
