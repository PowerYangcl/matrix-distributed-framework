package com.matrix.cache.redis.core;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;

import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.RedisEntity;
import com.matrix.support.RedissonLock;
import com.matrix.system.cache.PowerCache;

/**
 * @descriptions 针对缓存提供基本的增删改查操作 
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年12月12日 下午10:50:56
 * @version 1.0.1
 */
public class RedisFactory implements ICacheFactory{  

	private String baseKey = "";
	private String load = "";  // ILoadCache		
	public RedisFactory(String sKey , String load) {
		if(StringUtils.isBlank(load)) {
			load = "";
		}
		baseKey = sKey;
		this.load = "com.matrix.load." + load;
	}

	
	/**
	 * @description: 返回基础的key前缀
	 *
	 * @author Yangcl
	 * @date 2018年12月17日 下午10:38:35 
	 * @version 1.0.0.1
	 */
	public String getBaseKey() {
		return baseKey;
	}
	
	/**
	 * @description: 判断ehcache中是否包含这个Key
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月19日 下午19:03:37 
	 * @version 1.0.0.1
	 */
	private boolean containsKey(String key) {
		return PowerCache.getInstance().getCache("power").isKeyInCache(key);
	}
	
	/**
	 * @description: 从ehcache中直接获取缓存对应的值
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月19日 下午19:04:09 
	 * @version 1.0.0.1
	 */
	private String findInEhcache(String key) {
		if(!this.containsKey(key)) {
			return "KEY-404";
		}
		return (String) PowerCache.getInstance().getCache("power").get(key).getObjectValue();
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
	public String get2(String key) {
		String value = RedisTemplateLettuce.getInstance().get(baseKey + key);
		if(StringUtils.isBlank(value)) {
			RLock disLock = RedissonLock.getInstance().getRedissonClient().getLock("lock-" + baseKey + key);  // 添加分布式锁
			try {
			    // 尝试获取分布式锁|20秒内获取不到锁则直接返回； 第二个参数是60秒后强制释放
				boolean isLock = disLock.tryLock(20L, 60L, TimeUnit.SECONDS);
			    if (isLock) {
			    	value = RedisTemplateLettuce.getInstance().get(baseKey + key);
					if(StringUtils.isBlank(value)) {
						if(this.load.length() == 16) {
							return "";
						}
						try {
							Class<?> clazz = Class.forName(load);   
							if (clazz != null && clazz.getDeclaredMethods() != null){
								// Redis 开始增量计次：20次。如果10分钟内20次连续查询数据库，则10分钟内返回空
								Long count = RedisTemplateLettuce.getInstance().incrementTimeout(baseKey + key, 10*60L);
								if(count >= 20) {
									return "";
								}
								@SuppressWarnings("unchecked")
								ILoadCache<String> cache = (ILoadCache<String>) clazz.newInstance();
								return cache.load(key , "");
							}else {
								return "";
							}
						}catch (Exception e) {
							e.printStackTrace();
							return "";
						}    
					}else {
						return value;
					}
			    }
			} catch (Exception e) {
				e.printStackTrace(); 
			} finally {   // 无论如何, 最后都要解锁
			    disLock.unlock();
			}
			
			return value;
		}else {
			return value;
		}
	}
	
	public String get(String key) {
		String value = RedisTemplateLettuce.getInstance().get(baseKey + key);
		if(StringUtils.isBlank(value)) {
			try {
			    // 尝试获取分布式锁|20秒内获取不到锁则直接返回； 第二个参数是60秒后强制释放
			    synchronized (Object.class) {
			    	value = RedisTemplateLettuce.getInstance().get(baseKey + key);
					if(StringUtils.isBlank(value)) {
						if(this.load.length() == 16) {
							return "";
						}
						try {
							Class<?> clazz = Class.forName(load);   
							if (clazz != null && clazz.getDeclaredMethods() != null){
								// Redis 开始增量计次：20次。如果10分钟内20次连续查询数据库，则10分钟内返回空
								Long count = RedisTemplateLettuce.getInstance().incrementTimeout(baseKey + key, 10*60L);
								if(count >= 20) {
									return "";
								}
								@SuppressWarnings("unchecked")
								ILoadCache<String> cache = (ILoadCache<String>) clazz.newInstance();
								return cache.load(key , "");
							}else {
								return "";
							}
						}catch (Exception e) {
							e.printStackTrace();
							return "";
						}    
					}else {
						return value;
					}
			    }
			} catch (Exception e) {
				e.printStackTrace(); 
			} finally {   // 无论如何, 最后都要解锁
			}
			
			return value;
		}else {
			return value;
		}
	}
	
    /**
     * @description: 设置一个值，永不过期。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午5:26:44
     * @version 1.0.0.1
     */
    public Boolean set(String key, String value) {
    	return RedisTemplateLettuce.getInstance().set(baseKey + key, value);
    }
    
    /**
     * @description: 将字符串值value关联到key，带自定义过期时间，单位：秒。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:19:51
     * @version 1.0.0.1
     */
    public Boolean set(String key, String value, long expire) {
    	return RedisTemplateLettuce.getInstance().set(baseKey + key, value, expire);
    }

    /**
     * @description: 将字符串值value关联到key，默认过期时间30天。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:19:51
     * @version 1.0.0.1
     */
    public Boolean setWithDefExpire(String key, String value) {
    	return RedisTemplateLettuce.getInstance().setWithDefExpire(baseKey + key, value);
    }

	/**
	 * @description:自定义 redis key 设置超时时间
	 * 
	 * @author Sjh
	 * @date 2019/4/10 16:29
	 * @version 1.0.1
	 */
    public void setKeyTimeout(String key, Long expire) {
    	RedisTemplateLettuce.getInstance().setKeyTimeout(baseKey + key, expire);
    }

    /**
     * @description: 在原有的值基础上新增字符串到末尾。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:42:07
     * @version 1.0.0.1
     */
    public Integer appendStringInEnd(String key, String value) {
    	return RedisTemplateLettuce.getInstance().appendStringInEnd(baseKey + key, value);
    }
    
    /**
     * @description: 使用pipeline进行大批量数据插入
     * 
     * @author Yangcl
     * @date 2021-2-5 16:26:33
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Boolean batchInsert(List<RedisEntity> list, long expire) {
    	return RedisTemplateLettuce.getInstance().batchInsert(list, expire);
    }

    /**
     * @description: 获取原来key键对应的值并重新赋新值。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午6:46:33
     * @version 1.0.0.1
     */
    public String getAndSet(String key, String value) {
    	return RedisTemplateLettuce.getInstance().getAndSet(baseKey + key, value);
    }

    /**
     * @description: 获取指定字符串的长度。
     *
     * @author Yangcl
     * @date 2018年9月18日 下午7:05:22
     * @version 1.0.0.1
     */
    public Long size(String key) {
    	return RedisTemplateLettuce.getInstance().size(baseKey + key);
    }

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
    public Long increment(String key, Long amount , long expire) {
    	return RedisTemplateLettuce.getInstance().increment(baseKey + key, amount, expire);
    }

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
    public Long incrementTimeout(String key, Long expire) {
    	return RedisTemplateLettuce.getInstance().incrementTimeout(baseKey + key, expire);
    }

    /**
     * @description: 删除缓存信息
     *
     * @author Yangcl
     * @date 2018年9月18日 下午8:15:00
     * @version 1.0.0.1
     */
    public Long del(String key) {
    	return RedisTemplateLettuce.getInstance().del(baseKey + key);
    }
    
    /**
     * @description: 根据多个key批量删除
     * 
     * @author Yangcl
     * @date 2021-2-5 15:45:33
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Long deleteMore(String ... keys) {
    	return RedisTemplateLettuce.getInstance().deleteMore(keys);
    }

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
    public Boolean batchDeleteByPrefix(String prefix) {
    	return RedisTemplateLettuce.getInstance().batchDeleteByPrefix(prefix);
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
    public String hget(String key, String field) {
    	return RedisTemplateLettuce.getInstance().hget(baseKey + key, field);
    }

    /**
     * @description: 返回哈希表 key 中，所有的域和值。
     *
     * @param key
     * @author Yangcl
     * @date 2018年9月18日 下午8:52:39
     * @version 1.0.0.1
     */
    public Map<String, String> hgetAll(String key){
    	return RedisTemplateLettuce.getInstance().hgetAll(baseKey + key);
    }

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
    public Boolean hset(String key, String field, String value) {
    	return RedisTemplateLettuce.getInstance().hset(baseKey + key, field, value);
    }
    
    
    public Boolean hset(String key, String field, String value, long expire) {
    	return RedisTemplateLettuce.getInstance().hset(baseKey + key, field, value);
    }

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
    public Long hsetAll(String key, Map<String, String> map) {
    	return RedisTemplateLettuce.getInstance().hsetAll(baseKey + key, map);
    }
    
    /**
     * @description: 返回添加成功的条数
     * 
     * @author Yangcl
     * @date 2021-2-7 20:43:35
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Long hsetAll(String key, Map<String, String> map, long expire) {
    	return RedisTemplateLettuce.getInstance().hsetAll(baseKey + key, map, expire);
    }

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
    public Long hdel(String key, String... fields) {
    	return RedisTemplateLettuce.getInstance().hdel(baseKey + key, fields);
    }

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
    public Boolean hkeyExist(String key, String hashKey) {
    	return RedisTemplateLettuce.getInstance().hkeyExist(baseKey + key, hashKey);
    }


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
    public Long addSet(String key, String... values) {
    	return RedisTemplateLettuce.getInstance().addSet(baseKey + key, values);
    }
    
    public Long addSet(String key, long expire, String... values) {
    	return RedisTemplateLettuce.getInstance().addSet(baseKey + key, expire, values);
    }

    /**
     * @description: 获取Set集合变量中的值。如果Set == null or Set.size() == 0，依然返回空
     *			TODO 没加分布式锁，后期改进
     * @param key
     * @author Yangcl
     * @date 2018年9月19日 上午10:27:10
     * @version 1.0.0.1
     */
    public Set<String> sget(String key){
    	Set<String> value = RedisTemplateLettuce.getInstance().sget(baseKey + key);
        if (value == null || value.size() == 0) {
            synchronized (CacheLaunch.class) {
                value =  RedisTemplateLettuce.getInstance().sget(baseKey + key);
                if (value == null || value.size() == 0) {
                    if (this.load.length() == 16) {
                        return null;
                    }
                    try {
                        Class<?> clazz = Class.forName(load);
                        if (clazz != null && clazz.getDeclaredMethods() != null) {
                            // Redis 开始增量计次：20次。如果10分钟内20次连续查询数据库，则10分钟内返回空
                            Long count = RedisTemplateLettuce.getInstance().incrementTimeout(baseKey + key, 10 * 60L);
                            if (count >= 20) {
                                return null;
                            }
                            @SuppressWarnings("unchecked")
                            ILoadCache<Set<String>> cache = (ILoadCache<Set<String>>) clazz.newInstance();
                            return cache.load(key, "");
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return value;
                }
            }
        } else {
            return value;
        }
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
    	return RedisTemplateLettuce.getInstance().setLength(baseKey + key);
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
    	return RedisTemplateLettuce.getInstance().isInSet(baseKey + key, value);
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
    public Long setElementRemove(String key, String... members) {
    	return RedisTemplateLettuce.getInstance().setElementRemove(baseKey + key, members);
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
    	return RedisTemplateLettuce.getInstance().addZset(baseKey + key, value, sort);
    }
    
    public Boolean addZset(String key, String value, double sort , long expire) {
    	return RedisTemplateLettuce.getInstance().addZset(baseKey + key, value, sort, expire);
    }


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
    public Set<String> zsetRangeIndex(String key, long start, long end){
    	return RedisTemplateLettuce.getInstance().zsetRangeIndex(baseKey + key, start, end);
    }

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
    public Set<String> zsetRangeByScore(String key, double min, double max){
    	return RedisTemplateLettuce.getInstance().zsetRangeByScore(baseKey + key, min, max);
    }

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
    public Long zsetCount(String key, double min, double max) {
    	return RedisTemplateLettuce.getInstance().zsetCount(baseKey + key, min, max);
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
    	return RedisTemplateLettuce.getInstance().zsetScore(baseKey + key, value);
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
    	return RedisTemplateLettuce.getInstance().zsetSize(baseKey + key);
    }

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
    public Long zsetRemove(String key, String... values) {
    	return RedisTemplateLettuce.getInstance().zsetRemove(baseKey + key, values);
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
    	return RedisTemplateLettuce.getInstance().zsetRemoveRangeByScor(baseKey + key, min, max);
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
    	return RedisTemplateLettuce.getInstance().zsetRemoveRange(baseKey + key, start, end);
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
    	return RedisTemplateLettuce.getInstance().getExpire(baseKey + key);
    }
    
    /**
     * @description: 在有序Set集合中，递增指定的某个元素的score值
     * 
     * @author Yangcl
     * @date 2021-2-8 15:00:10
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Double zincrby(String key, String member, double amount) {
    	return RedisTemplateLettuce.getInstance().zincrby(baseKey + key, member, amount);
    }
    
    /**
     * @description: 关闭连接
     * 
     * @author Yangcl
     * @date 2021-2-18 18:28:22
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Boolean close() {
    	return RedisTemplateLettuce.getInstance().close();
    }

}



















































