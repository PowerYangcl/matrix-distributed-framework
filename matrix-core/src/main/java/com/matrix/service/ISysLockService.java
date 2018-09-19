package com.matrix.service;

import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.entity.SysLock;

public interface ISysLockService {
	
	/**
	 * 
	 * @param keycode
	 * @param timeoutSecond
	 * @param uuid
	 * @return
	 */
	public String addLock(String keycode, Integer timeoutSecond, String uuid);
	
	public String unLock(String uuid);
	
}
