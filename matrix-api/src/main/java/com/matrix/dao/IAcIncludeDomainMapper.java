package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.AcIncludeDomainDto;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.view.AcIncludeDomainView;

public interface IAcIncludeDomainMapper extends IBaseDao<Long ,AcIncludeDomain, AcIncludeDomainDto , AcIncludeDomainView>{

	public List<AcIncludeDomainView> queryPageList(AcIncludeDomain entity); 

}