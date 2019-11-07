package com.matrix.cache.redis.core;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.matrix.base.BaseClass;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description: 支持Redis集群带密码验证的需求|底层包装类为org.springframework.data.redis。
 *			提供接口，包含String、hash、Set和ZSet，没有加入List
 *
 *			Redis集群通常采用主从、哨兵模式和集群模式三种。主从往往是为了读写分离、backup 等目的， 
 *			哨兵可以检测主从健康， 主挂了可以把从提升为主， 集群往往是为了数据分片， 解决单台机器资源的上限的问题。
 *			故：在应对较大的缓存数据量的情况系统采用集群模式。
 *		
 *			https://www.cnblogs.com/tankaixiong/p/4048167.html
 *
 * @author Yangcl
 * @date 2018年9月18日 上午9:38:09
 * @version 1.0.0.1
 */
public class RedisTemplate extends BaseClass {

    private StringRedisTemplate template = null;
    private JedisConnectionFactory factory = null;

    private RedisTemplate() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        clusterConfig.setMaxRedirects(Integer.valueOf(this.getConfig("matrix-cache.redis_max_redirects")));
        String[] arr = this.getConfig("matrix-cache.cache_url_" + this.getConfig("matrix-core.model")).split(",");
        this.getLogger(null).sysoutInfo("-------------------------------------------Current Redis Cache Url Is : " + this.getConfig("matrix-cache.cache_url_" + this.getConfig("matrix-core.model")), this.getClass());
        for (int i = 0; i < arr.length; i++) {
            RedisClusterNode node = new RedisClusterNode(arr[i].split(":")[0], Integer.valueOf(arr[i].split(":")[1]));
            clusterConfig.addClusterNode(node);
        }

        JedisPoolConfig pconfig = new JedisPoolConfig();
        pconfig.setMaxIdle(Integer.valueOf(this.getConfig("matrix-cache.redis_pool_maxIdle")));				// 最大空闲连接数, 默认8个
        pconfig.setMinIdle(Integer.valueOf(this.getConfig("matrix-cache.redis_pool_minIdle")));				// 最小空闲连接数, 默认0
        pconfig.setMaxTotal(Integer.valueOf(this.getConfig("matrix-cache.redis_pool_maxTotal")));		//最大连接数, 50
        pconfig.setMaxWaitMillis(Long.valueOf(this.getConfig("matrix-cache.redis_pool_maxWaitMillis")));
        pconfig.setTestOnCreate(Boolean.valueOf(this.getConfig("matrix-cache.redis_pool_testOnCreate")));
        pconfig.setTestOnReturn(Boolean.valueOf(this.getConfig("matrix-cache.redis_pool_testOnReturn")));
        pconfig.setTestOnBorrow(Boolean.valueOf(this.getConfig("matrix-cache.redis_pool_testOnBorrow"))); // 在获得链接的时候检查有效性
        pconfig.setTestWhileIdle(Boolean.valueOf(this.getConfig("matrix-cache.redis_pool_testWhileIdle")));  // 在空闲时检查有效性
        
        
        pconfig.setLifo(true);
        
        // 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接 > 最大空闲数(MaxIdle)
        // 时直接逐出,不再根据setMinEvictableIdleTimeMillis判断  (默认逐出策略)   
        pconfig.setSoftMinEvictableIdleTimeMillis(600000);
        
        // 默认逐出策略。逐出连接的最小空闲时间 默认1800000毫秒(此处设置10分钟)
        // https://www.cnblogs.com/xiao-tao/p/9797252.html
        // pconfig.setMinEvictableIdleTimeMillis(1800000);
        
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        pconfig.setNumTestsPerEvictionRun(100);
         
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        pconfig.setTimeBetweenEvictionRunsMillis(600000);
        

