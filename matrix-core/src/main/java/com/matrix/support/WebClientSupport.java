package com.matrix.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.matrix.base.BaseClass;
import com.matrix.map.MDataMap;
import com.matrix.pojo.dto.WebClientRequest;
import com.matrix.system.cache.TopConst;


/**
 * @descriptions 原作者不详。TODO 该类有待进一步拆解
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月23日 下午9:05:54
 * @version 1.0.1
 */
public class WebClientSupport extends BaseClass {

	public static WebClientSupport create() {
		return new WebClientSupport();
	}

	/**
	 * @descriptions 获取请求链接
	 *
	 * @param sUrl
	 * @param sPost
	 * @date 2016年11月23日 下午9:05:29
	 * @author null 
	 * @version 1.0.0.1
	 */
	public String upRequest(String sUrl, String sPost) throws Exception {
		HttpEntity httpEntity = null;
		try {
			httpEntity = new StringEntity(sPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return doRequest(sUrl, httpEntity);
	}

	public static String upHttpsPost(WebClientRequest webClientRequest) 
			throws NoSuchAlgorithmException, KeyStoreException, 	KeyManagementException, CertificateException, IOException {
		String sReturn = "";

		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File(webClientRequest.getFilePath()));
		trustStore.load(instream, webClientRequest.getPassword().toCharArray());
		HttpClientBuilder cb = HttpClientBuilder.create();
		SSLContextBuilder sslcb = new SSLContextBuilder();
		sslcb.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy());
		cb.setSslcontext(sslcb.build());
		CloseableHttpClient httpClient = cb.build();

		HttpPost httppost = new HttpPost(webClientRequest.getUrl());
		httppost.setEntity(webClientRequest.getHttpEntity());

		if (StringUtils.isNotEmpty(webClientRequest.getConentType())) {
			httppost.setHeader("Content-Type", webClientRequest.getConentType());
		}

		// 设置成短链接模式 关闭keep-alve
		httppost.setHeader("Connection", "close");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				sReturn = EntityUtils.toString(resEntity, TopConst.CONST_BASE_ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sReturn = e.getMessage();
		} finally {
			response.close();
			httpClient.close();
		}

		try {
			// CTO电话通知让sleep200毫秒 2015-07-29 14:40修改
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return sReturn;
	}

	/**
	 * 根据链接获取post数据
	 * 
	 * @param sUrl
	 * @param mDataMap
	 * @return
	 * @throws Exception
	 */
	public static String upPost(String sUrl, MDataMap mDataMap) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// nvps.add(new BasicNameValuePair("charset",));
		for (String sKey : mDataMap.keySet()) {
			nvps.add(new BasicNameValuePair(sKey, mDataMap.get(sKey)));
		}
		HttpEntity httpEntity = new UrlEncodedFormEntity(nvps , TopConst.CONST_BASE_ENCODING);
		//return poolRequest(sUrl, httpEntity);
		return  WebClientSupport.create().doRequest(sUrl, httpEntity);
	}

	/**
	 * 根据链接获取post数据---传递head参数
	 * 
	 * @param sUrl
	 * @param mDataMap
	 * @param headerDataMap
	 *            请求头参数
	 * @return
	 * @throws Exception
	 */
	public static String upPost(String sUrl, MDataMap mDataMap , MDataMap headerDataMap) throws Exception {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// nvps.add(new BasicNameValuePair("charset",));
		for (String sKey : mDataMap.keySet()) {
			nvps.add(new BasicNameValuePair(sKey, mDataMap.get(sKey)));
		}

		HttpEntity httpEntity = new UrlEncodedFormEntity(nvps , TopConst.CONST_BASE_ENCODING);

		return poolRequest(sUrl, httpEntity, headerDataMap);
	}

	static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;

