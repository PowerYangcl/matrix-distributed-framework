package com.matrix.dao;

import java.util.List;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.AcApiProjectDto;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.view.AcApiProjectView;

public interface IAcApiProjectMapper extends IBaseDao<Long , AcApiProject , AcApiProjectDto, AcApiProjectView>{

	public List<AcApiProjectView> queryPageList(AcApiProject entity);

	public List<AcApiProjectView> findAll();  
	
}