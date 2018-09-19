package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.ApiExampleDto;
import com.matrix.pojo.dto.UserDemoDto;
import com.matrix.pojo.entity.UserDemo;
import com.matrix.pojo.view.UserDemoView;

public interface IUserDemoMapper extends IBaseDao<Long , UserDemo, UserDemoDto , UserDemoView>{

	public List<UserDemo> findEntityByApiDto(ApiExampleDto dto); 

}
