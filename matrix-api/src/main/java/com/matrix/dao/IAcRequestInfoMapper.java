package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.AcRequestInfoDto;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.AcRequestInfoView;

public interface IAcRequestInfoMapper extends IBaseDao<Long , AcRequestInfo, AcRequestInfoDto , AcRequestInfoView>{

	public List<AcRequestInfoView> queryPageList(AcRequestInfo entity); 
	
	public AcRequestInfo findByKey(String key);
    
}