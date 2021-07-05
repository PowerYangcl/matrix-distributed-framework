package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindApiProjectListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 8800745158048157336L;

	private String target;
    
	private McUserInfoView userCache;
	
	public AcApiProject buildAjaxApiProjectList() {
		AcApiProject e = new AcApiProject();
		e.setTarget(target);
		return e;
	}
}


