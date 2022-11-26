package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McUserPositionDto;
import com.matrix.pojo.entity.McUserPosition;
import com.matrix.pojo.view.McUserPositionView;

public interface IMcUserPositionMapper extends IBaseDao<Long, McUserPosition, McUserPositionDto, McUserPositionView>{
}