package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.matrix.base.Result;
import lombok.extern.slf4j.Slf4j;


/**
 * @description: 以访问接口开辟内存空间的方式来验证Kubernetes动态扩缩容能力
 * 
 * @author Yangcl
 * @date 2023-6-12 17:55:12
 * @home https://github.com/PowerYangcl
 * @path matrix-jsp-demo / com.matrix.controller.DynamicScalingController.java
 * @version v-matrix-hd-test
 */
@Slf4j
@Controller
@RequestMapping("ds")
public class DynamicScalingController {
	
	/**
	 * @description: 模拟CPU占用，这段代码会执行一个CPU密集型的操作，即计算正弦函数。
	 * 		http://localhost:8080/matrix-jsp-demo/ds/api_dynamic_scaling_cpu_request.do?sin=100
	 * 		样本数据：从sin=100,0000(100万) 到 sin=1000,0000(1000万)，三个pod cpu占用率明显飙升
	 * 
	 * @author Yangcl
	 * @date 2023-6-12 18:01:59
	 * @home https://github.com/PowerYangcl
	 * @version v-matrix-hd-test
	 */
	@ResponseBody
	@RequestMapping(value = "api_dynamic_scaling_cpu_request", produces = { "application/json;charset=utf-8" })
	public Result<?> apiDynamicScalingCpuRequest(Integer sin, HttpServletRequest request, HttpSession session){ 
		double value = 0;
        try {
        	if(sin == null || sin <= 0) {
        		sin = 10000;
    		}
        	for (int i = 0; i < sin; i++) {		// Perform some CPU-intensive operation
        		value += Math.sin(i);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			log.info("api dynamic scaling cpu request Math.sin(" + sin + ") = " + value);
		}
		return Result.SUCCESS("api dynamic scaling cpu sin += " + value);
	}
	
	/**
	 * @description: 模拟内存占用
	 * 		http://localhost:8080/matrix-jsp-demo/ds/api_dynamic_scaling_mem_request.do?mem=20
	 * 
	 * @param mem
	 * @author Yangcl
	 * @date 2023-6-13 15:00:31
	 * @home https://github.com/PowerYangcl
	 * @version v-matrix-hd-test
	 */
	@ResponseBody
	@RequestMapping(value = "api_dynamic_scaling_mem_request", produces = { "application/json;charset=utf-8" })
	public Result<?> apiDynamicScalingMemRequest(Integer mem, HttpServletRequest request, HttpSession session){ 
		if(mem == null || mem <= 0) {
			mem = 10;
		}
		byte[] memory = new byte[1024 * 1024 * mem];		// 开辟10M内存空间
		try {
			Thread.sleep(1000);	 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("api dynamic scaling2 used：" + mem + "Mb  MaxMemory: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "Mb  TotalMemory: " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "Mb  FreeMemory: " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "Mb");
		
		return Result.SUCCESS("api dynamic scaling " + mem + " M");
	}
	
}
























































