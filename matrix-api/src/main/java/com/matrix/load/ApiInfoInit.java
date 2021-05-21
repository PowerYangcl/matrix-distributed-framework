package com.matrix.load;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IAcApiDomainMapper;
import com.matrix.dao.IAcApiInfoMapper;
import com.matrix.dao.IAcIncludeDomainMapper;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.AcApiDomainView;

public class ApiInfoInit  extends BaseClass implements ILoadCache<String>{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IAcIncludeDomainMapper acIncludeDomainMapper;
	@Inject
	private IAcApiDomainMapper acApiDomainMapper;
	@Inject
	private IAcApiInfoMapper acApiInfoMapper;
	
	/**
	 * @description: 多表联查，加载缓存
	 * key: xd-ApiInfo-ORDER-INFO
	 * value:
					{
						"id": 80160155,
						"name": "用户登录接口",
						"target": "MANAGER-API-100",
						"dtoInfo": "{\"userName\":\"admin-mdl\",\"password\":\"123456\",\r\n\"platform\":\"134160222D87\"}",
						"atype": "private",		---------------------接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
						"module": "matrix-manager-api",
						"processor": "privates.ManagerApi100Processor",
						"domain": 0,							--------------- 接口是否拥有跨域行为 0 不允许  1 允许跨域访问|ac_api_domain表作为关联
						"parentId": 9,			所属内部项目id,用于树形结构展示|ac_api_project表id
						"seqnum": 3,				顺序码 同一层次显示顺序
						"discard": 1,				这个api是否废弃|0:废弃 1:使用中
						"login": 0,	                                         ---------------- 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
						"remark": "验证用户登录信息|客户端用户：nodejs/IOS平板等",
						"list": [		------------------------------------------------API入口做跨域判断使用
							"http://api.baidu.com",
					        "http://sub.model.firos.com.cn"
						],
						"deleteFlag": 1,					删除标记: 0删除|1未删除
						
						
						"createUserId": 1992,
						"updateUserId": 20072026234128,
						"updateUserName": "admin",
						"createUserName": "Yangcl",
						"updateTime": 1568786995000,
						"startIndex": 1,
						"pageSize": 10,
						"createTime": 1539244178000,
					}
	 * @param key  AcApiInfo.target
	 * @param field null
	 * @return 
	 * @author Yangcl
	 * @date 2017年11月29日 上午11:52:39 
	 * @version 1.0.0
	 */
	@Override
	public String load(String key, String field) {
		if(StringUtils.isBlank(key)) {
			return "";
		}
		AcApiInfo dto = new AcApiInfo();
		dto.setTarget(key); 
		List<AcApiInfo> apiInfoList = acApiInfoMapper.selectByEntity(dto); 
		if(apiInfoList == null || apiInfoList.size() == 0) {
			return "";
		}
		AcApiInfo e = apiInfoList.get(0);
		// 开始初始化API缓存
		JSONObject cache = JSONObject.parseObject(JSONObject.toJSONString(e)); 
		cache.put("list", new ArrayList<String>()); 
		if(e.getDomain() == 1) {		// 针对可跨域情况
			List<AcApiDomainView> adList = acApiDomainMapper.selectByApiInfoId(e.getId());
			if(adList != null && adList.size() != 0) {
				List<JSONObject> domains = new ArrayList<JSONObject>();
				List<String> list = new ArrayList<String>();
				for(AcApiDomainView v : adList) {
					JSONObject include = new JSONObject();
					include.put("id", v.getId());
					include.put("domain", v.getDomain());
					include.put("companyName", v.getCompanyName());
					domains.add(include);
					list.add(v.getDomain());
				}
				cache.put("domains", domains); 
				cache.put("list", list); 
			}
		}
		String value = cache.toJSONString();
		launch.loadDictCache(DCacheEnum.ApiInfo , null).set(key , value , 24*60*60); 
		return value;
	}

}



























