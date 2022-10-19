package com.matrix.support;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @descriptions 基础的文件操作支持
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2017年8月2日 下午5:37:18
 * @version 1.0.1
 */
@Slf4j
public class FileSupport {
	
	private FileSupport(){
	}
	private static class LazyHolder{
		private static final FileSupport INSTANCE = new FileSupport();
	}
	public static final FileSupport getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	/**
	 * @descriptions 获取任意网站上图片的属性
	 *
	 * @param imgUrl
	 * @date 2017年8月2日 下午5:34:57
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject getImageProperty(String imgUrl) {
		JSONObject property = new JSONObject(); 
		property.put("width", "10");
		property.put("height", "10");
		property.put("size", "10");
		
		URL url = null;  
		HttpURLConnection conn = null;
		InputStream is = null;  
	    BufferedImage img = null; 
	    int length = 0;
	    try {  
	        url = new URL(imgUrl);   
	        conn = (HttpURLConnection) url.openConnection();
	        length = conn.getContentLength(); // 获取图片字节数，kb
			String result = String.valueOf(length / 1024.0);
			if(result.length() > 5){
				result = result.substring(0, 5);  // 保留两位小数
			} 
			property.put("size", result);
			
	        is = url.openStream();  
	        img = ImageIO.read(is);  
	        is.close();
	        property.put("width", String.valueOf(img.getWidth()) );    // 获取图片长宽属性
			property.put("height",  String.valueOf(img.getHeight()) ); 
	    }catch (Exception e) {  
	        log.error("图片长宽属性读取异常", e); 
	    } finally {  
	    	conn.disconnect();  
	    } 
		
		return property;
	}
	
}
