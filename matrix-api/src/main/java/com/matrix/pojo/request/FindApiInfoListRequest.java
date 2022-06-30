package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.ApiTreeView;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindApiInfoListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6167831064779815765L;

	private IBaseLaunch<ICacheFactory> launch;
	
	private Integer discard;
    
	private McUserInfoView userCache;
	
	private JSONArray arr;
	
	public Result<List<ApiTreeView>> validate(IBaseLaunch<ICacheFactory> launch){
		String project = launch.loadDictCache(CachePrefix.ApiProject , "ApiProjectInit").get("all");
		if(StringUtils.isBlank(project)) { // 600010068=API树形结构加载失败!api所属项目未能正常初始化，请重试.
			return Result.ERROR(this.getInfo(600010068), ResultCode.OPERATION_FAILED);
		}
		JSONObject pobj = JSONObject.parseObject(project);
		if(!pobj.getString("status").equals("success")) { // 600010069=API树形结构加载失败!api所属项目缓存异常.
			return Result.ERROR(this.getInfo(600010069), ResultCode.OPERATION_FAILED);
		}
		JSONArray arr = pobj.getJSONArray("data");
		if(arr.size() == 0) { // 600010070=API树形结构加载失败!api所属项目未定义.
			return Result.ERROR(this.getInfo(600010070), ResultCode.OPERATION_FAILED);
		}
		this.arr = arr;
		return Result.SUCCESS();
	}
	
	
	public AcApiInfo buildAjaxApiInfoList() {
		AcApiInfo e = new AcApiInfo();
		e.setDiscard(discard);
		return e;
	}
}




















