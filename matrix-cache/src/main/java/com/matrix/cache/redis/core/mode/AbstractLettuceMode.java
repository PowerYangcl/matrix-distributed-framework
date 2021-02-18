package com.matrix.cache.redis.core.mode;


import com.matrix.base.BaseClass;
import com.matrix.cache.inf.ICacheFactory;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class AbstractLettuceMode extends BaseClass implements ICacheFactory {
    protected String host;
    protected Integer port;
    protected String username;
    protected CharSequence password;
    protected ClientResources resources = null;
    protected RedisURI redisUri = null;
    protected CharSequence sentinelPassword;		// sentinel服务器密码
    protected String sentinelMasterId;					// 这特么到底是个啥？大爷的。。lettuce文档里也没写明白，真几把der。是runid啊还是特么master-group-name？？
	
	public AbstractLettuceMode() {
		this.setHost(this.getConfig("matrix-cache.lettuce_cache_url_" + this.getConfig("matrix-core.model")));
		this.setPort(Integer.valueOf(this.getConfig("matrix-cache.lettuce_port_" + this.getConfig("matrix-core.model")))); 
		this.setUsername( this.getConfig("matrix-cache.lettuce_username_" + this.getConfig("matrix-core.model"))  );
		this.setPassword( this.getConfig("matrix-cache.lettuce_password_" + this.getConfig("matrix-core.model")) ); 
	}

	public abstract Boolean close();
	
}



