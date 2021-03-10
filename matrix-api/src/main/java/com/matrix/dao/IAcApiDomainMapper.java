package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.AcApiDomainDto;
import com.matrix.pojo.entity.AcApiDomain;
import com.matrix.pojo.view.AcApiDomainView;

public interface IAcApiDomainMapper extends IBaseDao<Long , AcApiDomain, AcApiDomainDto , AcApiDomainView>{

	public List<AcApiDomainView> selectByApiInfoId(Long apiInfoId);

	public void deleteByApiInfoId(Long apiInfoId);   
    
}