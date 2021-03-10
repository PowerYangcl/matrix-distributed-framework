package com.matrix.quartz.support;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseJob;
import com.matrix.pojo.entity.JobInfo;


/**
 * @descriptions 定时任务的增删改功能
 *
 * @author zht 
 * @date 2016年11月24日 下午11:30:29
 * @version 1.0.1
 */
public class JobSupport extends BaseClass {

	private Scheduler scheduler = null;

	private JobSupport() {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
			this.getLogger(null).sysoutInfo(200010006, this.getClass());  // 200010006=org.quartz.Scheduler初始化异常!
		}
	}
	
	private static class LazyHolder {
        private static final JobSupport INSTANCE = new JobSupport();
    }
    public static final JobSupport getInstance() {
        return LazyHolder.INSTANCE;
    }

	/**
	 * @descriptions 添加定时任务
	 *
	 * @param jobClass 类名称
	 * @param jobTriger 定时表达式 标准quartz结构体
	 * @param jobName 任务名称 可以为空 为空则自动生成
	 * 
	 * @author zht
	 * @date 2016年11月24日 下午11:34:34
	 * @version 1.0.0.1
	 */
	public void addJob(JobInfo info) {
		try {
			if (scheduler == null) {
				SchedulerFactory sf = new StdSchedulerFactory();
				scheduler = sf.getScheduler();
				scheduler.start();
			}
			@SuppressWarnings("unchecked")
			Class<IBaseJob> jobClass = ClassUtils.getClass(info.getJobClass());
			
			// 设置作业，具体操作在SimpleJob类里
			JobDetail job = JobBuilder.newJob(jobClass).withIdentity(info.getJobName() , Scheduler.DEFAULT_GROUP).build(); 
			job.getJobDataMap().put("job_status", info);

			// 设置触发器
			Set<CronTrigger> triggerSet = new HashSet<CronTrigger>();
			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
					.withIdentity("trigger_" + info.getJobName() , Scheduler.DEFAULT_GROUP)
					.withSchedule(CronScheduleBuilder.cronSchedule( info.getJobTriger() )).build(); 
			triggerSet.add(trigger);
			
			scheduler.scheduleJob(job , triggerSet , true); // 设置调度作业
			
		} catch (SchedulerException | ClassNotFoundException e) {
			e.printStackTrace();
			this.getLogger(null).sysoutInfo(200010007 , this.getClass());  // 200010007=定时任务添加失败!
		}
	}

	/**
	 * @descriptions 删除任务
	 *
	 * @author zht
	 * @param jobName
	 * @date 2016年11月24日 下午11:37:12
	 * @version 1.0.0.1
	 */
	public boolean deleteJob(String jobName) {
		try {
			scheduler.deleteJob(JobKey.jobKey(jobName , Scheduler.DEFAULT_GROUP));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @descriptions 关闭所有定时器
	 *
	 * @author zht
	 * @date 2016年11月24日 下午11:37:34
	 * @version 1.0.0.1
	 */
	public boolean shutDown() {
		try {
			if (scheduler != null) {
				scheduler.shutdown();
				// 延迟一秒 静候所有任务停止
				Thread.sleep(1000);
				while (!scheduler.isShutdown()) {
					Thread.sleep(1000);
				}
				scheduler.shutdown(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @descriptions 开始所有任务
	 *
	 * @author zht
	 * @date 2016年11月24日 下午11:39:00
	 * @version 1.0.0.1
	 */
	public boolean start() {
		try {
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * @descriptions 暂停所有任务
	 *
	 * @author zht
	 * @date 2016年11月24日 下午11:39:00
	 * @version 1.0.0.1
	 */
	public boolean pauseAll() {
		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @descriptions 恢复所有任务
	 *
	 * @author zht
	 * @date 2016年11月24日 下午11:39:00
	 * @version 1.0.0.1
	 */
	public boolean resumeAll() {
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @descriptions 重启所有任务
	 *
	 * @author zht
	 * @date 2016年11月24日 下午11:39:00
	 * @version 1.0.0.1
	 */
	public boolean restart() {
		shutDown();
		return start();
	}

}
























