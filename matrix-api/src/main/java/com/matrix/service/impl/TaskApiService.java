package com.matrix.service.impl;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.dto.Head;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.SignUtil;

public class TaskApiService extends BaseClass implements Callable<JSONObject>{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	private HttpServletRequest request; 
	private HttpServletResponse response; 
	private HttpSession session; 
	private String json;
	
	public TaskApiService(HttpServletRequest request, HttpServletResponse response, HttpSession session, String json) {
		this.request = request;
		this.response = response;
		this.session = session;
		this.json = json;
	}

	public JSONObject call() throws Exception {
		return this.apiService(request, response, session, json);
	}
	
	private  JSONObject apiService(HttpServletRequest request, HttpServletResponse response , HttpSession session , String json) {
		JSONObject result = this.checkRequest(request , response , json);
		if (result.getString("status").equals("success")){  
			synchronized (Object.class) {
				try {     
					Class<?> clazz = Class.forName("com.matrix.processor." + result.getString("processor"));   
					if (clazz != null && clazz.getDeclaredMethods() != null){
						IBaseProcessor iprocessor = (IBaseProcessor) clazz.newInstance();
						return iprocessor.processor(request , response , session , result.getJSONObject("requester"));
					}else {
						return this.errorMsg(response, "10010", 600010010, result.getString("target"));	// 600010010=系统错误, 未找到{0}接口对应的处理类.请联系开发人员!
					}
				}catch (Exception e) {
					e.printStackTrace(); 
					return this.errorMsg(response, "10011", 600010011);	// 600010011=系统错误, 请联系开发人员!
				}
			}
		}else { 
			return result;
		}
	}
	
