package com.matrix.cache.redis.core.mode;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.matrix.pojo.entity.RedisEntity;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.Range;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterreplica.MasterReplica;
import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;
import io.lettuce.core.resource.DefaultClientResources;

/**
 * @description: 哨兵模式适配器
 * 		参考：https://www.cnblogs.com/kevingrace/p/9004460.html
 * 					https://my.oschina.net/spinachgit/blog/1982111
 * 
 * @author Yangcl
 * @date 2021-2-4 10:25:59
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.core.mode.LettuceCluster.java
 * @version 1.0.0.1
 */
public class LettuceSentinel extends AbstractLettuceMode {

	private RedisClient client = null; // 创建客户端
	private StatefulRedisMasterReplicaConnection<String, String> connection = null; // 创建线程安全的连接
	private RedisCommands<String, String> commands = null; // 创建同步命令
	private RedisAsyncCommands<String, String> asyncCommands = null;

	public LettuceSentinel() {
		super();
		this.setSentinelPassword( this.getConfig("matrix-cache.lettuce_sentinel_password_" + this.getConfig("matrix-core.model")) );   // 哨兵特有属性
		this.setSentinelMasterId( this.getConfig("matrix-cache.lettuce_sentinel_master_id_" + this.getConfig("matrix-core.model")) ); 
		redisUri = RedisURI.builder() // 创建单机连接的连接信息
				.withSentinel(host, port, sentinelPassword)		// sentinelPassword 可为空
				.withSentinelMasterId(sentinelMasterId)		// 哨兵Master的ID		文档写的模糊，暂时不知道是个什么，MMP查了好久。
				.withAuthentication(username, password)
				.withTimeout(Duration.of(30, ChronoUnit.SECONDS)) // 30秒超时
				.build();
		resources = DefaultClientResources.builder()
				.ioThreadPoolSize(4) // I/O线程数 default: Runtime.getRuntime().availableProcessors()
				.computationThreadPoolSize(4) // 任务线程数 default : Runtime.getRuntime().availableProcessors()
				.build();
		client = RedisClient.create(resources);

		connection = MasterReplica.connect(client, StringCodec.UTF8, redisUri);
		connection.setReadFrom(ReadFrom.REPLICA); // 只从从节点读取数据

		commands = connection.sync(); // 创建同步命令
		asyncCommands = connection.async();
	}

