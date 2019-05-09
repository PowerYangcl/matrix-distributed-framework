package com.matrix.threads;

import com.matrix.base.BaseClass;

/**
 * @description: 基础多线程封装，以Thread/Runnable方式运行，可以实现优雅退出
 *
 * @author Yangcl
 * @date 2019年2月21日 下午10:41:24 
 * @version 1.0.0.1
 */
public class RunnableService extends BaseClass{
	
	private Thread executeThread;
    private boolean finished = false;

    /**
     * @description: 执行一个多线程任务
     *
     * @param task 准备启动执行的线程
     * @author Yangcl
     * @date 2019年2月21日 下午11:02:00 
     * @version 1.0.0.1
     */
    public void execute(final Runnable task) {
        executeThread = new Thread() {
            public void run() {
                Thread runner = new Thread(task);
                runner.setDaemon(true);
                runner.start();
                try {
                	// executeThread线程(主线程)将一直等待runner执行完成，如果不被显示打断的话。
                    runner.join();		
                    finished = true;
                } catch (InterruptedException e) {  // 此处无需捕获异常
                }
            }
        };
        executeThread.start();
    }

    /**
     * @description: 在指定毫秒后结束任务
     *
     * @param mills 指定时间：毫秒
     * @author Yangcl
     * @date 2019年2月21日 下午11:05:04 
     * @version 1.0.0.1
     */
    @SuppressWarnings("static-access")
	public void shutdown(long mills) {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if ((System.currentTimeMillis() - currentTime) >= mills) {
                executeThread.interrupt();	// 任务超时需要结束，这里进行显示打断
                break;
            }
            try {
                executeThread.sleep(10);
            } catch (InterruptedException e) {
                break;	// 执行线程被打断
            }
        }
        finished = false;
    }
    
    
/**
    public static void main(String[] args) {
    	RunnableService service = new RunnableService();
        long start = System.currentTimeMillis();
        service.execute(new Runnable() {
			public void run() { //load a very heavy resource.
				try {
					// 线程不会等待50000毫秒，会在指定的10000毫秒后结束。
					// 如果这里是5000，那么线程也不会等待10000毫秒后才执行。
	                Thread.sleep(5000);		
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			}
		});
        service.shutdown(10000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
 */
    
}














