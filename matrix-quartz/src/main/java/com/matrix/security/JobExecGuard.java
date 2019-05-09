package com.matrix.security;

import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;

/**
 * @description: 主动触发定时任务|当定时任务在一个节点处于关闭状态的
 * 			时候，此处会将所有定时任务加入轮询器，同时激活
 *
 * @author Yangcl
 * @date 2019年1月9日 下午3:26:31 
 * @version 1.0.0.1
 */
public class JobExecGuard extends BaseClass implements IBaseExecute {

	public String execute(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
