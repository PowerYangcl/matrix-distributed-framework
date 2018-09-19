package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.SysErrorDto;
import com.matrix.pojo.entity.SysError;
import com.matrix.pojo.view.SysErrorView;

public interface ISysErrorMapper extends IBaseDao<Long , SysError , SysErrorDto , SysErrorView> {

}
