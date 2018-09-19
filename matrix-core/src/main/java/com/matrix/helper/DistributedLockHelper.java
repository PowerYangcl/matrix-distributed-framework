package com.matrix.helper;

import java.util.Date;
import java.util.UUID;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.pojo.entity.SysError;
import com.matrix.service.ISystemService;


/**
 * @description: 分布式锁控制器 
 * @example:
 		String lockCode = "";
		try {
			lockCode = DistributedLockHelper.getInstance().addLock(10 , "JobForTestOne");	// 分布式锁定
			// TODO you code 
		}catch (Exception e) {
			DistributedLockHelper.getInstance().unLock(lockCode);
			e.printStackTrace();
		}
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月6日 下午5:59:06 
 * @version 1.0.0
 */
public class DistributedLockHelper extends BaseClass {

	@Inject
	private ISystemService systemService;

	private DistributedLockHelper() {
	}
	private static class LazyHolder {
		private static final DistributedLockHelper INSTANCE = new DistributedLockHelper();
	}
	public static final DistributedLockHelper getInstance() {
		return LazyHolder.INSTANCE; 
	}

	/**
	 * zw_webcode -> sys_webcode #SystemService#
	 * 
	 * alias upCode 获取唯一编号
	 * 
	 * @param sCodeStart
	 * @return
	 */
	public String genUniqueCode(String sCodeStart) {
		return systemService.getUniqueCode(sCodeStart);
	}

	/**
	 * alias upUuid 获取uuid
	 * 
	 * @return
	 */
	public String genUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 加锁 该方法只能锁定一个编号
	 * 
	 * @param keys 
	 * @param lifeSeconds 锁的生效时间单位为秒。
	 * @return
	 */
	public String addLock(String keys, int lifeSeconds) {
		return addLock(lifeSeconds, keys);
	}

	/**
	 * 锁定某些编号 防止并发处理 <br/>
	 * 返回锁定的唯一编号 如果返回的是非空表示锁定成功 在业务逻辑执行完成后调用unlock解锁<br/>
	 * 返回如果是空需要自行处理失败的消息
	 * 
	 * @param lifeSeconds 设置这个锁的生效时间，单位为秒
	 * @param keys
	 * @return 返回非空表示锁定成功 否则表示锁定失败
	 */
	public String addLock(int lifeSeconds, String... keys) {
		String sReturn = "";
		String sUid = genUuid();
		boolean bFlagLocked = true;

		for (String sKey : keys) {
			if (bFlagLocked) {
				try {
					String outFlag = systemService.addLock(sKey, lifeSeconds, sUid);
					if (!outFlag.equals("1")) {
						bFlagLocked = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					bFlagLocked = false;
				}
			}
		}

		if (bFlagLocked) {
			sReturn = sUid;
		}
		return sReturn;
	}

	/**
	 * 解锁
	 * 
	 * @param uuid  要解锁的uuid
	 * @return
	 */
	public String unLock(String uuid) {
		try {
			String outFlag = systemService.unLock(uuid);

			if (outFlag.equals("1"))
				return uuid;
			else
				return "";
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 该操作函数为预留函数 输出性Url统一走该操作 防止以后替换
	 * 
	 * @param sUrl
	 * @return
	 */
	public String checkUrl(String sUrl) {
		return sUrl;
	}



	/**
	 * 错误信息类
	 * 
	 * @param sCode  错误编号
	 * @param sErrorType 错误类型
	 * @param iErrorLevel 错误级别 数字越大越严重 默认写0
	 * @param sErrorSource 错误来源 一般传过来类名吧
	 * @param sMessage 错误内容
	 * @param e 可传null 如果不为null 则printStackTrace
	 */
	public void errorMessage(String sCode, String sErrorType, int iErrorLevel, String sErrorSource, String sMessage,
			Exception e) {
		try {
			if (e != null) {
				sMessage = sMessage + " ##### WebHelper.java ####" + e.getMessage();
				e.printStackTrace();
			}

			SysError entity = new SysError();
			entity.setUid(genUuid());
			entity.setErrorCode(sCode);
			entity.setErrorType(sErrorType);
			entity.setErrorLevel(String.valueOf(iErrorLevel));
			entity.setErrorSource(sErrorSource);
			entity.setErrorInfo(sMessage);
			entity.setCreateTime(new Date());
			systemService.addSystemError(entity);

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

}
