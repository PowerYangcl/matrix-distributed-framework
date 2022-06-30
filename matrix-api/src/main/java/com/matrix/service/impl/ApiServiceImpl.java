package com.matrix.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.ApiHead;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IApiService;
import com.matrix.util.SignUtil;

/**
 * @description: 网关入口
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年8月9日 下午5:32:44 
 * @version 1.0.0.1
 */
@Service("apiService")
public class ApiServiceImpl extends BaseClass implements IApiService {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	/**
	 * @description: 实现IBaseProcessor接口的类以及这个类所调用的Service方法或RPC接口，其返回
	 * 		值不允许使用公共静态字段，并发场景下会导致返回结果错误
	 *
	 * @author Yangcl
	 * @date 2019年10月29日 下午11:02:06 
	 * @version 1.0.0.1
	 */
	public  Result<?> apiService(BaseApiDto param, HttpServletRequest request, HttpServletResponse response , HttpSession session) {
		Result<AcApiInfoCache> result = this.checkRequest(request , response , param);
		if (result.getStatus().equals("success")){
			try {     
				Class<?> clazz = Class.forName("com.matrix.processor." + result.getData().getProcessor());   
				if (clazz != null && clazz.getDeclaredMethods() != null){
					IBaseProcessor iprocessor = (IBaseProcessor) clazz.newInstance();
					return iprocessor.processor(param, request , response , session);
				}else {
					return this.errorMsg(response, 10010, 600010010, result.getData().getTarget());	// 600010010=系统错误, 未找到{0}接口对应的处理类.请联系开发人员!
				}
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else { 
			return result;
		}
	}
	
	/**
	 * @description: 验证请求信息
	 * 
	 * @param request
	 * @param response
	 * @param json
	 * @return Result<AcApiInfoCache>
	 * @author Yangcl
	 * @date 2021-5-21 17:45:45
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	private Result<AcApiInfoCache> checkRequest(HttpServletRequest request, HttpServletResponse response , BaseApiDto param) {
		/////// 请求信息验证 ///////
		if(param == null) {
			return this.errorMsg(response, 10017, 600010017);	// 600010017=非法的请求数据结构，未检测到请求数据
		}
		if(param.getHead() == null) {
			return this.errorMsg(response,  10018, 600010018);		// 600010018=非法的请求数据结构，缺少请求头
		}
		ApiHead head = param.getHead();
		String key = head.getKey() == null ? "" : head.getKey();
		String value = head.getValue() == null ? "" : head.getValue();
		if(StringUtils.isAnyBlank(key , value)) {
			return this.errorMsg(response, 10016, 600010016);	// 600010016=非法的请求数据结构，缺少key或value
		}
		if(StringUtils.isBlank(head.getTarget())) {  
			return this.errorMsg(response, 10003, 600010003);	// 600010003 非法的请求数据结构，缺少所访问的接口名。
		}

		/////// API信息验证 ///////
		String apiInfoStr = launch.loadDictCache(CachePrefix.ApiInfo , "ApiInfoInit").get(head.getTarget()); 
		if(StringUtils.isBlank(apiInfoStr)){
			return this.errorMsg(response, 10014, 600010014);	// 600010014=系统未检测到您所访问的接口
		} 
		AcApiInfoCache apiInfo = this.initAcApiInfoCache(JSONObject.parseObject(apiInfoStr));
		if(apiInfo.getDiscard() == 0) {
			return this.errorMsg(response, 10013, 600010013);	// 600010013=您所访问的接口已停用
		}
		if(StringUtils.isBlank(apiInfo.getProcessor())) {  // "processor": "private.order.OrderInfomation"
			return this.errorMsg(response, 10015, 600010015);	// 600010015=系统未检测到对应接口处理类!请联系开发人员!
		}
		
		String originHeader = request.getHeader("Origin");
		if(StringUtils.startsWith(apiInfo.getProcessor() , "common")) {		// 如果是工具类型的api则移除跨域访问限制
			response.setHeader("Access-Control-Allow-Origin", originHeader); 	// 移除跨域访问限制
			apiInfo.setLogin(0);
		}
		
		/////// 免登陆接口验证 ///////
		String userInfo = "";
		if(apiInfo.getLogin() == 1) {  // 如果当前接口不是免登录类型的
			if(StringUtils.isBlank(head.getAccessToken() )) {		// 开始验证用户是否登录，令牌是否过期
				return this.errorMsg(response, 10019, 600010019);	// 600010019=用户令牌(accessToken)为空
			}
			userInfo = launch.loadDictCache(CachePrefix.AccessToken , null).get(head.getAccessToken()); 
			if(StringUtils.isBlank(userInfo)) {
				return this.errorMsg(response, 10020, 600010020);	// 600010020=用户登录已经超时
			}
			launch.loadDictCache(CachePrefix.AccessToken , null).setKeyTimeout(head.getAccessToken() , 15*24*60*60L); 
		}
		
		/////// 秘钥验 ///////
		String requestInfo = launch.loadDictCache(CachePrefix.ApiRequester , "ApiRequesterInit").get(head.getKey());  // ac_request_info表的缓存
		if(StringUtils.isBlank(requestInfo)) {
			return this.errorMsg(response, 10012, 600010012);	// 非法的请求! 您请求的公钥未包含在我们的系统中.
		}
		JSONObject requester = JSONObject.parseObject(requestInfo);
		if(StringUtils.isBlank(requester.getString("value"))) {
			return this.errorMsg(response, 10002, 600010002);	// 系统秘钥数据为空，请联系开发人员，为您带来不便请谅解!
		}
		String md5 = SignUtil.md5Sign( requester.getString("value") + head.getTarget() + head.getRequestTime() ); 
		if( !value.equals(md5) ){
			return this.errorMsg(response, 10005, 600010005);	// 秘钥验证失败
		}
		
		/////// 浏览器跨域访问限制 ///////
		if(this.getConfig("matrix-core.model").equals("master")) {
			// 如果【api所属项目】的开放类型 是【公司内部使用接口】，那么遍历该请求者是否拥有该接口的请求权限。
			if("private".equals(requester.getString("atype"))) {
				if(!head.getClient().equals("0") && !head.getClient().equals("1")) {
					if(StringUtils.isBlank(originHeader)) {		// 验证远端浏览器地址是否包含在白名单内
						// TODO 返回错误提示
					}
					String defaultDomains = this.getConfig("matrix-api.default_leader_service_list_" + this.getConfig("matrix-core.model"));  // 取出默认跨域服务器列表
					if(StringUtils.contains(defaultDomains, originHeader)) {
						response.setHeader("Access-Control-Allow-Origin", originHeader); // 移除跨域访问限制
					}else {
						if(!CollectionUtils.isEmpty(apiInfo.getList())) {
							boolean flag = true;
							for(String url : apiInfo.getList()) {
								if(originHeader.equals(url)) {
									response.setHeader("Access-Control-Allow-Origin", originHeader); // 移除跨域访问限制
									flag = false;
									break;
								}
							}
							if(flag) {			// 您所请求的接口尚未包含当前域名。TODO 改600010008提示文案
								return this.errorMsg(response, 10008, 600010008, head.getTarget());	 // 600010008=您所请求的接口{0}不支持跨域访问!
							}
						}else {					// 您所请求的接口不支持跨域访问。TODO改600010009提示文案
							return this.errorMsg(response, 10009, 600010009, head.getTarget());	 // 600010009=非法的请求!您尚未包含在{0}跨域访问名单中!
						}
					}
				}
			}
		}else {
			response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域访问限制，开发环境和测试环境不在限制跨域
		}
		
		/////// 登录后可访问的接口，添加用户信息附着  ///////
		if(StringUtils.isNotBlank(userInfo)) { 		// 登录后可访问的接口
			McUserInfoView view = JSONObject.parseObject(userInfo, McUserInfoView.class);
			if(view.getCid() != 0) {		// 0 或 -1；cid如果 != 0 则代表用户拥有多店铺，需要前端传入cid
				JSONObject shopObj = view.getShopInfoMap().get("key-" + param.getHead().getCid());
				if (shopObj != null){
					view.setCid(param.getHead().getCid());  
					view.setTenantInfoId(shopObj.getLong("tenantInfoId"));
				}
			}
			param.setUserCache(view); 		// 加入用户Session信息
			this.getLogger(null).sysoutInfo("用户：" + view.getUserName() + " 接口名称：" + apiInfo.getName() + " target = " + head.getTarget(), this.getClass()); 
		}
		
		return Result.SUCCESS(apiInfo);
	}
	
	
	/**
	 * @description: 移除跨域访问限制，返回错误消息提示
	 *
	 * @param response
	 * @param code
	 * @param infoCode
	 * @param msg
	 * 
	 * @author Yangcl
	 * @date 2018年10月11日 下午3:05:55 
	 * @version 1.0.0.1
	 */
	private Result<AcApiInfoCache> errorMsg(HttpServletResponse response , Integer code , long infoCode , Object... msg) {
		response.setHeader("Access-Control-Allow-Origin" , "*");
		return Result.ERROR(this.getInfo(infoCode , msg), code);
	}
	

	/**
	 * @description: 记录请求日志 
	 *
	 * @param requester
	 * @param apiInfo 
	 * @author Yangcl
	 * @date 2017年12月3日 下午9:18:25 
	 * @version 1.0.0.1
	 */
//	private void apiRequestLogger(JSONObject requester , JSONObject apiInfo) {
//		
//	}
	
	private AcApiInfoCache initAcApiInfoCache(JSONObject apiInfo) {
		AcApiInfoCache info = new AcApiInfoCache();
		info.setId(apiInfo.getLong("id"));
		info.setName(apiInfo.getString("name"));
		info.setTarget(apiInfo.getString("target"));
//		info.setDtoInfo(apiInfo.getString("dtoInfo"));
		info.setAtype(apiInfo.getString("atype"));
		info.setModule(apiInfo.getString("module"));
		info.setProcessor(apiInfo.getString("processor"));
		info.setDomain(apiInfo.getInteger("domain"));
		info.setParentId(apiInfo.getLong("parentId"));
		info.setSeqnum(apiInfo.getInteger("seqnum"));
		info.setDiscard(apiInfo.getInteger("discard"));
		info.setLogin(apiInfo.getInteger("login"));
		info.setRemark(apiInfo.getString("remark"));
		if(apiInfo.getJSONArray("list") != null && apiInfo.getJSONArray("list").size() != 0) {
			JSONArray arr = apiInfo.getJSONArray("list");
			for(int i = 0; i < arr.size(); i ++) {
				if(StringUtils.isBlank(arr.getString(i))) {
					continue;
				}
				info.getList().add(arr.getString(i));
			}
		}
		return info;
	}
}


















