package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.TcLandedPropertyDto;
import com.matrix.pojo.entity.TcLandedProperty;
import com.matrix.pojo.view.TcLandedPropertyView;

public interface ITcLandedPropertyMapper extends IBaseDao<Long , TcLandedProperty, TcLandedPropertyDto , TcLandedPropertyView>{ 
	
}