package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.AcRequestOpenApiDto;
import com.matrix.pojo.entity.AcRequestOpenApi;
import com.matrix.pojo.view.AcRequestOpenApiView;

public interface IAcRequestOpenApiMapper extends IBaseDao<Long , AcRequestOpenApi , AcRequestOpenApiDto , AcRequestOpenApiView> {

	public List<AcRequestOpenApiView> findListById(Long acRequestInfoId); 
}
