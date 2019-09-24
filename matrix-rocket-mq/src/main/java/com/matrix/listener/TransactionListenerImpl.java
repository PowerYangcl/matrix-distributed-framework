package com.matrix.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 事务回查
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年6月26日 上午11:21:19 
 * @version 1.0.0.1
 */
public class TransactionListenerImpl implements TransactionListener {
	
//	private AtomicInteger transactionIndex = new AtomicInteger(0);
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
    private ConcurrentHashMap<String, String> localTrans = new ConcurrentHashMap<>();

	/**
	 * @description: 本地事务执行方法，系统的业务逻辑（扣钱加钱等事务性操作）
	 * 		注意！此方法只会执行一次。UNKNOW状态的消息会由【checkLocalTransaction()】方法来检查，默认检查15次后丢弃
	 *
	 * @author Yangcl
	 * @date 2019年6月24日 下午16:44:26 
	 * @version 1.0.0.1
	 */
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.err.println(arg + " = 开始执行本地事务");
        localTrans.put(msg.getTransactionId(), arg.toString());
        
        if(arg.equals("TagA")) {
        	// TODO 此时需要记录日志
        	return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        if(arg.equals("TagB")) {
        	return LocalTransactionState.UNKNOW;
        }
        
        System.err.println(arg + " 事务已经提交 Commit Message Ok !");
        return LocalTransactionState.COMMIT_MESSAGE;
    }


	/**
	 * @description: 本地事务状态检查回调方法，模拟从ConcurrentHashMap获取本地事务的执行情况，并根据执行情况决定最终的事务提交状态
	 * 
	 * 		此方法主要回查 executeLocalTransaction()方法中处于UNKNOW状态的消息
	 *
	 * @author Yangcl
	 * @date 2019年6月24日 下午16:44:26 
	 * @version 1.0.0.1
	 */
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
    	String status = localTrans.get(msg.getTransactionId());
        if (null != status) {
            switch (status) {
	            case "TagA":
	            	System.err.println("正在检查本地事务状态" + status +"  = roll back message |" + msg.getUserProperty("up"));
	            	return LocalTransactionState.ROLLBACK_MESSAGE;
                case "TagB":
                	/**
                	 * DCacheEnum.Increment是通用自增枚举前缀，在Redis中自增，以当前消息ID为缓存后缀。
                	 * 这里假设达到了4次我们就回滚这条消息，认定为消息失败，然后记录日志。msg.getMsgId()是动态变化的，不可以做
                	 * 缓存后缀使用。
                	 */
                	Long value = launch.loadDictCache(DCacheEnum.Increment, "").increment(msg.getTransactionId(), 1L, 2*60*60);
                	System.err.println("正在检查本地事务状态" + status +" = unknow |" + msg.getUserProperty("up") + " 当前回调次数：" + value);
                	if(value == 3) {  // TODO 此处需要结合Redis做判断，下面的方式不可用
                		System.err.println("消息提交成功！UP = " + msg.getUserProperty("up"));
                		return LocalTransactionState.COMMIT_MESSAGE;
                	}
                	
                	if(value == 5) {  // 此处仅做示例使用
                		// TODO 泛化调用，记录日志。
                		System.err.println("该消息废弃！status = " + status + " 系统已经记录日志");
                		return LocalTransactionState.ROLLBACK_MESSAGE;
                	}
                	
                    return LocalTransactionState.UNKNOW;		//  消息会回调15次，然后扔掉，消费者收不到消息
                default:
                	System.err.println("正在检查本地事务状态" + status +"  = commit message |" + msg.getUserProperty("up"));
                    return LocalTransactionState.COMMIT_MESSAGE;
            }
        }
        
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}































