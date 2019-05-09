package com.matrix.mapper;

import com.matrix.mq.DbMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IMessageMapper {

	void insert(DbMessage dbMessage);

	void delete(long id);

	void modifyState(@Param("id") long id, @Param("state") Integer state);

	List<DbMessage> getAll(int endWitchSeconds);

}
