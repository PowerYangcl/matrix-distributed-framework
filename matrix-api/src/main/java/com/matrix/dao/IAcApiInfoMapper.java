package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.AcApiInfoView;
import com.matrix.pojo.view.ApiTreeView;

public interface IAcApiInfoMapper extends IBaseDao<Long , AcApiInfo , AcApiInfoDto , AcApiInfoView>{

	public List<ApiTreeView> findApiInfoList(AcApiInfo e); 
	
	public List<AcApiInfo> selectByEntity(AcApiInfo entity);
	
	
}