package mutex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
/**
 * @description: 首先：调用batchInsert方法插入10000条测试数据，
 * 	然后使用redisPipelineGet批量获取数据打印，再调用redisStringBatchGet方法批量获取数据
 * 	打印（之前用的方法generalBatchGet我注释了太慢了运行太久有兴趣的可以打开注释试试）
 * 
 * @author Yangcl
 * @date 2021-2-5 16:02:51
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / mutex.RedisPipelineServiceTest.java
 * @version 1.0.0.1
 */
public class RedisPipelineServiceTest {

	@Autowired
    private RedisPipelineService pipelineService;
	
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String pattern="yuanfenge:test:";


    @Test
    public void batchGet() throws Exception {
        batchInsert();
        redisPipelineGet();
        //generalBatchGet();
        redisStringBatchGet();
    }

    public void batchInsert() throws Exception {
        long stime=System.currentTimeMillis();
        List<Map<String, String>> saveList=new ArrayList<Map<String, String>>();
        for (int i = 0; i < 10000; i++) {
            Map<String,String> map=new HashMap<>();
            map.put("key",pattern + i);
            map.put("value","value值为" + i);
            saveList.add(map);
        }
        pipelineService.batchInsert(saveList, TimeUnit.MINUTES,15);

        long etime=System.currentTimeMillis();
        System.out.println("插入10000条数据消耗时间为："+(etime-stime));

    }

    private void redisPipelineGet() {
        long stime=System.currentTimeMillis();
        Set<String> keys = stringRedisTemplate.keys(pattern + "*");
        List<String> keyList=new ArrayList<>();
        keys.forEach(i->{
            keyList.add(i);
        });
        List<String> strings = pipelineService.batchGet(keyList);
        long etime=System.currentTimeMillis();
        System.out.println("string="+strings);
        System.out.println("使用Pipelined消耗时间为："+(etime-stime));
    }


    public void redisStringBatchGet() {
        long stime=System.currentTimeMillis();
        Set<String> keys = stringRedisTemplate.keys(pattern + "*");
        List<String> keyList=new ArrayList<>();
        keys.forEach(i->{
            keyList.add(i);
        });
        
        List<String> strings = stringRedisTemplate.opsForValue().multiGet(keyList);
        long etime=System.currentTimeMillis();
        System.out.println("string="+strings);
        System.out.println("使用multiGet消耗时间为："+(etime-stime));
    }

    
    
    
    public void generalBatchGet() {
        long stime=System.currentTimeMillis();
        Set<String> keys = stringRedisTemplate.keys(pattern + "*");
        List<String> keyList=new ArrayList<>();
        keys.forEach(i->{
            keyList.add(i);
        });
        List<String> strings=new ArrayList<>();
        for (String key : keyList) {
            String value = stringRedisTemplate.opsForValue().get(key);
            strings.add(value);
        }
        long etime=System.currentTimeMillis();
        System.out.println("string="+strings);
        System.out.println("消耗时间为："+(etime-stime));
    }

}
