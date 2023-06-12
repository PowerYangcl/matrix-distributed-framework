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
	 * @description: 开辟10M内存空间，1分钟后回收
	 * 		http://localhost:8080/matrix-jsp-demo/ds/api_dynamic_scaling_request.do
	 * 
	 * @param request
	 * @author Yangcl
	 * @date 2023-6-12 18:01:59
	 * @home https://github.com/PowerYangcl
	 * @version v-matrix-hd-test
	 */
	@ResponseBody
	@RequestMapping(value = "api_dynamic_scaling_request", produces = { "application/json;charset=utf-8" })
	public Result<?> apiDynamicScalingRequest(HttpServletRequest request, HttpSession session){ 
		byte[] memory = null;
        try {
        	memory = new byte[1024 * 1024 * 10];		// 开辟10M内存空间
        	log.info("api dynamic scaling：" + memory.length);
			Thread.sleep(60000);	// Sleep for 1 minute
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			memory = null;		// Deallocate memory
		}
		return Result.SUCCESS("api dynamic scaling 10 M");
	}
}
























































