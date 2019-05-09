package com.matrix.load;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.ISysTableOpreateRecordMapper;
import com.matrix.pojo.entity.SysTableOpreateRecord;

public class InitTableOpreate extends BaseClass implements ILoadCache  {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private ISysTableOpreateRecordMapper sysTableOpreateRecordMapper;
	
	public String load(String key, String field) {
		SysTableOpreateRecord e_ = new SysTableOpreateRecord();
		e_.setCid(Long.valueOf(key.split("@")[1])); 
		e_.setMdvalue(key.split("@")[0]);
		e_.setTableName(key.split("@")[2]);
		
		String value = "";
		try {
			SysTableOpreateRecord e = sysTableOpreateRecordMapper.findByMdvalue(e_);
			if(e != null) {
				value = e.getZid() == null ? "error" : e.getZid().toString();
				if(!value.equals("error")) {
					launch.loadDictCache(DCacheEnum.TableOpreate , null).set(key , value , 30*60); // 保持半小时存在
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			value = "exception";
			throw new RuntimeException(ex);
		}
		
		return value;
	}

}