	public Boolean close() {
		try {
			if (connection != null && client != null) {
				connection.close(); // 5 关闭连接。
				client.shutdown(); // 6 关闭客户端。
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
		return commands.get(key);
	}

	/**
	 * @description: 设置一个值，永不过期。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午5:26:44
	 * @version 1.0.0.1
	 */
	public Boolean set(String key, String value) {
		return "ok".equalsIgnoreCase(commands.set(key, value));
	}

	/**
	 * @description: 将字符串值value关联到key，带自定义过期时间，单位：秒。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:19:51
	 * @version 1.0.0.1
	 */
	public Boolean set(String key, String value, long expire) {
		String flag = commands.set(key, value);
		commands.expire(key, expire);
		return "ok".equalsIgnoreCase(flag);
	}

	/**
	 * @description: 将字符串值value关联到key，默认过期时间30天。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:19:51
	 * @version 1.0.0.1
	 */
	public Boolean setWithDefExpire(String key, String value) {
		return this.set(key, value, 30 * 24 * 60 * 60);
	}

	/**
	 * @description:自定义redis key，设置超时时间
	 *
	 * @author Sjh
	 * @date 2019/4/10 16:29
	 * @version 1.0.1
	 */
	public void setKeyTimeout(String key, Long expire) {
		commands.expire(key, expire);
	}

	/**
	 * @description: 在原有的值基础上新增字符串到末尾。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:42:07
	 * @version 1.0.0.1
	 */
	public Integer appendStringInEnd(String key, String value) {
		Long append = commands.append(key, value); // 返回追加字符串的长度
		if (append == 0) {
			return 0;
		}
		return 1;
	}

	/**
	 * @description: 使用pipeline进行大批量数据插入 TODO 重点测试
	 * 
	 * @author Yangcl
	 * @date 2021-2-5 16:26:33
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Boolean batchInsert(List<RedisEntity> list, long expire) {
		try {
			asyncCommands.setAutoFlushCommands(false);
			List<RedisFuture<?>> futureList = Lists.newArrayList(); // perform a series of independent calls
			for (RedisEntity entity : list) {
				futureList.add(asyncCommands.set(entity.getKey(), entity.getValue()));
				futureList.add(asyncCommands.expire(entity.getKey(), expire));
			}
			asyncCommands.flushCommands(); // write all commands to the transport layer
			return LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futureList.toArray(new RedisFuture[futureList.size()]));
		} catch (Exception ex) {
			ex.printStackTrace(); // TODO 指定log格式
		}
		return false;
	}

	/**
	 * @description: 获取原来key键对应的值并重新赋新值。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午6:46:33
	 * @version 1.0.0.1
	 */
	public String getAndSet(String key, String value) {
		return commands.getset(key, value);
	}

	/**
	 * @description: 获取指定字符串的长度。
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午7:05:22
	 * @version 1.0.0.1
	 */
	public Long size(String key) {
		return commands.strlen(key);
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
	public Long increment(String key, Long amount, long expire) {
		Long incrby = commands.incrby(key, amount);
		commands.expire(key, expire);
		return incrby;
	}

	/**
	 * @description: 以增量+1的方式将long值存储在变量中，同时在指定时间内过期
	 *
	 * @param key     自增Key
	 * @param timeout 过期时间|如果为null，则持久生效
	 *
	 * @author Yangcl
	 * @date 2018年11月19日 下午18:36:26
	 * @version 1.0.0.1
	 */
	public Long incrementTimeout(String key, Long expire) {
		Long value = this.increment(key, 1L, expire);
		this.getLogger(null).sysoutInfo("获取缓存开始增量计次|Redis Key = " + key + "  当前增量值 = " + value, this.getClass());
		return value;
	}

	/**
	 * @description: 删除缓存信息，返回删除的数量
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:15:00
	 * @version 1.0.0.1
	 */
	public Long del(String key) {
		Long count = commands.del(key);
		return count;
	}

	/**
	 * @description: 根据多个key批量删除| TODO 不建议使用
	 * 
	 * @author Yangcl
	 * @date 2021-2-5 15:45:33
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Long deleteMore(String... keys) {
		return commands.del(keys);
	}

	/**
	 * @description: 通过缓存的删除关键字 批量删除缓存信息 | 最好不要使用 需要高版本的Redis，支持多线程，采用异步进行批量删除。
	 *               如缓存key：xs-MipLevel-2-1062173003451863043，用 MipLevel-2
	 *               做为关键字批量删除缓存
	 * 
	 * @author Yangcl
	 * @date 2021-2-4 10:33:25
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public Boolean batchDeleteByPrefix(String prefix) {
		List<String> list = null;
		try {
			List<RedisFuture<Long>> futureList = Lists.newArrayList(); // perform a series of independent calls
			asyncCommands.setAutoFlushCommands(false);
			do {
				list = commands.scan(new ScanArgs().limit(1000).match(prefix)).getKeys();
				if (list == null || list.size() == 0) {
					break;
				}
				for (String key : list) {
					futureList.add(asyncCommands.del(key));
				}
			} while (!CollectionUtils.isEmpty(list));

			if (CollectionUtils.isEmpty(futureList)) {
				return true;
			}
			asyncCommands.flushCommands(); // write all commands to the transport layer
			return LettuceFutures.awaitAll(10, TimeUnit.SECONDS,
					futureList.toArray(new RedisFuture[futureList.size()]));
		} catch (Exception ex) {
			ex.printStackTrace(); // TODO 指定log格式
		}
		return false;
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
		return commands.hget(key, field);
	}

	/**
	 * @description: 返回哈希表 key 中，所有的域和值。
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:52:39
	 * @version 1.0.0.1
	 */
	public Map<String, String> hgetAll(String key) {
		return commands.hgetall(key);
	}

	/**
	 * @description: 将哈希表key中的域 field 的值设为value。如果 key 不存在， 一个新的哈希表被创建并进行 HSET
	 *               操作。如果域 field 已经存在于哈希表中，旧值将被覆盖。 默认过期时间1天
	 *
	 *               例如：
	 *               redisTemplate.opsForHash().put("hashValue","map1","map1-1");
	 *               redisTemplate.opsForHash().put("hashValue","map2","map2-2");
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
		return this.hset(key, field, value, 24 * 60 * 60);
	}

	public Boolean hset(String key, String field, String value, long expire) {
		Boolean hset = commands.hset(key, field, value);
		commands.expire(key, expire);
		return hset;
	}

	/**
	 * @description: 以map集合的形式添加键值对，返回添加成功的条数。 例如： Map newMap = new HashMap();
	 *               newMap.put("map3","map3-3"); newMap.put("map5","map5-5");
	 *               redisTemplate.opsForHash().putAll("hashValue",newMap); Map map
	 *               = redisTemplate.opsForHash().entries("hashValue");
	 *
	 * @param key
	 * @param map
	 *
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:34:31
	 * @version 1.0.0.1
	 */
	public Long hsetAll(String key, Map<String, String> map) {
		return this.hsetAll(key, map, 24 * 60 * 60);
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
		Long hset = commands.hset(key, map); // the number of fields that were added.
		commands.expire(key, expire);
		return hset;
	}

	/**
	 * @description: 删除变量中的键值对，可以传入多个参数，删除多个键值对 例如：
	 *               hdel("hashValue","map-key1","map-key2");
	 *
	 * @param key
	 * @param field 建议使用字符串
	 * @return
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:39:20
	 * @version 1.0.0.1
	 */
	public Long hdel(String key, String... fields) {
		return commands.hdel(key, fields);
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
		return commands.hexists(key, hashKey);
	}

	/////////////////////////////////////////////////////////////////// 无序Set存储|交叉并操作暂未提供
	/////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////////
	/**
	 * @description: 添加一个set集合到redis中，默认过期时间1天。 返回添加成功的值的个数。 例如：
	 *               redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
	 * @param key
	 * @param values
	 *
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:22:26
	 * @version 1.0.0.1
	 */
	public Long addSet(String key, String... values) {
		return this.addSet(key, 24 * 60 * 60, values);
	}

	public Long addSet(String key, long expire, String... values) {
		Long sadd = commands.sadd(key, values); // the number of elements that were added to the set, not including all
												// the elements alreadypresent into the set
		commands.expire(key, expire);
		return sadd;
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
		return commands.smembers(key);
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
		return commands.scard(key);
	}

	/**
	 * @description: 检查给定的元素是否在set集合中。 例如： boolean isMember =
	 *               redisTemplate.opsForSet().isMember("setValue","A");
	 * @param key
	 * @param value
	 *
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:37:15
	 * @version 1.0.0.1
	 */
	public Boolean isInSet(String key, String value) {
		return commands.sismember(key, value);
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
		return commands.srem(key, members);
	}

	/////////////////////////////////////////////////////////////////// 有序Set存储|交叉并操作暂未提供 //////////////////////////////////////////////////////////////////////
	/**
	 * @description: 添加一个元素到指定的有序set集合中 例如：
	 *               redisTemplate.opsForZSet().add("zSetValue","A",1);
	 *               redisTemplate.opsForZSet().add("zSetValue","B",3);
	 *               redisTemplate.opsForZSet().add("zSetValue","C",2);
	 *               redisTemplate.opsForZSet().add("zSetValue","D",5);
	 *
	 * @param key
	 * @param values
	 * @param sort   顺序集合中的排序权重
	 *
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:22:26
	 * @version 1.0.0.1
	 */
	public Boolean addZset(String key, String value, double sort) {
		return this.addZset(key, value, sort, 24 * 60 * 60);
	}

	public Boolean addZset(String key, String value, double sort, long expire) {
		Long count = commands.zadd(key, sort, value);
		commands.expire(key, expire);
		return count > 0;
	}

	/**
	 * @description: 获取有序set集合中指定区间(set游标为依据)的元素。 例如：Set zSetValue =
	 *               redisTemplate.opsForZSet().range("zSetValue",0,-1);
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
		return new HashSet<String>(commands.zrange(key, start, end));
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
	public Set<String> zsetRangeByScore(String key, double min, double max) {
		return new HashSet<String>(commands.zrangebyscore(key, Range.create(min, max)));
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
		return commands.zcount(key, Range.create(min, max));
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
		return commands.zscore(key, value);
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
		return commands.zcard(key);
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
		return commands.zrem(key, values);
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
		return commands.zremrangebyscore(key, Range.create(min, max));
	}

	/**
	 * @description: 根据索引值移除区间元素。
	 *
	 * @param key
	 * @param start 有序set集合索引
	 * @param end   有序set集合索引
	 *
	 * @author Yangcl
	 * @date 2018年9月19日 上午11:17:30
	 * @version 1.0.0.1
	 */
	public Long zsetRemoveRange(String key, long start, long end) {
		return commands.zremrangebyrank(key, start, end);
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
		return commands.objectIdletime(key);
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
		return commands.zincrby(key, amount, member);
	}
}
