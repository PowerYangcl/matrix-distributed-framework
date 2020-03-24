package com.matrix.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.matrix.base.BaseClass;




/**
 * @description: 网关入口
 * 
 * 请求接口数据格式示例：
			{
				"head": {
					"target": "MANAGER-API-100",
					"accessToken": "",
					"client": 3,
					"version": "vsesion-2.0.0.1",
					"requestTime": "2018-12-14 16:47:09",
					"channel": "mip会员平台PC前端",
					"key": "133C9CB27DA0",
					"value": "58b6e0bd4d34a35b9773d4762be0f521"
				},
				"data": {
					"cid":2735828374812748174428374,  // cid如果 != 0则代表用户拥有多店铺，需要前端传入cid
					"userName": "admin-lqx",
					"password": "xxxxxx",
					"validateCodeKey": "b89e4919-2620-4c52-a371-240e452fbc3b",
					"validateCode": "SOFH",
					"platform": "133EFB922DF3"
				}
			}
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年8月9日 下午5:32:44 
 * @version 1.0.0.1
 */
//@Service("apiService")
public class ApiServiceImplThreadPool extends BaseClass { //  implements IApiService {
	
	
	private static Integer coreSize = Runtime.getRuntime().availableProcessors();
	
	private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("ApiController线程池-%s").build();
	 
    /**
     * 线程池特性：
     * 		特性一：当池中正在运行的线程数（包括空闲线程）小于corePoolSize时，新建线程执行任务。
     * 		特性二：当池中正在运行的线程数大于等于corePoolSize时，新插入的任务进入workQueue排队（如果workQueue长度允许），等待空闲线程来执行。
     * 		特性三：当队列里的任务数达到上限，并且池中正在运行的线程数小于maximumPoolSize，对于新加入的任务，新建线程。
     * 		特性四：当队列里的任务数达到上限，并且池中正在运行的线程数等于maximumPoolSize，对于新加入的任务，执行拒绝策略（线程池默认的拒绝策略是抛异常）。
     * 
     * ThreadPoolExecutor(
     * 	int corePoolSize, 					线程池核心线程数|第一个请求进入后就会被默认初始化的线程数量
     * 	int maximumPoolSize, 		线程池最大数|corePoolSize达到阈值后会新建线程，上线为maximumPoolSize
     * 	long keepAliveTime, 			空闲线程存活时间|特指corePoolSize达到阈值后会新建线程
     * 	TimeUnit unit, 					时间单位
     * 	BlockingQueue<Runnable> workQueue, 		线程池所使用的缓冲队列|maximumPoolSize达到阈值后，剩余的请求会被存入缓冲队列
     * 	ThreadFactory threadFactory, 						线程池创建线程使用的工厂
     * 	RejectedExecutionHandler handler 				线程池对拒绝任务的处理策略|workQueue缓冲队列满后的异常处理策略
     * )
     * 
     * maximumPoolSize + workQueue.size|线程池最大数+线程池所使用的缓冲队列大小之和，为这个线程池的总容量
     * 请求超过这个容量会报出java.util.concurrent.RejectedExecutionException
     * 也就是说Nginx服务转发到这台Tomcat上的请求不得大于此线程池的数量
     * 
     * */ 
    private final static ExecutorService pool = new ThreadPoolExecutor(
    		50, 
    		100, 
    		60L, 
    		TimeUnit.SECONDS, 
    		new LinkedBlockingQueue<Runnable>(150), 
    		namedThreadFactory, 
    		new ThreadPoolExecutor.AbortPolicy()
	);
 
	/**
	 * @description: 开放接口入口|接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
	 *
	 * @param key 请求秘钥
	 * @param value 请求秘钥加密后的消息体    平台分配给第三方的秘钥+接口名称+请求发起时间(精确到小时，格式为：yyyy-MM-dd HH:mm:ss) 做md5后的结果
	 * @param json 请求消息体
	 * @author Yangcl
	 * @date 2017年11月10日 上午11:49:21 
	 * @version 1.0.0.1
	 */
	public  JSONObject apiService(HttpServletRequest request, HttpServletResponse response , HttpSession session , String json) {
		JSONObject result = null;
		TaskApiService task = new TaskApiService(request, response, session, json);
		Future<JSONObject> submit = pool.submit(task);
		try {
			result = submit.get(60L, TimeUnit.SECONDS);	// request请求在一分钟后会被丢弃	
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			result = new JSONObject();
			result.put("status" , "error");
			result.put("code" , "10022");
			result.put("msg" , this.getInfo(600010022));  // 600010022=API接口请求自动超时，请刷新
			return result;
		}
		return result;
	}
}


















