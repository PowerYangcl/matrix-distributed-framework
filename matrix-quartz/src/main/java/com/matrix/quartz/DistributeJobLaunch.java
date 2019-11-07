package com.matrix.quartz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseInit;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.quartz.support.JobSupport;
import com.matrix.service.IJobService;
import com.matrix.system.cache.PowerCache;

/**
 * @description: 分布式定时任务实例化。定时任务区分运行组，运行组包含服务器列表信息。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月1日 下午12:40:49 
 * @version 1.0.0.1
 */
public class DistributeJobLaunch extends BaseInit{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IJobService jobService;
	
	
	/**
	 * @description: 初始化定时任务|根据定时任务项目所部署的服务器IP地址段不同，有选择行的根据
	 * 	ip地址来进行定时任务的初始化工作。
	 *
	 * @author Yangcl
	 * @date 2018年9月1日 下午12:56:01 
	 * @version 1.0.0.1
	 */
	public boolean onInit() {
		boolean flag = true;
		if(!this.getConfig("matrix-web.model").equals("job-system")) {  // job-system|web-system|mq-system
			this.getLogger(null).sysoutInfo(200010010, this.getClass());  // 200010010=系统定时任务已关闭
			return true;
		}
		if(!this.getConfig("matrix-quartz.job_init").equals("true")) {
			this.getLogger(null).sysoutInfo(200010010, this.getClass());  // 200010010=系统定时任务已关闭
			return true;
		}
		
        try {
        	PowerCache.getInstance().compelPut("PropConfig", "matrix-core.guard_job_exec", "com.matrix.security.JobExecGuard");
        	List<JobInfo> list = new ArrayList<JobInfo>();  
        	List<JobInfo> list_ = jobService.findJobInfoList(null);
        	if(list_ == null || list_.size() == 0) {
        		return true;
        	}
        	for(JobInfo s : list_ ){ 
        		String jobjson = launch.loadDictCache(DCacheEnum.SysJob , "InitSysJob").get(s.getJobName()); 
    			if(StringUtils.isNotBlank(jobjson) && s.getTrigerType() == 1){	// Scheduler中轮询状态的任务
    				list.add(s);
    			}
        	}
        	if(list == null || list.size() == 0) {
        		return true;
        	}
        	
        	for(JobInfo info : list ) {
        		String triger = info.getJobTriger();
        		if(StringUtils.isBlank(triger)) {
        			continue;
        		}
        		JobSupport.getInstance().addJob(info);
        		this.getLogger(null).sysoutInfo(200010005, this.getClass() , info.getJobName() , info.getJobTitle() , info.getJobTriger());	// 开始加载任务{0}，任务执行周期为{1}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	
	public boolean onDestory() {
		return true;
	}

}


































































