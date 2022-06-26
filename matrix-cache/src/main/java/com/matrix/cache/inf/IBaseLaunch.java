package com.matrix.cache.inf;

import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;

public interface IBaseLaunch<T> {
	
	public T loadServiceCache(CachePrefix enum_ , String load);
	
	public T loadDictCache(CachePrefix enum_ , String load); 
	
	
//	public T loadServiceCache(<? extends CachePrefix> prefix , String load);
	
//	public T loadDictCache(E prefix , String load); 
}


// E extends CachePrefix