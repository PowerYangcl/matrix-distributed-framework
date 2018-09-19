package com.matrix.quartz.job;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

//import com.matrix.helper.DistributedLockHelper;


/**
 * @description: 这是第一个定时任务的测试类，用于验证定时任务模块是否稳定
 * 
 * @rglist matrix-quartz-test
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月28日 下午2:16:04 
 * @version 1.0.0
 */
public class JobForTestOne {		// extends RootJob 

	private static Logger logger = Logger.getLogger(JobForTestOne.class);
	
//	public void doExecute(JobExecutionContext context) {
//		String lockCode = "";
//		try {
//			lockCode = DistributedLockHelper.getInstance().addLock(10 , "JobForTestOne");	// 分布式锁定
//			if (StringUtils.isNotBlank(lockCode)){
//				String rglist = "***************** 所属任务组：matrix-quartz-test";
//				this.getLogger(logger).logInfo(999990001 , "@ JobForTestOne.java is running" , rglist); 
//			}else{
//				this.getLogger(logger).logInfo(999990002, "【JobForTestOne】");   
//			}
//		}catch (Exception e) {
//			DistributedLockHelper.getInstance().unLock(lockCode);
//			e.printStackTrace();
//		}
//	}

}
