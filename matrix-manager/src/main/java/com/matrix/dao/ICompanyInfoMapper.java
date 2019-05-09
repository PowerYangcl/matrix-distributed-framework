package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.CompanyInfoDto;
import com.matrix.pojo.entity.CompanyInfo;
import com.matrix.pojo.view.CompanyInfoView;

public interface ICompanyInfoMapper extends IBaseDao<Long,CompanyInfo,CompanyInfoDto,CompanyInfoView> {
}