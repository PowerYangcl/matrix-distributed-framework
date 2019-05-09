package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.JobGroupDto;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.view.JobGroupView;

public interface IJobGroupMapper extends IBaseDao<Long, JobGroup, JobGroupDto, JobGroupView>{
}