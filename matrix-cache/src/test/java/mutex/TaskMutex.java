package mutex;

import java.util.concurrent.Callable;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class TaskMutex implements Callable<String>{

	private InterProcessMutex interProcessMutex;
	private String name;
	
	@Override
	public String call() {
		try{
            // 获取锁
			interProcessMutex.acquire();
            System.out.println(this.name + "  acquire read lock");
            return "锁获取成功";
        } catch (Exception e){
        	e.printStackTrace();   
        } finally {
        	try {
        		// 释放锁
				interProcessMutex.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
            System.out.println(this.name + "  release read lock");
        }
		
		return "锁获取成功";
	}

	public InterProcessMutex getInterProcessMutex() {
		return interProcessMutex;
	}
	public void setInterProcessMutex(InterProcessMutex interProcessMutex) {
		this.interProcessMutex = interProcessMutex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
