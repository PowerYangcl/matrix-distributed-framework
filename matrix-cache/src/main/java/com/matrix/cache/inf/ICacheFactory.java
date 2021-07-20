package com.matrix.cache.inf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.matrix.pojo.entity.RedisEntity;

/**
 * @description: Redis集群模式适配器接口
 * 
 * @author Yangcl
 * @date 2021-2-4 10:29:15
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.inf.IRedisModel.java
 * @version 1.0.0.1
 */
public interface ICacheFactory {

	 /////////////////////////////////////////////////////////////////// 基础json //////////////////////////////////////////////////////////////////////
	/**
     * @description: 返回 key 所关联的字符串值。
     *
     * @param key 如果 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
     * @author Yangcl
     * @date 2018年9月18日 下午5:24:33
     * @version 1.0.0.1
     */
    public String get(String key);

    /**
     * @description: 设置一个值，永不过期。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午5:26:44
     * @version 1.0.0.1
     */
    public Boolean set(String key, String value);
    
    /**
     * @description: 将字符串值value关联到key，带自定义过期时间，单位：秒。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:19:51
     * @version 1.0.0.1
     */
    public Boolean set(String key, String value, long expire);

    /**
     * @description: 将字符串值value关联到key，默认过期时间30天。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:19:51
     * @version 1.0.0.1
     */
    public Boolean setWithDefExpire(String key, String value);

	/**
	 * @description:自定义 redis key 设置超时时间
	 * 
	 * @author Sjh
	 * @date 2019/4/10 16:29
	 * @version 1.0.1
	 */
    public void setKeyTimeout(String key, Long expire);

    /**
     * @description: 在原有的值基础上新增字符串到末尾。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:42:07
     * @version 1.0.0.1
     */
    public Integer appendStringInEnd(String key, String value);
    
    /**
     * @description: 使用pipeline进行大批量数据插入
     * 
     * @author Yangcl
     * @date 2021-2-5 16:26:33
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Boolean batchInsert(List<RedisEntity> list, long expire);

    /**
     * @description: 获取原来key键对应的值并重新赋新值。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:46:33
     * @version 1.0.0.1
     */
    public String getAndSet(String key, String value);

    /**
     * @description: 获取指定字符串的长度。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午7:05:22
     * @version 1.0.0.1
     */
    public Long size(String key);

    /**
     * @description: 以增量的方式将long值存储在变量中，返回增加后的结果值。
     *
     * @param key
     * @param amount 增量值，1、2、3等等
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:06:42
     * @version 1.0.0.1
     */
    public Long increment(String key, Long amount , long expire);

    /**
     * @description: 以增量+1的方式将long值存储在变量中，同时在指定时间内过期
     *
     * @param key 自增Key
     * @param expire 过期时间|如果为null，则持久生效
     *
     * @author Yangcl
     * @date 2018年11月19日 下午18:36:26
     * @version 1.0.0.1
     */
    public Long incrementTimeout(String key, Long expire);

    /**
     * @description: 删除缓存信息
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:15:00
     * @version 1.0.0.1
     */
    public Long del(String key);
    
    /**
     * @description: 根据多个key批量删除
     * 
     * @author Yangcl
     * @date 2021-2-5 15:45:33
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Long deleteMore(String ... keys);

    /**
     * @description: 通过缓存的删除关键字 批量删除缓存信息 | 最好不要使用
     * 		需要高版本的Redis，支持多线程，采用异步进行批量删除。
     * 		如缓存key：xs-MipLevel-2-1062173003451863043，用 MipLevel-2 做为关键字批量删除缓存
     * 
     * @author Yangcl
     * @date 2021-2-4 10:33:25
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Boolean batchDeleteByPrefix(String prefix);


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
    public String hget(String key, String field);

    /**
     * @description: 返回哈希表 key 中，所有的域和值。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月18日 下午8:52:39
     * @version 1.0.0.1
     */
    public Map<String, String> hgetAll(String key);

