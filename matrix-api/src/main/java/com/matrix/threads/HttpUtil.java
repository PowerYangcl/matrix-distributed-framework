package com.matrix.threads;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
/**
 * @description: http工具类,支持添加header信息 ,支持ssl,post,get方式
 *
 * @date 2019年8月15日 上午10:39:34 
 * @version 1.0.0.1
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger("httpUtil");
	//private static Logger logger = LoggerFactory.getLogger(HTTPUtil.class);
	private static Logger errorLogger = LoggerFactory.getLogger("errorLog");

	private static final String ENCODING = "utf-8";
	private static final String ACCEPT = "application/json;charset=utf-8";
	private static final String CONTENT_TYPE_JSON = "application/x-www-form-urlencoded";
	private static final String CONTENT_TYPE_SIMPLE_FORM = "application/x-www-form-urlencoded;charset=utf-8";
	/**
	 * 建立http连接的等待时间
	 */
	private static final int HTTP_CONNECTION_TIMEOUT = 20000;
	/**
	 * 读取响应数据的等待时间
	 */
	private static final int HTTP_SOCKET_TIMEOUT = 60000;

	public static String addHttpProtocol(String url) {
		if (StringUtils.isEmpty(url) == false) {
			if (url.startsWith("http://") == false
					&& url.startsWith("https://") == false) {
				return "http://" + url;
			} else {
				return url;
			}
		} else {
			return "";
		}
	}

	/**
	 * 按application/json格式提交post请求，并读取返回的json
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	@Deprecated
	public static JSONObject post(String url, String json) {
		return postJSON(url, json, true, HTTP_CONNECTION_TIMEOUT,
				HTTP_SOCKET_TIMEOUT, null);
	}
	
	/**
	 * 按application/json格式提交post请求，并读取返回的json
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static JSONObject postJSON(String url, String json) {
		return postJSON(url, json, false , HTTP_CONNECTION_TIMEOUT,
				HTTP_SOCKET_TIMEOUT, null);
	}
	
	/**
	 * 按application/json格式提交post请求，并读取返回的json
	 * 
	 * @param url
	 * @param json
	 * @param header
	 * @return
	 */
	public static JSONObject postJSON(String url, String json, boolean isSSL, Header[] headers) {
		return postJSON(url, json, isSSL , HTTP_CONNECTION_TIMEOUT,
				HTTP_SOCKET_TIMEOUT, headers);
	}

	/**
	 * 按application/json格式提交post请求，并读取返回的json
	 * 
	 * @param url
	 * @param jsonStr
	 * @param isSSL
	 * @param connectionTimeout
	 * @param socketTimeout
	 * @param header
	 * @return
	 */
	public static JSONObject postJSON(String url, String jsonStr, boolean isSSL,
									  int connectionTimeout, int socketTimeout, Header[] headers) {
		HttpPost httpost = new HttpPost(url);

		if (headers != null) {
			httpost.setHeaders(headers);
		}

		List formparams = new ArrayList();
		formparams.add(new BasicNameValuePair("json", jsonStr));


		httpost.setHeader("Content-Type", CONTENT_TYPE_JSON);
		httpost.setHeader("Accept", ACCEPT);

		String json= "" ;
		try {

			logger.info("REQUEST url:\n" + url);
			logger.info("REQUEST json:\n" + jsonStr);
			
//			StringEntity input = new StringEntity("json:" + jsonStr, "UTF-8");
//			input.setContentType(CONTENT_TYPE_JSON);
//			httpost.setEntity(input);

			UrlEncodedFormEntity uefEntity;
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpost.setEntity(uefEntity);

			DefaultHttpClient httpclient = getHttpClient(isSSL);

			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, socketTimeout);

			HttpResponse response = httpclient.execute(httpost);

			if(response == null){
				errorLogger.error("postJSON , response为空");
				return null ;
			}
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			HttpEntity entity = response.getEntity();
			json = EntityUtils.toString(entity);
			
			logger.info("RESPONSE body:\n" + json);
			
			if(statusCode == 200 && json != null && (json.isEmpty() || json.equals("\"\""))){
				json = "{}";
			}
			
			return JSONObject.parseObject(json);

		} catch (Exception e) {
			errorLogger.error("REQUEST url:\n" + url);
			errorLogger.error("RESPONSE body:\n" + json);
			errorLogger.error(e.getMessage(), e);
			
		} finally {
			try {
				httpost.releaseConnection();
			} catch (Exception e) {
				errorLogger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static JSONObject postSimpleForm(String url, Map<String,String> params) {
		return postSimpleForm(url,params,false,HTTP_CONNECTION_TIMEOUT,HTTP_SOCKET_TIMEOUT, null);
	}
	
	public static JSONObject postSimpleForm(String url, Map<String,String> params , boolean isSSL,
											int connectionTimeout, int socketTimeout, Header header) {
		HttpPost httpost = new HttpPost(url);

		if (header != null) {
			httpost.setHeader(header);
		}

		httpost.setHeader("Content-Type", CONTENT_TYPE_SIMPLE_FORM);
		httpost.setHeader("Accept", ACCEPT);

		String body = "" ;
		try {

//			logger.info("REQUEST url:\n" + url);
//			infoLogger.info("REQUEST json:\n" + json);
			
			//装填参数  
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();  
	        if(params !=null){
	            for (Entry<String, String> entry : params.entrySet()) {
	                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
	            }  
	        } 
			
			httpost.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));

			DefaultHttpClient httpclient = getHttpClient(isSSL);

			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, socketTimeout);

			HttpResponse response = httpclient.execute(httpost);
			
			if(response == null){
				errorLogger.error("postSimpleForm , response为空");
				return null ;
			}

			int statusCode = response.getStatusLine().getStatusCode();
			
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
			
//			logger.info("RESPONSE body:\n" + body);
			
			if(statusCode == 200 && body != null && (body.isEmpty() || body.equals("\"\""))){
				body = "{}";
			}
			
			return JSONObject.parseObject(body);
			

		} catch (Exception e) {
			errorLogger.error("REQUEST url:\n" + url);
			errorLogger.error("RESPONSE body:\n" + body);
			errorLogger.error("http post 请求失败", e);
		} finally {
			try {
				httpost.releaseConnection();
			} catch (Exception e) {
				errorLogger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	/*************************************** GET ******************************************/

	/**
	 * 只看响应是否正常
	 * 
	 * @param url
	 * @param header
	 * @param isSSL
	 * @return
	 */
	public static boolean get(String url, boolean isSSL , Header header) {
		HttpGet httpGet = new HttpGet(url);
		if (header != null) {
			httpGet.addHeader(header);
		}
		try {
			HttpClient client = getHttpClient(isSSL);
			HttpResponse response = client.execute(httpGet);
			
			if(response == null){
				errorLogger.error("getJSON , response为空");
				return false ;
			}
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			errorLogger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 会将返回内容按json解析，解析失败返回null
	 * 
	 * @param url
	 * @param header
	 * @param isSSL
	 * @return
	 */
	public static JSONObject getJSON(String url, boolean isSSL , Header header) {
		HttpGet httpGet = new HttpGet(url);
		if (header != null) {
			httpGet.addHeader(header);
		}
		
		String body = "" ;
		try {
			
//			logger.info("REQUEST url:\n" + url);
			
			HttpClient client = getHttpClient(isSSL);
			
			HttpResponse response = client.execute(httpGet);
			
			if(response == null){
				errorLogger.error("getJSON , response为空");
				return null ;
			}
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			HttpEntity entity = response.getEntity();
			
			body = EntityUtils.toString(entity);
			
//			logger.info("RESPONSE body:\n" + body);
			
			if(statusCode == 200 && body != null && (body.isEmpty() || body.equals("\"\""))){
				body = "{}";
			}
			
			return JSONObject.parseObject(body);
			
		} catch (Exception e) {
			errorLogger.error("REQUEST url:\n" + url);
			errorLogger.error("RESPONSE body:\n" + body);
			errorLogger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 会将返回内容按json解析，解析失败返回null
	 *
	 * @param url
	 * @param headers
	 * @param isSSL
	 * @return
	 */
	public static JSONObject getJSON(String url, boolean isSSL , List<Header> headers) {
		HttpGet httpGet = new HttpGet(url);
		if (headers != null) {
			if (headers != null) {
				for (Header header : headers) {
					httpGet.addHeader(header);
				}
			}
		}

		String body = "" ;
		try {

//			logger.info("REQUEST url:\n" + url);

			HttpClient client = getHttpClient(isSSL);

			HttpResponse response = client.execute(httpGet);

			if(response == null){
				errorLogger.error("getJSON , response为空");
				return null ;
			}

			int statusCode = response.getStatusLine().getStatusCode();

			HttpEntity entity = response.getEntity();

			body = EntityUtils.toString(entity);

//			logger.info("RESPONSE body:\n" + body);

			if(statusCode == 200 && body != null && (body.isEmpty() || body.equals("\"\""))){
				body = "{}";
			}

			return JSONObject.parseObject(body);

		} catch (Exception e) {
			errorLogger.error("REQUEST url:\n" + url);
			errorLogger.error("RESPONSE body:\n" + body);
			errorLogger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings({ "deprecation" })
	private static DefaultHttpClient getHttpClient(boolean isSSL)
			throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				20000);
		if (isSSL) {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpclient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			httpclient = new DefaultHttpClient(ccm, httpclient.getParams());
			return httpclient;
		} else {
			return httpclient;
		}
	}
	// 使用URLConnection发送get请求
    public static int sendGet(String url) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("connection", "Keep-Alive");
            // 建立实际的连接
            connection.connect();
            return ((HttpURLConnection)connection).getResponseCode();
        } catch (Exception e) {
        	errorLogger.error("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	errorLogger.error(e2.getMessage(), e2);
            }
        }
        return 404;
    }
    
 // 使用URLConnection发送get请求
    public static int sendGet(String url,String ua) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            if(ua != null) {
            		connection.setRequestProperty("User-Agent", ua);
            }
            // 建立实际的连接
            connection.connect();
            return ((HttpURLConnection)connection).getResponseCode();
        } catch (Exception e) {
        	errorLogger.error("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	errorLogger.error(e2.getMessage(), e2);
            }
        }
        return 404;
    }
	/**
	 * 按application/json格式提交post请求，并读取返回的json
	 *
	 * @param url
	 * @param json
	 * @return
	 */
	public static JSONObject postJSONString(String url, String json) {
		HttpPost httpost = new HttpPost(url);
		httpost.setHeader("Content-Type", CONTENT_TYPE_SIMPLE_FORM);
		httpost.setHeader("Accept", ACCEPT);

		String body= "" ;
		try {

			logger.info("REQUEST url:\n" + url);
			logger.info("REQUEST json:\n" + json);

			StringEntity input = new StringEntity(json, "UTF-8");
			input.setContentType(CONTENT_TYPE_SIMPLE_FORM);
			httpost.setEntity(input);

			DefaultHttpClient httpclient = getHttpClient(false);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 120000);

			HttpResponse response = httpclient.execute(httpost);

			if(response == null){
				errorLogger.error("postJSON , response为空");
				return null ;
			}

			int statusCode = response.getStatusLine().getStatusCode();

			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);

			logger.info("RESPONSE body:\n" + body);

			if(statusCode == 200 && body != null && (body.isEmpty() || body.equals("\"\""))){
				body = "{}";
			}

			return JSONObject.parseObject(body);

		} catch (Exception e) {
			errorLogger.error("REQUEST url:\n" + url);
			errorLogger.error("RESPONSE body:\n" + body);
			errorLogger.error(e.getMessage(), e);

		} finally {
			try {
				httpost.releaseConnection();
			} catch (Exception e) {
				errorLogger.error(e.getMessage(), e);
			}
		}
		return null;
	}
}
