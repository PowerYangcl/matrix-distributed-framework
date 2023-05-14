package com.matrix.cache.redis.core.mode;



import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.cache.inf.ICacheFactory;

import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class AbstractLettuceMode extends BaseClass implements ICacheFactory {
	
	@Inject
	private RedisConnectionProperty redisConnectionProperty;

    protected String host;
    protected Integer port;
    protected String username;
    protected CharSequence password;
    protected String sentinelMasterId;					// 这特么到底是个啥？大爷的。。lettuce文档里也没写明白，真几把der。是runid啊还是特么master-group-name？？
    protected CharSequence sentinelPassword;		// sentinel服务器密码
    protected Integer defalutDb;
	
    protected ClientResources resources = null;
    protected RedisURI redisUri = null;
    

    public AbstractLettuceMode() {
    	this.host = redisConnectionProperty.getHost();
    	this.port = Integer.valueOf(redisConnectionProperty.getPort());
    	this.username = redisConnectionProperty.getName();
    	this.password = redisConnectionProperty.getPassword();
    	this.sentinelMasterId = redisConnectionProperty.getSentinelMasterId();
    	this.sentinelPassword = redisConnectionProperty.getSentinelPassword();
    	this.defalutDb = Integer.valueOf(redisConnectionProperty.getDefalutDb());
	}
    
    

	/**
	 * @description: 如果commands和asyncCommands一起用需要重置资源链接
	 * 
	 * @author Yangcl
	 * @date 2021-3-16 15:57:42
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	public void reset() {
		this.close();
		this.init();
	}
	
	public abstract void init();
	
	public abstract Boolean close();
}



