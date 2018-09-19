package com.matrix.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.matrix.dao.ISysLockMapper;
import com.matrix.map.MObjMap;
import com.matrix.service.ISysLockService;

/**
 * @description: 分布式锁|锁库|准备废弃的类
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年7月26日 下午4:03:15 
 * @version 1.0.0.1
 */
@Service("lockService")
public class SysLockServiceImpl  implements ISysLockService {
	@Resource
	private ISysLockMapper sysLockMapper;

	@Override
	public String addLock(String keycode, Integer timeoutSecond, String uuid) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", keycode);
		param.put("keysplit", ",");
		param.put("timeoutsecond", timeoutSecond);
		param.put("lockflag", "1");
		param.put("uuid", uuid);
		return sysLockMapper.addLock(param);
	}
	
	@Override
	public String unLock(String uuid) {
		MObjMap<String, Object> param = new MObjMap<String, Object>();
		param.put("somekey", "");
		param.put("keysplit", ",");
		param.put("timeoutsecond", new Integer(0));
		param.put("lockflag", "2");
		param.put("uuid", uuid);
		return sysLockMapper.addLock(param);
	}
}