    /**
     * @description: 将哈希表key中的域 field 的值设为value。如果 key 不存在，
     * 		一个新的哈希表被创建并进行 HSET 操作。如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *			默认过期时间1天
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
    public Boolean hset(String key, String field, String value);
    
    
    public Boolean hset(String key, String field, String value, long expire);

    /**
     * @description: 以map集合的形式添加键值对，返回添加成功的条数。
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
    public Long hsetAll(String key, Map<String, String> map);
    
    /**
     * @description: 返回添加成功的条数
     * 
     * @author Yangcl
     * @date 2021-2-7 20:43:35
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Long hsetAll(String key, Map<String, String> map, long expire);

    /**
     * @description: 删除变量中的键值对，可以传入多个参数，删除多个键值对
     * 例如：
     *			hdel("hashValue","map-key1","map-key2");
     *
     * @param key
     * @param field  建议使用字符串
     * @return
     * @author Yangcl
     * @date 2018年9月18日 下午8:39:20
     * @version 1.0.0.1
     */
    public Long hdel(String key, String... fields);

    /**
     * @description: 判断变量中是否有指定的map键。
     *
     * @param key
     * @param hashKey map中的key
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:46:24
     * @version 1.0.0.1
     */
    public Boolean hkeyExist(String key, String hashKey);


    /////////////////////////////////////////////////////////////////// 无序Set存储|交叉并操作暂未提供 //////////////////////////////////////////////////////////////////////
    /**
     * @description: 添加一个set集合到redis中，默认过期时间1天。
     * 		返回添加成功的值的个数。
     *	例如：
     *			redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
     * @param key
     * @param values
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:22:26
     * @version 1.0.0.1
     */
    public Long addSet(String key, String... values);
    
    public Long addSet(String key, long expire, String... values);

    /**
     * @description: 获取变量中的值。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:27:10
     * @version 1.0.0.1
     */
    public Set<String> sget(String key);

    /**
     * @description: 获取set集合的长度
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:30:26
     * @version 1.0.0.1
     */
    public Long setLength(String key);

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
    public Boolean isInSet(String key, String value);

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
    public Long setElementRemove(String key, String... members);

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
    public Boolean addZset(String key, String value, double sort);
    
    public Boolean addZset(String key, String value, double sort , long expire);


    /**
     * @description: 获取有序set集合中指定区间(set游标为依据)的元素。
     *	例如：Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
     *
     * @param key
     * @param start
     * @param end
     *
     * @author Yangcl
     * @date 2018年9月19日 上午10:59:46
     * @version 1.0.0.1
     */
    public Set<String> zsetRangeIndex(String key, long start, long end);

    /**
     * @description: 根据设置的score获取序set集合中的区间值；可依据此命令实现延时队列。
     * 
     * @param min score min
     * @param max score max
     * 
     * @author Yangcl
     * @date 2021-2-8 11:21:45
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Set<String> zsetRangeByScore(String key, double min, double max);

    /**
     * @description: 获取区间值的个数。
     *
     * @param key
     * @param min score min
     * @param max score max
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:08:35
     * @version 1.0.0.1
     */
    public Long zsetCount(String key, double min, double max);

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
    public Double zsetScore(String key, String value);

    /**
     * @description: 获取变量中元素的个数。
     *
     * @param key
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:12:19
     * @version 1.0.0.1
     */
    public Long zsetSize(String key);

    /**
     * @description: 批量移除元素根据元素值，返回移除元素的数量。
     *
     * @param key
     * @param values
     *
     * @author Yangcl
     * @date 2018年9月19日 上午11:13:51
     * @version 1.0.0.1
     */
    public Long zsetRemove(String key, String... values);

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
    public Long zsetRemoveRangeByScor(String key, double min, double max);

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
    public Long zsetRemoveRange(String key, long start, long end);


    /**
     * @description: 获取redis key剩余过期时间
     *
     * @param key
     * @author wanghao
     * @date 2019年5月11日 上午9:44:03 
     * @version 1.0.0.1
     */
    public Long getExpire(String key);
    
    /**
     * @description: 在有序Set集合中，递增指定的某个元素的score值
     * 
     * @author Yangcl
     * @date 2021-2-8 15:00:10
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Double zincrby(String key, String member, double amount);
    
    /**
     * @description: 关闭连接
     * 
     * @author Yangcl
     * @date 2021-2-18 18:28:22
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Boolean close();
}































