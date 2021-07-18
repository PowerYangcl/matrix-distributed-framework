package com.matrix.pojo.cache;


import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AcApiDomainCache implements Serializable{

	private static final long serialVersionUID = 5362877368001948278L;
	private Long id;
    private String domain;
    private String companyName;
    
	public AcApiDomainCache(Long id, String domain, String companyName) {
		super();
		this.id = id;
		this.domain = domain;
		this.companyName = companyName;
	}
}
