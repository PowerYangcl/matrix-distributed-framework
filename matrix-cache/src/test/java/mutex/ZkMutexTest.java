package mutex;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import com.matrix.cache.enums.ZkLockPathEnum;
import com.matrix.support.DistributeLockZk;

public class ZkMutexTest {

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		InterProcessMutex ipm = DistributeLockZk.getInstance().zkMutexLock(ZkLockPathEnum.zkTest);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		TaskMutex a = new TaskMutex();
		a.setInterProcessMutex(ipm);
		a.setName("线程 af");
		Future<String> af = executor.submit(a);
		
		
		TaskMutex b = new TaskMutex();
		b.setInterProcessMutex(ipm);
		b.setName("线程 bf");
		Future<String> bf = executor.submit(b);
		
		
		TaskMutex c = new TaskMutex();
		c.setInterProcessMutex(ipm);
		c.setName("线程 cf");
		Future<String> cf = executor.submit(c);
		
		
		TaskMutex d = new TaskMutex();
		d.setInterProcessMutex(ipm);
		d.setName("线程 df");
		Future<String> df = executor.submit(d);
		
		
		TaskMutex e = new TaskMutex();
		e.setInterProcessMutex(ipm);
		e.setName("线程 ef"); 
		Future<String> ef = executor.submit(e);
		
		
		
		System.out.println("af status = " + af.get());
		System.out.println("bf status = " + bf.get());
		System.out.println("cf status = " + cf.get());
		System.out.println("df status = " + df.get());
		System.out.println("ef status = " + ef.get());
		
		
		
		
		
		
		
		executor.shutdown();
	}
}

















