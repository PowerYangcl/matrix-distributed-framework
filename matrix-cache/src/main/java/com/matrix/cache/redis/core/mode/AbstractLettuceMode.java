package com.matrix.cache.redis.core.mode;


import com.matrix.base.BaseClass;
import com.matrix.cache.inf.IRedisModel;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class AbstractLettuceMode extends BaseClass implements IRedisModel {
    protected String host;
    protected Integer port;
    protected String password;
    protected ClientResources resources = null;
    protected RedisURI redisUri = null;
	
	public AbstractLettuceMode() {
		this.setHost(this.getConfig("matrix-cache.lettuce_cache_url_" + this.getConfig("matrix-core.model")));
		this.setPort(Integer.valueOf(this.getConfig("matrix-cache.lettuce_port_url_" + this.getConfig("matrix-core.model")))); 
		this.setPassword( this.getConfig("matrix-cache.lettuce_password_url_" + this.getConfig("matrix-core.model")) ); 
	}

	public abstract Boolean close();
	
}



