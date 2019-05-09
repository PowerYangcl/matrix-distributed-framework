package com.matrix.support;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.alibaba.fastjson.JSONObject;

/** @description: mip项目上传支持类
 *
 * @author wanghao
 * @date 2018年11月23日 下午4:17:08 
 * @version 1.0.0.1
 */
public class MipHttpClientFileUpload extends HttpClientSupport{
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件流
	 */
	private InputStream inputStream;

	@Override
	public InputStream streamInit() {
		return this.inputStream; 
	}
	/**
	 * @description: 上传对象构造类
	 *
	 * @param fileName 文件名称
	 * @param inputStream  文件流
	 * @author wanghao
	 * @date 2018年11月26日 上午9:17:03 
	 * @version 1.0.0.1
	 */
	public MipHttpClientFileUpload(String fileName,InputStream inputStream) {
		this.fileName = fileName;
		this.inputStream = inputStream;
	}
	@Override
	public HttpPost postInit(String url , InputStream inputStream) {
		HttpPost post = new HttpPost(url);
		try {
			inputStream.reset();
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			post.addHeader("Origin", "http");
			post.addHeader("binfile-auth", "platform-name");
			post.addHeader("binfile-gzip", "false");
			post.addHeader("binfile-reqlen", String.valueOf(inputStream.available()));
			String filename = this.fileName; 
			builder.addBinaryBody("binFile", inputStream, ContentType.DEFAULT_BINARY, filename);// 文件
			builder.addTextBody("filename", filename, ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), "UTF-8"));
			post.setEntity(builder.build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return post;
	}
	/**
	 * @description: 执行上传
	 *
	 * @return 
	 * @author wanghao
	 * @date 2018年11月26日 上午9:17:39 
	 * @version 1.0.0.1
	 */
	public JSONObject upload() {
		String url = this.getConfig("matrix-core.ajax_file_upload_"+this.getConfig("matrix-core.model"));
		postInit(url,this.inputStream);
		JSONObject result = super.requestServerStream(url);
		return result;
	}
	
}
