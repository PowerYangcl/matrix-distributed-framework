package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.view.StoreInfoView;

public interface IStoreInfoMapper extends IBaseDao<Long,StoreInfo,StoreInfoDto,StoreInfoView> {

	public StoreInfo findByOrgId(Long orgId);

	public Integer deleteByOrgId(Long orgId);
}