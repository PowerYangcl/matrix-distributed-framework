package com.matrix.cache.redis.core;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;

import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.ICacheFactory;
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
	public String get(String key) {
		String value = RedisTemplate.getInstance().get(baseKey + key);
		if(StringUtils.isBlank(value)) {
			RLock disLock = RedissonLock.getInstance().getRedissonClient().getLock("lock-" + baseKey + key);  // 添加分布式锁
			try {
			    // 尝试获取分布式锁|20秒内获取不到锁则直接返回； 第二个参数是60秒后强制释放
				boolean isLock = disLock.tryLock(20L, 60L, TimeUnit.SECONDS);
			    if (isLock) {
			    	value = RedisTemplate.getInstance().get(baseKey + key);
					if(StringUtils.isBlank(value)) {
						if(this.load.length() == 16) {
							return "";
						}
						try {
							Class<?> clazz = Class.forName(load);   
							if (clazz != null && clazz.getDeclaredMethods() != null){
								// Redis 开始增量计次：20次。如果10分钟内20次连续查询数据库，则10分钟内返回空
								Long count = RedisTemplate.getInstance().incrementTimeout(baseKey + key, 10*60L);
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

	/**
	 * @description: 将字符串值 value 关联到 key 。
	 *		对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
	 *
	 * @param key	如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param value
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午5:26:44 
	 * @version 1.0.0.1
	 */
	public void set(String key, String value) {
		RedisTemplate.getInstance().set(baseKey + key, value);
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
		RedisTemplate.getInstance().set(baseKey + key, value, timeout);
	}
	
	/**
     * @description: 调用底层jedis命令实现StringRedisTemplate中没有的setnx方法
     * 			通常这个方法作为Redis高性能分布式锁来使用。
     *
     * @param key					使用key来当锁，因为key是唯一的
     * @param requestFlag    Redis对应的Value，这里表达为请求加锁者提供的标识，为分布式锁的可靠性提供依据
     * @param timeout				锁的过期时间，需要根据实际业务场景定义，单位秒
     * @param nxxx					通常填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作
     * @param expx					通常传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由 timeout 参数决定。
     * @author Yangcl
     * @date 2018年12月17日 下午3:57:28 
     * @version 1.0.0.1
     */
	public Boolean setnx(final String key, final String requestFlag, final Long timeout, final String nxxx , final String expx) {
		return RedisTemplate.getInstance().setnx(key, requestFlag, timeout, nxxx, expx);
	}
	
	/**
	 * @description: 返回 key 在一级缓存中所关联的字符串值，如果没取到则从二级缓存中尝试获取
	 *
	 * @param key 如果 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
	 * @author Yangcl
	 * @date 2018年11月19日 上午11:27:53 
	 * @version 1.0.0.1
	 */
	public String powerGet(String key) {
		String value = this.findInEhcache(baseKey + key);
		if(value.equals("KEY-404")) {
			return "";
		}
		if(StringUtils.isBlank(value)) {
			synchronized (RedisFactory.class) {
				value = this.findInEhcache(baseKey + key);
				if(StringUtils.isBlank(value)) {
					value = this.get(key);  // 取出的值还是有可能为空
					if(StringUtils.isNotBlank(value)) {
						this.powerSet(key, value);
					}
				}
				return value;
			}
		}
		return value;
	}

	/**
	 * @description: 设置一级缓存|将字符串值 value 关联到 key
	 *
	 * @param key
	 * @param value
	 * @author Yangcl
	 * @date 2018年11月19日 上午11:29:22 
	 * @version 1.0.0.1
	 */
	public void powerSet(String key, String value) {
		PowerCache.getInstance().reset("power", baseKey + key, value);
	}
	
	/**
	 * @description: 设置一级缓存，同时放入Redis|将字符串值 value 关联到 key
	 *
	 * @param key
	 * @param value
	 * @param timeout 
	 * @author Yangcl
	 * @date 2018年11月19日 下午3:46:14 
	 * @version 1.0.0.1
	 */
	public void powerSet(String key, String value , long timeout) {
		PowerCache.getInstance().reset("power", baseKey + key, value);
		RedisTemplate.getInstance().set(baseKey + key, value, timeout);
	}

	/**
	 * @description: 将字符串值 value 关联到 key 。带默认超时时间30天
	 *
	 * @param key
	 * @param value
	 * @param timeout 
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:19:51 
	 * @version 1.0.0.1
	 */
	public void setDefalutTimeout(String key, String value) {
		RedisTemplate.getInstance().setDefalutTimeout(baseKey + key, value); 
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
    public void setKeyTimeout(String key, Long expireTime) {
        RedisTemplate.getInstance().setKeyTimeout(key,expireTime);
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
		return RedisTemplate.getInstance().appendStringInEnd(baseKey + key, value);
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
		return RedisTemplate.getInstance().getAndSet(baseKey + key, value);
	}

	/**
	 * @description: 获取指定字符串的长度。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午7:05:22 
	 * @version 1.0.0.1
	 */
	public Long size(String key) {
		return RedisTemplate.getInstance().size(baseKey + key); 
	}

	/**
	 * @description: 以增量的方式将long值存储在变量中。
	 *
	 * @param key
	 * @param delta  增量值，1、2、3等等
	 * 
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:06:42 
	 * @version 1.0.0.1
	 */
	public Long increment(String key, Long delta , long timeout) {
//		this.get(key);  // 缓存没有从数据库里拿一下
		return RedisTemplate.getInstance().increment(baseKey + key, delta, timeout); 
	}

	/**
	 * @description: 删除缓存信息
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:15:00 
	 * @version 1.0.0.1
	 */
	public void del(String key) {
		RedisTemplate.getInstance().del(baseKey + key); 
	}

    /**
     *@description:通过缓存的删除关键字 批量删除缓存信息
     *
     *@param  preKey 如缓存key：xs-MipLevel-2-1062173003451863043，用 MipLevel-2 做为关键字批量删除缓存
     *@author Sjh
     *@date 2018/11/21 10:44
     *@version 1.0.0.1
     */
	public void batchDel(String key) {
		RedisTemplate.getInstance().batchDelWithPre(baseKey + key);
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
	public Object hget(String key, String field) {
		return RedisTemplate.getInstance().hget(baseKey + key, field);
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
		return RedisTemplate.getInstance().hgetAll(baseKey + key);
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
	public void hset(String key, String field, String value, Long expireTime) {
		RedisTemplate.getInstance().hset(baseKey + key, field, value);
		setKeyTimeout(baseKey + key, expireTime);
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
	public void hsetAll(String key, Map<String, Object> map, Long expireTime) {
		RedisTemplate.getInstance().hsetAll(baseKey + key, map);
		setKeyTimeout(baseKey + key, expireTime);
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
		return RedisTemplate.getInstance().hdel(baseKey + key, field); 
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
		return RedisTemplate.getInstance().hkeyExist(baseKey + key, hashKey); 
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
		return RedisTemplate.getInstance().addSet(baseKey + key, values);
	}
	
	/**
	 * @description: 添加一个set集合到redis中，同时设置过期时间
	 *	例如：
	 *			redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");  
	 * @param key
	 * @param values 
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:22:26 
	 * @version 1.0.0.1
	 */
	public Long addSet(String key , Integer expireTime , String... values) {
		Long value = RedisTemplate.getInstance().addSet(baseKey + key, values);
		this.setKeyTimeout(key, Long.valueOf(expireTime));
		return value;
	}

	/**
	 * @description: 获取Set集合变量中的值。如果Set == null or Set.size() == 0，依然返回空
	 *
	 * @param key 
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:27:10 
	 * @version 1.0.0.1
	 */
	public Set<String> sget(String key) {
		Set<String> value = RedisTemplate.getInstance().sget(baseKey + key);
        if (value == null || value.size() == 0) {
            synchronized (CacheLaunch.class) {
                value =  RedisTemplate.getInstance().sget(baseKey + key);
                if (value == null || value.size() == 0) {
                    if (this.load.length() == 16) {
                        return null;
                    }
                    try {
                        Class<?> clazz = Class.forName(load);
                        if (clazz != null && clazz.getDeclaredMethods() != null) {
                            // Redis 开始增量计次：20次。如果10分钟内20次连续查询数据库，则10分钟内返回空
                            Long count = RedisTemplate.getInstance().incrementTimeout(baseKey + key, 10 * 60L);
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
		return RedisTemplate.getInstance().setLength(baseKey + key);
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
		return RedisTemplate.getInstance().setRandomMember(baseKey + key); 
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
		return RedisTemplate.getInstance().setRandomMembers(baseKey + key, count);
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
		return RedisTemplate.getInstance().isInSet(baseKey + key, value); 
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
		return RedisTemplate.getInstance().setElementRemove(baseKey + key, objects);
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
		return RedisTemplate.getInstance().addZset(baseKey + key, value, sort);
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
		return RedisTemplate.getInstance().zsetRangeIndex(baseKey + key, start, end);
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
		return RedisTemplate.getInstance().zsetRangeByScore(baseKey + key, min, max);
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
		return RedisTemplate.getInstance().zsetCount(baseKey + key, min, max); 
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
		return RedisTemplate.getInstance().zsetScore(baseKey + key, value);
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
		return RedisTemplate.getInstance().zsetSize(baseKey + key);
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
		return RedisTemplate.getInstance().zsetRemove(baseKey + key, values);
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
		return RedisTemplate.getInstance().zsetRemoveRangeByScor(baseKey + key, min, max);
	}

	/**
	 * @description: 根据索引值移除区间元素。
	 *
	 * @param key
	 * @param start 有序set集合索引
	 * @param end	有序set集合索引
	 * 
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:17:30 
	 * @version 1.0.0.1
	 */
	public Long zsetRemoveRange(String key, long start, long end) {
		return RedisTemplate.getInstance().zsetRemoveRange(baseKey + key, start, end);
	}

	/**
	 * @description: 获取key的过期剩余时间 单位毫秒
	 *
	 * @param key
	 * @return 
	 * @author wanghao
	 * @date 2019年5月11日 上午9:47:00 
	 * @version 1.0.0.1
	 */
	public Long getExpire(String key){
		return RedisTemplate.getInstance().getExpire(key);
	}

}



















































