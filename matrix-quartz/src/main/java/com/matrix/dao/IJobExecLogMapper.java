package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.JobExecLogDto;
import com.matrix.pojo.entity.JobExecLog;
import com.matrix.pojo.view.JobExecLogView;

public interface IJobExecLogMapper extends IBaseDao<Long, JobExecLog, JobExecLogDto, JobExecLogView>{
}