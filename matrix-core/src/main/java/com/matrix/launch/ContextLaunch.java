package com.matrix.launch;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.matrix.base.BaseLog;
import com.matrix.system.init.SystemInit;
import com.matrix.system.listener.MatrixDistributedFrameworkListener;

/**
 * @description: Spring配置核心入口类
 * 
 * @author Yangcl
 * @date 2021-1-21 18:45:33
 * @home https://github.com/PowerYangcl
 * @path matrix-core/com.matrix.launch.Launch.java
 * @version 1.0.0.1
 */
@Aspect
@Configuration
@ComponentScan({"com.matrix.dao,com.matrix.service"})
public class ContextLaunch {
	
	private static final String execution = "execution(* com.matrix.service.impl.*.*(..))";
	
	private SystemInit systemInit = new SystemInit();
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Bean(name = "mdf-listener")
	public MatrixDistributedFrameworkListener initBbean() {
		return new MatrixDistributedFrameworkListener(systemInit);
	}
	
    @Bean(name = "tx-advice")
    public TransactionInterceptor txAdvice() {
        DefaultTransactionAttribute required = new DefaultTransactionAttribute();
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute readonly = new DefaultTransactionAttribute();
        readonly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        readonly.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", required);
        source.addTransactionalMethod("insert*", required);
        source.addTransactionalMethod("save*", required);
        source.addTransactionalMethod("create*", required);
        source.addTransactionalMethod("delete*", required);
        source.addTransactionalMethod("update*", required);
        source.addTransactionalMethod("edit*", required);
        source.addTransactionalMethod("exec*", required);
        source.addTransactionalMethod("set*", required);
        source.addTransactionalMethod("batch*", required);
        
        source.addTransactionalMethod("get*", readonly);
        source.addTransactionalMethod("query*", readonly);
        source.addTransactionalMethod("find*", readonly);
        source.addTransactionalMethod("list*", readonly);
        source.addTransactionalMethod("count*", readonly);
        source.addTransactionalMethod("is*", readonly);
        source.addTransactionalMethod("select*", readonly);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
    	this.systemInit();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(execution);
        return new DefaultPointcutAdvisor(pointcut, this.txAdvice());
    }
    
    private void systemInit() {
    	BaseLog.getInstance().setLogger(null).sysoutInfo("Power Matrix Initializing starting ! ! ! ! !" , this.getClass());
    	boolean flag = systemInit.onInit();
		if(flag) {
			BaseLog.getInstance().setLogger(null).sysoutInfo("Power Matrix Initializing Finished ! ! ! ! !" , this.getClass());
		}else {
			BaseLog.getInstance().setLogger(null).sysoutInfo("Error occurs in initializing Power Matrix " , this.getClass());
		}
    }
    
    /**
     * @description: 开启注解校验
     * 		https://blog.csdn.net/f641385712/article/details/97402946
     * 
     * @author Yangcl
     * @date 2022-3-4 18:28:53
     * @home https://github.com/PowerYangcl
     * @version 1.6.0.8-validation
     */
    @Bean
    public MethodValidationPostProcessor createMethodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}






// spring boot  配置事务通知  参考
// 配置文件：https://my.oschina.net/u/2331760/blog/3020092 
// 注解方式：https://www.cnblogs.com/edi-kai/p/11289701.html  推荐