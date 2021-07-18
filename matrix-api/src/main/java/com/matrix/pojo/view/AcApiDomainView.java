package com.matrix.pojo.view;

import com.matrix.base.BaseView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcApiDomainView extends BaseView{
	
	private static final long serialVersionUID = 6232049431896373817L;
	private Long id;
    private String domain;
    private String companyName;
}




























