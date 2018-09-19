package com.matrix.support;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.util.FileUtil;

/**
 * @deprecated 准备废弃的类
 * @description: 文件上传组件|将图片内容等上传到cfile服务器中
 * 	配置参照：config.matrix-core.properties
 *  FileUpload.getInstance().upFileFromRequest(request)
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年6月12日 下午2:24:03 
 * @version 1.0.0
 */
public class FileUploadSupport extends BaseClass{
	
	private String url = "";
	
	private FileUploadSupport(){
		url = this.getConfig("matrix-core.upload_pic_url_" + this.getConfig("matrix-core.model"));   // dev or master 
	}
	private static class LazyHolder{
		private static final FileUploadSupport INSTANCE = new FileUploadSupport();
	}
	public static final FileUploadSupport getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	
	/**
	 * @description: 上传一张图片到服务器
	 * 
	 * @param request HttpServletRequest request
	 * @return 得到一个ueditor所需的返回内容
	 * {
		    "state": "SUCCESS",
		    "title": "ba89f9575e8b4a83a490e1d25b1ebe3d.png",
		    "original": "QQ图片20161222141415.png",
		    "type": ".png",
		    "url": "http://cfiles.beta.huijiayou.cn/cfiles/staticfiles/upload/29a75/ba89f9575e8b4a83a490e1d25b1ebe3d.png",
		    "size": 146693
		}
	 * 
	 * 
	 * @author Yangcl 
	 * @date 2017年6月13日 下午5:24:26 
	 * @version 1.0.0.1
	 */
	public JSONObject uploadOnePicture(HttpServletRequest request){
		JSONObject result = new JSONObject();
		List<FileItem> items = this.getFileFromRequest(request);
		if (items != null && items.size() > 0) {
			for (FileItem fi : items) {
				String remoteUpload = this.remoteUpload("upload" , fi.getName() , fi.get());
				JSONObject t = JSONObject.parseObject(remoteUpload, result.getClass());
				String imgs = t.getString("resultObject");
				if(t.getInteger("resultCode") == 1 && StringUtils.isNotBlank(imgs) && imgs.length() > 0) {
					result.put("state", "SUCCESS");  // 编辑器编辑上传图片，暂时只支持上传一张图片
					result.put("title", imgs.substring(imgs.lastIndexOf("/")+1));
					result.put("original", fi.getName());
					result.put("type", imgs.substring(imgs.lastIndexOf(".")));
					result.put("url", imgs);
					result.put("size", fi.getSize());
				} else {
					result.put("state", "上传失败");
					result.put("title", imgs.substring(imgs.lastIndexOf("/")+1));
					result.put("original", "");
					result.put("type", "");
					result.put("url", "");
					result.put("size", fi.getSize());
				}
			}
		}
		return result;
	}
	
	
	/**
	 * @description: 获取request的上传文件
	 * 
	 * @param request
	 * @return
	 * @author 付强
	 * @date 2017年6月12日 下午2:01:26 
	 * @version 1.0.0.1
	 */
	private List<FileItem> getFileFromRequest(HttpServletRequest request) {
		List<FileItem> items = null;   // 得到所有的文件
		String sContentType = request.getContentType();
		if (StringUtils.contains(sContentType, "multipart/form-data")) {  // 如果文件是以二进制方式上传的
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			} 
		}
		return items;
	}
	
	
	/**
	 * @description: 远程上传文件
	 * 
	 * @param target cfile服务器接口的识别码
	 * @param fileName 
	 * @param b
	 * @return
	 * @author 付强
	 * @date 2017年6月12日 下午2:02:10 
	 * @version 1.0.0.1
	 */
	private String remoteUpload(String target, String fileName, byte[] b) {
		String returnString = "";
		try {
			String sUrl = url + target;
			MultipartEntityBuilder mb = MultipartEntityBuilder.create();
			mb.addBinaryBody("file", b, ContentType.MULTIPART_FORM_DATA, fileName);
			returnString = WebClientSupport.create().doRequest(sUrl, mb.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	
	/**
	 * 只为上传图片
	 * @param request
	 * @return
	 */
	public List<String> uploadPic(HttpServletRequest request){
		List<String> result = new ArrayList<String>();
		List<FileItem> items = this.getFileFromRequest(request);
		if (items != null && items.size() > 0) {
			for (FileItem fi : items) {
				String remoteUpload = this.remoteUpload("upload" , fi.getName() , fi.get());
				JSONObject t = JSONObject.parseObject(remoteUpload, JSONObject.class);
				result.add(t.getString("resultObject"));
			}
		}
		return result;
	}
	
	/**
	 * @description: 上传保存在本地的文件到服务器 
	 * 
	 * @param file
	 * @return 文件保存在服务器的路径
	 * @author Yangcl 
	 * @date 2017年7月13日 上午11:43:22 
	 * @version 1.0.0.1
	 */
	public JSONObject uploadLocalFile(File file){
		JSONObject result = new JSONObject();
		result.put("status", "error");
		JSONObject o = JSONObject.parseObject(this.remoteUpload("upload" , file.getName() , FileUtil.getInstance().getFileBytes(file)));
		if(o.getInteger("resultCode") == 1){
			result.put("status", "success");
			result.put("url", o.getString("resultObject")); 
		}
		return result; 
	}
	
 
}


















