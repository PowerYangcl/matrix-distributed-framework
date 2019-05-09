package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.SysTableOpreateRecordDto;
import com.matrix.pojo.entity.SysTableOpreateRecord;
import com.matrix.pojo.view.SysTableOpreateRecordView;

public interface ISysTableOpreateRecordMapper extends IBaseDao<Long , SysTableOpreateRecord , SysTableOpreateRecordDto , SysTableOpreateRecordView>{

	public Integer deleteByMdvalue(SysTableOpreateRecord e);

	public SysTableOpreateRecord findByMdvalue(SysTableOpreateRecord e);

	public Integer updateZid(SysTableOpreateRecord e);

}
