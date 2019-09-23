package mutex;

import com.matrix.base.BaseClass;
import com.matrix.cache.redis.core.RedisTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// 李玟霆
public class RedisConcurrentTest extends BaseClass{
    //线程数量
    private static final int threadCount = 100;

    RedisTemplate instance = RedisTemplate.getInstance();


    public void main() throws InterruptedException {

        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //创建一个CountDownLatch 并且给200个线程数量
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        //循环
        for (int i = 0; i < threadCount; i++) {
            //给一个线程名称(生产中最好给一个有意义的名称)
            final int threadNum = i;
            //线程池调用
            executorService.execute(() -> {
                try {
                    //业务逻辑方法
                    test(threadCount);
                } catch (Exception e) {
                } finally {
                    //执行完业务逻辑一定要执行 countDown方法 来-1
                    //如果未调用则无法执行 下方的await()方法
                    //内部使用CAS算法 调用CPU级别指令 安全高效
                    countDownLatch.countDown();
                }
            });
        }
        //当countDownLatch=0的时候执行以下方法
        countDownLatch.await();
        //如果忘记调用countDown()方法来减-1 或者逻辑执行时间比较久 也可以设置一个超时时间,超过这个时间一到会执行await()方法
        //很简单就是加上俩参数一个 超时的时间 一个是时间单位
        //countDownLatch.await(10, TimeUnit.MILLISECONDS);
        //关闭线程
        executorService.shutdown();

    }

    //模拟业务逻辑方法
    private void test(int threadNum) throws InterruptedException {
        Long count = instance.incrementTimeout("Concurrent_test91", 10*60L);
        System.out.println(count);
    }

}
