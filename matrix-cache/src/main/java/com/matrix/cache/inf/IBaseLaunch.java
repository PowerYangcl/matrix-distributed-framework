package com.matrix.cache.inf;

import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;

public interface IBaseLaunch<T> {
	
	public T loadServiceCache(SCacheEnum enum_ , String load);
	
	public T loadDictCache(DCacheEnum enum_ , String load); 
	
	
	public T loadServiceCache(String prefix , String load);
	
	public T loadDictCache(String prefix , String load); 
}