        // Spring Data Redis 连接工厂配置
        factory = new JedisConnectionFactory(clusterConfig , pconfig);
        factory.setPassword(this.getConfig("matrix-cache.redis_password_" + this.getConfig("matrix-core.model")));
        factory.afterPropertiesSet();
        template = new StringRedisTemplate(factory);
    }

    private static class LazyHolder {
        private static final RedisTemplate INSTANCE = new RedisTemplate();
    }

    public static final RedisTemplate getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    @Bean
    public StringRedisTemplate getTemplate() {
    	return template;
    }
    
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
    	return factory;
    }

    /////////////////////////////////////////////////////////////////// 基础json //////////////////////////////////////////////////////////////////////

    /**
     * @description: 返回 key 所关联的字符串值。
     *
     * @param key 如果 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
     * @author Yangcl
     * @date 2018年9月18日 下午5:24:33
     * @version 1.0.0.1
     */
    public String get(String key) {
        return this.template.opsForValue().get(key);
    }

    /**
     * @description: 将字符串值 value 关联到 key 。
     *		对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     *
     * @param key    如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月18日 下午5:26:44
     * @version 1.0.0.1
     */
    public void set(String key, String value) {
        this.template.opsForValue().set(key, value);
    }

    /**
     * @description: 将字符串值 value 关联到 key 。带自定义超时时间
     *
     * @param key
     * @param value
     * @param timeout
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:19:51
     * @version 1.0.0.1
     */
    public void set(String key, String value, long timeout) {
        this.template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * @description: 调用底层jedis命令实现StringRedisTemplate中没有的setnx方法
     * 			通常这个方法作为Redis高性能分布式锁来使用。
     *
     * @param key					使用key来当锁，因为key是唯一的
     * @param requestFlag    Redis对应的Value，这里表达为请求加锁者提供的标识，为分布式锁的可靠性提供依据
     * @param timeout				锁的过期时间，需要根据实际业务场景定义，单位秒
     * @param nxxx					通常填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作
     * @param expx					通常传的是EX，意思是我们要给这个key加一个过期的设置，具体时间由 timeout 参数决定。
     * @author Yangcl
     * @date 2018年12月17日 下午3:57:28 
     * @version 1.0.0.1
     */
    public Boolean setnx(final String key, final String requestFlag, final Long timeout, final String nxxx , final String expx) {
    	if(StringUtils.isAnyBlank(key , requestFlag , nxxx , expx)) {
    		return false;
    	}
    	if(timeout == null || timeout <= 0) {
    		return false;
    	}
        return this.template.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Object nativeConnection = connection.getNativeConnection();
                String result = "";
                if (nativeConnection instanceof JedisCommands) {
                    result = ((JedisCommands) nativeConnection).set(key, requestFlag, nxxx, expx, timeout);
                }
				return "OK".equalsIgnoreCase(result);
			}
		});
    }
    
    /**
     * @description: 将字符串值 value 关联到 key 。带默认超时时间30天
     *
     * @param key
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:19:51
     * @version 1.0.0.1
     */
    public void setDefalutTimeout(String key, String value) {
        this.template.opsForValue().set(key, value, 30 * 24 * 60 * 60, TimeUnit.SECONDS);
    }

	/**
	 * @description:自定义 redis key 设置超时时间
	 *
	 * @param key
	 * @param expireTime
	 * 
	 * @author Sjh
	 * @date 2019/4/10 16:29
	 * @version 1.0.1
	 */
    public void setKeyTimeout(String key, Long expire) {
        this.template.expire(key,expire, TimeUnit.SECONDS);
    }

    /**
     * @description: 在原有的值基础上新增字符串到末尾。
     *
     * @param key
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:42:07
     * @version 1.0.0.1
     */
    public Integer appendStringInEnd(String key, String value) {
        return this.template.opsForValue().append(key, value);
    }

    /**
     * @description: 获取原来key键对应的值并重新赋新值。
     *
     * @param key
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:46:33
     * @version 1.0.0.1
     */
    public String getAndSet(String key, String value) {
        return this.template.opsForValue().getAndSet(key, value);
    }

    /**
     * @description: 获取指定字符串的长度。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午7:05:22
     * @version 1.0.0.1
     */
    public Long size(String key) {
        return this.template.opsForValue().size(key);
    }

    /**
     * @description: 以增量的方式将long值存储在变量中。
     *
     * @param key
     * @param delta 增量值，1、2、3等等
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:06:42
     * @version 1.0.0.1
     */
    public Long increment(String key, Long delta , long timeout) {
    	Long value =  this.template.opsForValue().increment(key, delta);
    	this.template.expire(key, timeout, TimeUnit.SECONDS);
    	return value;
    }

    /**
     * @description: 以增量+1的方式将long值存储在变量中，同时在指定时间内过期
     *
     * @param key 自增Key
     * @param timeout 过期时间|如果为null，则持久生效
     *
     * @author Yangcl
     * @date 2018年11月19日 下午18:36:26
     * @version 1.0.0.1
     */
    public Long incrementTimeout(String key, Long timeout) {
    	Long  value = this.increment(key + "-count", 1L , timeout);
        this.getLogger(null).sysoutInfo("获取缓存开始增量计次|Redis Key = " + key + "  当前增量值 = " + value, this.getClass());
        return value;
    }

    /**
     * @description: 删除缓存信息
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:15:00
     * @version 1.0.0.1
     */
    public void del(String key) {
        this.template.delete(key);
    }

    /**
     *@description:通过缓存的删除关键字 批量删除缓存信息
     *
     *@param  preKey 如缓存key：xs-MipLevel-2-1062173003451863043，用 MipLevel-2 做为关键字批量删除缓存
     *@author Sjh
     *@date 2018/11/21 10:44
     *@version 1.0.0.1
     */
    public void batchDelWithPre(String preKey) {
        Set<String> set = this.template.keys( preKey + "*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String keyStr = it.next();
            this.getLogger(null).sysoutInfo("开始批量删除缓存，key = " + keyStr , this.getClass());
            this.template.delete(keyStr);
        }
    }


    /////////////////////////////////////////////////////////////////// 哈希存储 //////////////////////////////////////////////////////////////////////

    /**
     * @description: 返回哈希表 key 中给定域 field 的值。
     *
     * @param key
     * @param field
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:53:28
     * @version 1.0.0.1
     */
    public Object hget(final String key, final String field) {
        return this.template.opsForHash().get(key, field);
    }

    /**
     * @description: 返回哈希表 key 中，所有的域和值。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月18日 下午8:52:39
     * @version 1.0.0.1
     */
    public Map<Object, Object> hgetAll(String key) {



        return this.template.opsForHash().entries(key);
    }

    /**
     * @description: 将哈希表key中的域 field 的值设为value。如果 key 不存在，
     * 		一个新的哈希表被创建并进行 HSET 操作。如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *
     * 例如：
     *			redisTemplate.opsForHash().put("hashValue","map1","map1-1");
     *			redisTemplate.opsForHash().put("hashValue","map2","map2-2");
     *
     * @param key
     * @param field
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:26:13
     * @version 1.0.0.1
     */
    public void hset(String key, String field, String value) {
        this.template.opsForHash().put(key, field, value);
    }

    /**
     * @description: 以map集合的形式添加键值对。
     * 例如：
    Map newMap = new HashMap();
    newMap.put("map3","map3-3");
    newMap.put("map5","map5-5");
    redisTemplate.opsForHash().putAll("hashValue",newMap);
    Map map = redisTemplate.opsForHash().entries("hashValue");
     *
     * @param key
     * @param map
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:34:31
     * @version 1.0.0.1
     */
    public void hsetAll(String key, Map<String, Object> map) {
        this.template.opsForHash().putAll(key, map);
    }

    /**
     * @description: 删除变量中的键值对，可以传入多个参数，删除多个键值对
     * 例如：
     *			redisTemplate.opsForHash().delete("hashValue","map1","map2");
     *
     * @param key
     * @param field  建议使用字符串
     * @return
     * @author Yangcl
     * @date 2018年9月18日 下午8:39:20
     * @version 1.0.0.1
     */
    public Long hdel(String key, Object... field) {
        return this.template.opsForHash().delete(key, field);
    }

    /**
     * @description: 判断变量中是否有指定的map键。
     *
     * @param key
     * @param hashKey
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:46:24
     * @version 1.0.0.1
     */
    public Boolean hkeyExist(String key, Object hashKey) {
        return this.template.opsForHash().hasKey(key, hashKey);
    }


    /////////////////////////////////////////////////////////////////// 无序Set存储|交叉并操作暂未提供 //////////////////////////////////////////////////////////////////////

    /**
     * @description: 添加一个set集合到redis中
     *	例如：
     *			redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
     * @param key
     * @param values
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:22:26
     * @version 1.0.0.1
     */
    public Long addSet(String key, String... values) {
        return this.template.opsForSet().add(key, values);
    }

    /**
     * @description: 获取变量中的值。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:27:10
     * @version 1.0.0.1
     */
    public Set<String> sget(String key) {
        return this.template.opsForSet().members(key);
    }

    /**
     * @description: 获取set集合的长度
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:30:26
     * @version 1.0.0.1
     */
    public Long setLength(String key) {
        return this.template.opsForSet().size(key);
    }

    /**
     * @description: 随机获取set集合中的元素。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:32:22
     * @version 1.0.0.1
     */
    public String setRandomMember(String key) {
        return this.template.opsForSet().randomMember(key);
    }

    /**
     * @description: 随机获取set集合中指定个数的元素。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:32:22
     * @version 1.0.0.1
     */
    public List<String> setRandomMembers(String key, long count) {
        return this.template.opsForSet().randomMembers(key, count);
    }


    /**
     * @description: 检查给定的元素是否在set集合中。
     *	例如：
     *			boolean isMember = redisTemplate.opsForSet().isMember("setValue","A");
     * @param key
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:37:15
     * @version 1.0.0.1
     */
    public Boolean isInSet(String key, String value) {
        return this.template.opsForSet().isMember(key, value);
    }

    /**
     * @description: 批量移除set集合中的元素。
     *
     * @param key
     * @param objects
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:41:34
     * @version 1.0.0.1
     */
    public Long setElementRemove(String key, Object... objects) {
        return this.template.opsForSet().remove(key, objects);
    }

    /////////////////////////////////////////////////////////////////// 有序Set存储|交叉并操作暂未提供 //////////////////////////////////////////////////////////////////////

    /**
     * @description: 添加一个元素到指定的有序set集合中
     *	例如：
     *			redisTemplate.opsForZSet().add("zSetValue","A",1);
     *			redisTemplate.opsForZSet().add("zSetValue","B",3);
     *			redisTemplate.opsForZSet().add("zSetValue","C",2);
     *			redisTemplate.opsForZSet().add("zSetValue","D",5);
     *
     * @param key
     * @param values
     * @param sort 顺序集合中的排序权重
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:22:26
     * @version 1.0.0.1
     */
    public Boolean addZset(String key, String value, double sort) {
        return this.template.opsForZSet().add(key, value, sort);
    }


    /**
     * @description: 获取有序set集合中指定区间(set游标为依据)的元素。
     *	例如：
     *			Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
     *
     * @param key
     * @param start
     * @param end
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:59:46
     * @version 1.0.0.1
     */
    public Set<String> zsetRangeIndex(String key, long start, long end) {
        return this.template.opsForZSet().range(key, start, end);
    }

    /**
     * @description: 根据设置的score获取序set集合中的区间值。
     *
     * @param key
     * @param min
     * @param max
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:04:08
     * @version 1.0.0.1
     */
    public Set<String> zsetRangeByScore(String key, double min, double max) {
        return this.template.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * @description: 获取区间值的个数。
     *
     * @param key
     * @param min
     * @param max
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:08:35
     * @version 1.0.0.1
     */
    public Long zsetCount(String key, double min, double max) {
        return this.template.opsForZSet().count(key, min, max);
    }

    /**
     * @description: 获取元素的分值。
     *
     * @param key
     * @param value
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:10:31
     * @version 1.0.0.1
     */
    public Double zsetScore(String key, String value) {
        return this.template.opsForZSet().score(key, value);
    }

    /**
     * @description: 获取变量中元素的个数。
     *
     * @param key
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:12:19
     * @version 1.0.0.1
     */
    public Long zsetSize(String key) {
        return this.template.opsForZSet().zCard(key);
    }

    /**
     * @description: 批量移除元素根据元素值。
     *
     * @param key
     * @param values
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:13:51
     * @version 1.0.0.1
     */
    public Long zsetRemove(String key, Object... values) {
        return this.template.opsForZSet().remove(key, values);
    }

    /**
     * @description: 根据分值移除区间元素。
     *
     * @param key
     * @param min
     * @param max
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:15:57
     * @version 1.0.0.1
     */
    public Long zsetRemoveRangeByScor(String key, double min, double max) {
        return this.template.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * @description: 根据索引值移除区间元素。
     *
     * @param key
     * @param start 有序set集合索引
     * @param end    有序set集合索引
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:17:30
     * @version 1.0.0.1
     */
    public Long zsetRemoveRange(String key, long start, long end) {
        return this.template.opsForZSet().removeRange(key, start, end);
    }


    /**
     * @description: 获取redis key剩余过期时间
     *
     * @param key
     * @author wanghao
     * @date 2019年5月11日 上午9:44:03 
     * @version 1.0.0.1
     */
    public Long getExpire(String key) {
        return this.template.getExpire(key);
    }
}
















































































