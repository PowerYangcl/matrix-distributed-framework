package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindApiInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 5363258315590489851L;

	private IBaseLaunch<ICacheFactory> launch;
	
	private McUserInfoView userCache;
	
	private String target;
	
	private String record;		// 可利用的返回值。
	
	public Result<AcApiInfoCache> validate(IBaseLaunch<ICacheFactory> launch){
		if(StringUtils.isBlank(target)) {	// 600010076=系统接口标识"target"参数不得为空!
			return Result.ERROR(this.getInfo(600010076), ResultCode.INVALID_ARGUMENT); 
		}
		String record = launch.loadDictCache(CachePrefix.ApiInfo , "ApiInfoInit").get(target);
		if(StringUtils.isBlank(record)) { // 600010077=目标接口: {0} 不存在!
			return Result.ERROR(this.getInfo(600010077 , target), ResultCode.OPERATION_FAILED);
		}
		this.record = record;
		return Result.SUCCESS();
	}
	
	
	public AcApiInfo buildAjaxApiInfoList() {
		AcApiInfo e = new AcApiInfo();
		return e;
	}
}




















