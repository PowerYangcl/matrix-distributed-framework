package com.matrix.service;

import com.matrix.pojo.entity.SysError;

public interface ISystemService {

	public String addLock(String keycode, Integer timeoutSecond, String uuid);
	
	public String unLock(String uuid);
	
	public String getUniqueCode(String codeStart);
	
	public Integer addSystemError(SysError entity);
}