	/**
	 * post请求 该请求调用的是连接池功能
	 * 
	 * @param sUrl
	 * @param httpEntity
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String poolRequest(String sUrl, HttpEntity httpEntity) throws ClientProtocolException, IOException {

		String sReturnString = "";
		if (poolingHttpClientConnectionManager == null) {
			poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
			// Increase max total connection to 200
			poolingHttpClientConnectionManager.setMaxTotal(200);
			// Increase default max connection per route to 20
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
		}

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();

		HttpPost httppost = new HttpPost(sUrl);
		httppost.setEntity(httpEntity);
		CloseableHttpResponse response = httpClient.execute(httppost);
		try {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				sReturnString = EntityUtils.toString(resEntity , TopConst.CONST_BASE_ENCODING);
			}
		} finally {
			response.close();
		}
		return sReturnString;
	}

	/**
	 * post请求 该请求调用的是连接池功能 ---参数添加到head里面
	 * 
	 * @param sUrl
	 * @param httpEntity
	 * @param headerDataMap
	 *            请求头参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String poolRequest(String sUrl, HttpEntity httpEntity, MDataMap headerDataMap) throws ClientProtocolException, IOException {

		String sReturnString = "";

		if (poolingHttpClientConnectionManager == null) {

			poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

			// Increase max total connection to 200
			poolingHttpClientConnectionManager.setMaxTotal(200);
			// Increase default max connection per route to 20
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);

		}

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(poolingHttpClientConnectionManager)
				.build();

		HttpPost httppost = new HttpPost(sUrl);

		for (String sKey : headerDataMap.keySet()) {
			httppost.setHeader(sKey, headerDataMap.get(sKey));
		}

		httppost.setEntity(httpEntity);

		CloseableHttpResponse response = httpClient.execute(httppost);

		try {
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {

				sReturnString = EntityUtils.toString(resEntity,
						TopConst.CONST_BASE_ENCODING);

			}
		} finally {
			response.close();
		}

		return sReturnString;

	}

	/**
	 * 获取请求
	 * 
	 * @param sUrl
	 * @param httpEntity
	 * @return
	 * @throws Exception
	 */
	public String doRequest(String sUrl, HttpEntity httpEntity)
			throws Exception {
		String sReturnString = null;
		HttpClientBuilder hClientBuilder = HttpClientBuilder.create();
		@SuppressWarnings("deprecation")
		SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		hClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

		// 设定超时时间，避免阻塞太久
		RequestConfig requestConfig = RequestConfig.custom()  
                .setConnectionRequestTimeout(60000)  
                .setConnectTimeout(60000)  
                .setSocketTimeout(60000)
                .build(); 
		
		hClientBuilder.setDefaultRequestConfig(requestConfig);
		CloseableHttpClient httpclient = hClientBuilder.build();
		
		HttpPost httppost = new HttpPost(sUrl);
		// 设置成短链接模式 关闭keep-alve
		httppost.setHeader("Connection", "close");
		CloseableHttpResponse response = null;

		try {

			httppost.setEntity(httpEntity);

			response = httpclient.execute(httppost);

			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {

				sReturnString = EntityUtils.toString(resEntity);

			}
			if (resEntity != null) {

				EntityUtils.consume(resEntity);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			if(response != null){
				response.close();
			}

			httpclient.close();
			httpclient = null;

		}

		return sReturnString;
	}

	public String doGet(String sUrl) throws Exception {
		String sReturnString = null;
		HttpClientBuilder hClientBuilder = HttpClientBuilder.create();

		CloseableHttpClient httpclient = hClientBuilder.build();

		// HttpPost httppost = new HttpPost(sUrl);

		HttpGet httpGet = new HttpGet(sUrl);
		// 设置成短链接模式 关闭keep-alve
		httpGet.setHeader("Connection", "close");
		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpGet);

			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {

				sReturnString = EntityUtils.toString(resEntity);

			}
			if (resEntity != null) {

				EntityUtils.consume(resEntity);

			}

		} catch (Exception e) {
			httpGet.reset();
			httpclient = null;
			e.printStackTrace();
			throw e;

		} finally {
			response.close();

			httpGet.reset();
			httpclient.close();
			httpclient = null;

		}

		return sReturnString;
	}

	/**
	 * 获取Stream的调用方法 一般尽量不能使用该方法操作调用完后需要关闭流
	 * 
	 * @param sUrl
	 * @return
	 * @throws Exception
	 */
	public HttpEntity upEntity(String sUrl) throws Exception {

		String sReturnString = null;
		HttpClientBuilder hClientBuilder = HttpClientBuilder.create();

		HttpEntity resEntity = null;

		CloseableHttpClient httpclient = hClientBuilder.build();

		// HttpPost httppost = new HttpPost(sUrl);

		HttpGet httpGet = new HttpGet(sUrl);
		// 设置成短链接模式 关闭keep-alve
		httpGet.setHeader("Connection", "close");

		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpGet);

			resEntity = response.getEntity();

		} catch (Exception e) {
			httpGet.reset();
			httpclient = null;
			e.printStackTrace();
			throw e;

		} finally {

			httpclient = null;

		}

		return resEntity;

	}

}
