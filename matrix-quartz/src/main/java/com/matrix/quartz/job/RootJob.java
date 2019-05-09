package com.matrix.quartz.job;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseJob;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.service.IJobService;
import com.matrix.support.DistributeLockRedis;
import com.matrix.util.ExceptionUtils;

/**
 * @descriptions 定时任务顶层实现类|使用方式请参考
 * 						matrix-example\src\main\java\com\matrix\quartz\job\JobForTestOne.java
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月24日 下午11:27:08
 * @version 1.0.1
 */
public abstract class RootJob extends BaseClass implements Job, IBaseJob {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	public IJobService jobService;
	private JobInfo jobInfo = null;  
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date beginTime = new Date();
		Date nextTime = null;
		Date endTime = null;

		if (context != null && context.getMergedJobDataMap() != null && context.getMergedJobDataMap().containsKey("job_status")){
			if (context.getNextFireTime() != null) {
				nextTime = context.getNextFireTime(); 
			}
			jobInfo = (JobInfo) context.getMergedJobDataMap().get("job_status");
		}
		String job = launch.loadDictCache(DCacheEnum.SysJob , "InitSysJob").get(jobInfo.getJobName()); 
		if(StringUtils.isBlank(job)) {		// 定时任务已经被删除 或 数据库连接出现问题
			return;
		}
		jobInfo = JSONObject.parseObject(job, JobInfo.class);		// 取出缓存中最新的状态，Scheduler是老的数据，不是最新的
		if(jobInfo.getPause() == 1) { // 判断定时任务是否已经暂停 | 定时任务是否暂停 0否|1是
			return;
		}
		
		Boolean execute = false;  // 这条定时任务是否在当前IP服务器中执行，默认不执行
		JSONObject job_ = JSONObject.parseObject(job);
		String[] ips = job_.getString("ip").split(",");
		for(String ip_ : ips) {
			if(this.getConfig("matrix-core.host_ip").equals(ip_)){		// 根据ip地址过滤定时任务
				execute = true;
				break;
			}
		}
		if(!execute) {
			return;
		}
		
		JSONObject result = new JSONObject();
		result.put("status", "success"); 
		if(jobInfo.getConcurrentType()  == 0) {		// 是否允许并行启动|0不允许 1允许
			DistributeLockRedis lock = new DistributeLockRedis(jobInfo.getLockKey() , jobInfo.getExpireTime() , jobInfo.getTimeOut());
			try {
				if (lock.tryLock()) {
					this.getLogger(null).sysoutInfo(200010011, this.getClass() ,  "【" +jobInfo.getJobTitle() + "|" + jobInfo.getJobName()+ "】");  // 200010011={0}分布式锁已获取
					result = this.doExecute(context);  // context可以为空
					this.getLogger(null).sysoutInfo(200010012, this.getClass() , "【" +jobInfo.getJobTitle() + "|" + jobInfo.getJobName()+ "】");  // 200010012={0}定时任务执行完成
				} else {
					this.getLogger(null).sysoutInfo(200010008, this.getClass());  // 200010008=定时任务没能获取分布式锁
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.getLogger(null).sysoutInfo(200010009, this.getClass()); // 200010009=定时任务执行出现异常!
				result.put("status", "exception");  // 标记为定时任务执行异常
				result.put("msg", ExceptionUtils.getExceptionInfo(e));
			} finally {
				lock.unlock();
			}
		}else {
			result = this.doExecute(context);  // context可以为空
			this.getLogger(null).sysoutInfo(200010013, this.getClass() , "【" +jobInfo.getJobTitle() + "|" + jobInfo.getJobName()+ "】");  // 200010013={0}并发性定时任务执行完成
		}
		
		
		endTime = new Date();
		if (StringUtils.isNotBlank(jobInfo.getJobName())) {  // 开始更新下次的执行时间
			JobInfo entity = new JobInfo();
			entity.setJobName(jobInfo.getJobName());
			entity.setBeginTime(beginTime);
			entity.setEndTime(endTime);
			entity.setNextTime(nextTime);
			entity.setUpdateTime(new Date());
			entity.setUpdateUserName("system-job");
			jobService.updateSelectiveByJobName(entity);
		}
		if(jobInfo.getLogType() == 2) {																		// TODO 记录日志，需要调用一个开发接口来插入数据，否则dubbo服务中的日志无法记录
			result.put("jobInfo", jobInfo);
			JobLog jobLog = new JobLog(result);
			Thread thread = new Thread(jobLog);
			thread.start();
		}
		
		if(StringUtils.isNotBlank(jobInfo.getJobList())) {		// 触发其他定时任务
			String [] arr = jobInfo.getJobList().split(",");  // job_name 数组
			for(String s : arr) {
				String jobjson = launch.loadDictCache(DCacheEnum.SysJob , "InitSysJob").get(s); 
    			if(StringUtils.isNotBlank(jobjson)) {
    				TrigerJob triger = new TrigerJob(jobjson);
    				triger.start();
    			}
			}
		}
	}

	public JobInfo getJobInfo() {
		return jobInfo;
	}
	public void setJobInfo(JobInfo trigerJobInfo) {
		this.jobInfo = trigerJobInfo;
	}
}



















