package com.matrix.threads;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.matrix.util.DateUtil;
import com.matrix.util.SignUtil;

/** @description: 支付系统请求支持工具类
 *
 * @author wanghao
 * @date 2019年9月2日 上午9:41:03 
 * @version 1.0.0.1
 */
public class PaymentRequestSupport {
	private static final String URL = "http://127.0.0.1:8080/api-commodity-web/commodity/api.do";
	private static final String CLIENT = "9";
	private static final String CHANNEL = "developer-private";
	private static final String VERSION = "1.0.0";
	private static final String KEY = "133C9CB27DA0";
	private static final String VALUE = "FD4007DB87B245EEACA7DAD5D4A1CECD";

	
	public static void main(String[] args) {
		for (int i = 0; i < 2000 ; i++) {
			
			try {
				Thread.sleep(125);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Thread catalog = new Thread(new TestCatalog(i +1));
			Thread label = new Thread(new TestLabel(i +1));
			catalog.start();
			label.start();
		}
	}
	
	
	/**
	 * @description: 发送支付系统请求
	 *
	 * @param target 请求接口
	 * @param data 请求业务参数
	 * @return 
	 * @author wanghao
	 * @date 2019年9月2日 上午11:00:53 
	 * @version 1.0.0.1
	 */
	public static JSONObject doRequest(String target,JSONObject data) {
		JSONObject param = new JSONObject();
		JSONObject head = getHead(target);
		param.put("head", head);
		param.put("data", data);
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("json", param.toJSONString());
		return HttpUtil.postSimpleForm(URL, postParams);
	}
	
	/**
	 * @description: 组装请求头信息
	 *
	 * @param target 请求地址
	 * @return 
	 * @author wanghao
	 * @date 2019年9月2日 上午11:01:23 
	 * @version 1.0.0.1
	 */
	private static JSONObject getHead(String target) {
		JSONObject head = new JSONObject();
		String requestTime = DateUtil.getSysDateTime();
		head.put("target", target);
		head.put("accessToken", "09255c7724fe9b8df952aa2f7e3ec71846e554168341f0240b254ae3ae1494fa");
		head.put("client", CLIENT);
		head.put("version", VERSION);
		head.put("requestTime", requestTime);
		head.put("channel", CHANNEL);
		head.put("key", KEY);
		head.put("value", SignUtil.md5Sign(VALUE + target + requestTime));
		return head;
	}
	

	
	/**
	 * @description: 创建支付单调用实例
	 * 
	 * @author wanghao
	 * @date 2019年9月2日 上午11:05:27 
	 * @version 1.0.0.1
	 */
	public static void testCreatePayOrder() {
		//json业务信息
		JSONObject data = new JSONObject();
		//1公众号,2小程序
		data.put("channelType", "2");
		//商户id(cid)
		data.put("mchId", "2");
		//商户订单号
		data.put("mchOrderNo", "111111");
		//默认
		data.put("channelId", "WX_JSAPI");
		//总金额单位(分)
		data.put("amount", "1000");
		//默认
		data.put("currency", "CNY");
		//客户端ip
		data.put("clientIp", "127.0.0.1");
		//回调地址
		data.put("notifyUrl", "test.html");
		//支付标题
		data.put("subject", "订单支付");
		//支付内容
		data.put("body", "法拉利488一辆");
		//附加参数
		JSONObject extra = new JSONObject();
		extra.put("openId", "onER45OrLUq-068J-9UeapCF4Hvs");
		data.put("extra", extra.toJSONString());
		//请求地址
		String target = "PAYMENT-100-PROCESSOR";
		//发送请求
		JSONObject result = doRequest(target, data);
		System.out.println(result);
	}
}