	/**
	 * @description: 开始进行md5签名验证|如果成功则返回ApiInfo缓存Json结构体
	 *
	 * @param key
	 * @param value
	 * @param json 
	 * @author Yangcl
	 * @date 2017年11月10日 下午3:59:15 
	 * @version 1.0.0
	 */
	private JSONObject checkRequest(HttpServletRequest request, HttpServletResponse response , String json) {
		if(StringUtils.isBlank(json)) {
			return this.errorMsg(response, "10017", 600010017);	// 600010017=非法的请求数据结构，未检测到请求数据
		}
		
		JSONObject requester_ = JSONObject.parseObject(json);
		if(StringUtils.isBlank(requester_.getString("head"))) {
			return this.errorMsg(response,  "10018", 600010018);		// 600010018=非法的请求数据结构，缺少请求头
		}
		Head head = JSONObject.parseObject(requester_.getString("head"), Head.class); 
		String key = head.getKey() == null ? "" : head.getKey();
		String value = head.getValue() == null ? "" : head.getValue();
		if(StringUtils.isAnyBlank(key , value)) {
			return this.errorMsg(response, "10016", 600010016);	// 600010016=非法的请求数据结构，缺少key或value
		}
		if(StringUtils.isBlank(head.getTarget())) {  
			return this.errorMsg(response, "10003", 600010003);	// 非法的请求数据结构，缺少所访问的接口名。
		}
		
		String originHeader = request.getHeader("Origin");
		
		String apiInfoStr = launch.loadDictCache(DCacheEnum.ApiInfo , "InitApiInfo").get(head.getTarget()); 
		if(StringUtils.isBlank(apiInfoStr)){
			return this.errorMsg(response, "10014", 600010014);	// 600010014=系统未检测到您所访问的接口
		} 
		JSONObject apiInfo = JSONObject.parseObject(apiInfoStr);
		if(apiInfo.getInteger("discard") == 0) {
			return this.errorMsg(response, "10013", 600010013);	// 600010013=您所访问的接口已停用
		}
		if(StringUtils.isBlank(apiInfo.getString("processor"))) {  // "processor": "private.order.OrderInfomation"
			return this.errorMsg(response, "10015", 600010015);	// 600010015=系统未检测到对应接口处理类!请联系开发人员!
		}
		
		if(StringUtils.startsWith(apiInfo.getString("processor") , "common")) {	// 如果是工具类型的api则移除跨域访问限制
			response.setHeader("Access-Control-Allow-Origin", originHeader); // 移除跨域访问限制
		}else {
			String userInfo = "";
			if(apiInfo.getInteger("login") == 1) {  // 如果当前接口不是免登录类型的
				if(StringUtils.isBlank(head.getAccessToken() )) {		// 开始验证用户是否登录，令牌是否过期
					return this.errorMsg(response, "10019", 600010019);	// 600010019=用户令牌(accessToken)为空
				}
				userInfo = launch.loadDictCache(DCacheEnum.AccessToken , null).get(head.getAccessToken()); 
				if(StringUtils.isBlank(userInfo)) {
					return this.errorMsg(response, "10020", 600010020);	// 600010020=用户登录已经超时
				}
				launch.loadDictCache(DCacheEnum.AccessToken , null).setKeyTimeout(head.getAccessToken() , 15*24*60*60L); 
			}
			
			String requestInfo = launch.loadDictCache(DCacheEnum.ApiRequester , "InitApiRequester").get(key);  // ac_request_info表的缓存
			if(StringUtils.isBlank(requestInfo)) {
				return this.errorMsg(response, "10012", 600010012);	// 非法的请求! 您请求的公钥未包含在我们的系统中.
			}
			JSONObject requester = JSONObject.parseObject(requestInfo);
			if(StringUtils.isBlank(requester.getString("value"))) {
				return this.errorMsg(response, "10002", 600010002);	// 系统秘钥数据为空，请联系开发人员，为您带来不便请谅解!
			}
			String md5 = SignUtil.md5Sign( requester.getString("value") + head.getTarget()	+ head.getRequestTime() ); 
			if( !value.equals(md5) ){
				return this.errorMsg(response, "10005", 600010005);	// 秘钥验证失败
			}
			// 如果当前接口不是免登录类型的，则需要请求者的访问权限与接口权限一样(权限：private or public)。
			if(apiInfo.getInteger("login") == 1 && !requester.getString("atype").equals(apiInfo.getString("atype"))) {
				return this.errorMsg(response, "10007", 600010007);	// 600010007=非法的请求! 您的请求域不匹配!
			}
			
			if(this.getConfig("matrix-core.model").equals("master")) {  
				// 如果【api所属项目】的开放类型 是【公司内部使用接口】，那么遍历该请求者是否拥有该接口的请求权限。
				if(apiInfo.getString("atype").equals("private")) {    
					if(!head.getClient().equals("0") && !head.getClient().equals("1") && StringUtils.isNotBlank(originHeader)) {
						String defaultDomains = this.getConfig("matrix-api.default_leader_service_list_" + this.getConfig("matrix-core.model"));  // 取出默认跨域服务器列表
						if (StringUtils.contains(apiInfo.getString("list"), originHeader)||StringUtils.contains(defaultDomains, originHeader)) { 
							response.setHeader("Access-Control-Allow-Origin", originHeader); // 移除跨域访问限制
						}else{
							if(apiInfo.getString("list") == null ||apiInfo.getString("list").length() == 2) {
								return this.errorMsg(response, "10008", 600010008, head.getTarget());	 // 600010008=您所请求的接口{0}不支持跨域访问!
							}
							return this.errorMsg(response, "10009", 600010009, head.getTarget());	 // 600010009=非法的请求!您尚未包含在{0}跨域访问名单中!
						}
					}
				}
			}else {
				response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域访问限制，开发环境和测试环境不在限制跨域
			}
			
//			else {    // else: requester.getString("atype").equals("public")  此时为open-api，系统公共开放接口，则移除跨域访问限制
//				response.setHeader("Access-Control-Allow-Origin", originHeader);
//			}
			
			// 重置请求对象，加入用户Session信息|此种情况一般为业务API调入，以“common”开始的Api通常不会进入到此代码块中。
			apiInfo.put("status", "success");
			JSONObject data = JSONObject.parseObject(requester_.getString("data"));
//			if(StringUtils.isNotBlank(userInfo)) { // ajax_client_login接口会导致此处为空
//				McUserInfoView view = JSONObject.parseObject(userInfo, McUserInfoView.class);
//				data.put("userCache", view);  // 加入用户Session信息
//				this.getLogger(null).sysoutInfo("用户：" + view.getUserName() + " 接口名称：" + apiInfo.getString("name") + " target = " + head.getTarget(), this.getClass()); 
//			}
			
			if(StringUtils.isNotBlank(userInfo)) { 		// 登录后可访问的接口
				McUserInfoView view = JSONObject.parseObject(userInfo, McUserInfoView.class);
				if(view.getCid() != 0) {		// 0 或 -1		
					JSONObject shopObj = view.getShopInfoMap().get("key-" + data.getLong("cid"));
					if (shopObj != null){
						view.setCid(data.getLong("cid"));  // cid如果 != 0 则代表用户拥有多店铺，需要前端传入cid
						view.setTenantInfoId(shopObj.getLong("tenantInfoId"));
					}
				}
				data.put("userCache", view);  // 加入用户Session信息
				this.getLogger(null).sysoutInfo("用户：" + view.getUserName() + " 接口名称：" + apiInfo.getString("name") + " target = " + head.getTarget(), this.getClass()); 
			}else {			// 免登录接口
				if(data.containsKey("cid")) {    // 部分接口可以不用传递 cid
					McUserInfoView view = new McUserInfoView();
					String shopInfo = launch.loadDictCache(DCacheEnum.TcShopInfo , "InitTcShopInfo").get(view.getCid().toString());
					if(StringUtils.isNotBlank(shopInfo)) {
						view.setCid(data.getLong("cid"));
						JSONObject shop = JSONObject.parseObject(shopInfo);
						view.setTenantInfoId(shop.getLong("tenantInfoId"));
					}else {
						return this.errorMsg(response, "10021", 600010021);	// 600010021=免登录接口所传入的cid未找到对应的店铺信息，请核实
					}
					data.put("userCache", view);  // 加入用户Session信息
					this.getLogger(null).sysoutInfo("免登录接口 | 接口名称：" + apiInfo.getString("name") + " target = " + head.getTarget(), this.getClass()); 
				}
			}
			
			
			JSONObject obj = new JSONObject();	// 重置请求对象
			obj.put("head" , head);
			obj.put("data", data);
			apiInfo.put("requester", obj);
			apiInfo.put("target", head.getTarget());
			return apiInfo;
		}
		
		
		apiInfo.put("status", "success");
		apiInfo.put("requester", requester_);
		apiInfo.put("target", head.getTarget());
		return apiInfo;
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
	private JSONObject errorMsg(HttpServletResponse response , String code , long infoCode , Object... msg) {
		JSONObject result = new JSONObject();
		result.put("status", "error");
		result.put("code", code);
		result.put("msg", this.getInfo(infoCode , msg));
		response.setHeader("Access-Control-Allow-Origin" , "*");
		return result;
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
	private void apiRequestLogger(JSONObject requester , JSONObject apiInfo) {
		
	}
}











