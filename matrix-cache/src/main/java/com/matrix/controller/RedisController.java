package com.matrix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.matrix.annotation.DistributedLock;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;

@Controller
@RequestMapping("redis")
public class RedisController extends BaseClass{

	public static  Integer total = 200;
	
	/**
	 * @description: 测试分布式锁扣减库存情况
	 * 		压测100个请求循环2次，平均耗时150毫秒/吞吐量381 sec
	 * 
	 * @author Yangcl
	 * @date 2021-7-23 17:59:37
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.0.4-redisson
	 * @throws InterruptedException 
	 */
    
    @ResponseBody
    @DistributedLock(value="goods", leaseTime=2)
    @RequestMapping(value = "api_decrease_stock", produces = {"application/json;charset=utf-8"})
    public Result<Integer>  lockDecreaseStock() throws InterruptedException{
        if (total > 0) {
            total = total - 1;
        }
        Thread.sleep(50);
        if(total != 0) {
        	this.getLogger(null).sysoutInfo("===注解模式=== 减完库存后，当前库存===" + total, this.getClass());
        }else {
        	this.getLogger(null).sysoutInfo("===库存 = " + total, this.getClass());
        }
        return Result.SUCCESS(total);
    }
}










































