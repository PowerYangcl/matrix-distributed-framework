package com.matrix.map;  

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @descriptions come from hjy , lost the auther's name
 *
 * @date 2016年11月12日 上午12:43:22
 * @version 1.0.1
 */
public class MStringMap extends MObjMap<String, String> implements Map<String,String> {
 
	private static final long serialVersionUID = -1113400831514224701L;
	
	
	public MStringMap(){
	}
	
	
	public MStringMap(Map<String, Object> mData){
		for(String sKyey:mData.keySet()) {
			this.put(sKyey, mData.get(sKyey).toString());
		}
	}
	
	
	/**
	 * 转换主键到数组
	 * @return
	 */
	public String[] convertKeysToStrings() {
		return this.getKeys().toArray(new String[]{});
	}
	
	
	

}
