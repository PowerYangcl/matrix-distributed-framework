package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.entity.McOrganization;
import com.matrix.pojo.view.McOrganizationView;

import java.util.List;

public interface IMcOrganizationMapper extends IBaseDao<Long, McOrganization, McOrganizationDto, McOrganizationView>{

    List<McOrganizationView> findListByParentId(McOrganizationDto views);
}