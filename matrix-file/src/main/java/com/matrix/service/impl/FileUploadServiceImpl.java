package com.matrix.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.dao.ISysFileRecordMapper;
import com.matrix.pojo.entity.SysFileRecord;
import com.matrix.service.IFileUploadService;
import com.matrix.support.FileSupport;
import com.matrix.util.DateUtil;
import com.matrix.util.IpUtil;
import com.matrix.util.JarUtil;
import com.matrix.util.UuidUtil;

@Service("fileUploadService")
public class FileUploadServiceImpl extends BaseClass implements IFileUploadService{
	
	@Resource
	private ISysFileRecordMapper sysFileRecordMapper;

	/**
	 * @description: 从页面Request请求获取文件保存到服务器
	 *
	 * @param request
	 * @author Yangcl
	 * @date 2018年10月25日 下午2:35:25 
	 * @version 1.0.0.1
	 */
	public JSONObject apiFileRemoteUpload(HttpServletRequest request) {
		JSONObject result = null;
		List<FileItem> fileItems = this.getFileFromRequest(request);
		if(fileItems != null && fileItems.size() != 0){
			result = this.saveFile(fileItems.get(0));
		}else{
			result = new JSONObject();
			result.put("status", "error");
			result.put("msg", this.getInfo(500010006));   // 500010006=未发现要上传的文件，请核实。
		}
		return result;
	}
	
	/**
	 * @description: 服务器写入文件到本地
	 *
	 * @param fileName
	 * @param file
	 * @author Yangcl
	 * @date 2018年10月25日 下午2:36:39 
	 * @version 1.0.0.1
	 */
	public JSONObject saveFileToLinux(String fileName , byte[] file) {
		return this.saveFile(fileName , file , file.length);
	}
	
	/**
	 * @descriptions  获取request的上传文件
	 *
	 * @param request
	 * @date 2017年8月2日 下午1:46:04
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private List<FileItem> getFileFromRequest(HttpServletRequest request) {
		List<FileItem> items = null;   // 得到所有的文件
		String contentType = request.getContentType();
		if (StringUtils.contains(contentType , "multipart/form-data")) {  // 如果文件是以二进制方式上传的
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
	
	private JSONObject saveFile(FileItem item){
		return this.saveFile(item.getName(), item.get() , item.getSize());
	}
	
	
	/**
	 * @descriptions 持久化一个文件到硬盘
	 *
	 * @param fileName 要保存文件的名称
	 * @param file 该文件的二进制流
	 * @date 2017年8月2日 下午2:39:57
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private JSONObject saveFile(String fileName , byte[] file , long size){
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(fileName)){
			result.put("status", "error");
			result.put("msg", this.getInfo(500010001));   // 500010001=文件名称不得为空 
			return result;
		}
		if(fileName.split("\\.").length < 2){
			result.put("status", "error");
			result.put("msg", this.getInfo(500010002));   // 500010002=文件名称错误，缺少后缀
			return result;
		}
		String postfix = StringUtils.substringAfterLast(fileName, ".").toLowerCase(); // 取得文件后缀名
		String allowFile = this.getConfig("matrix-file.upload_file_postfix"); // 取得允许上传的文件后缀
		if(StringUtils.containsNone(allowFile, postfix)){
			result.put("status", "error");
			result.put("msg", this.getInfo(500010003 , postfix));   // 500010003=服务器不支持上传【.{0}】类型的文件
			return result;
		}
		String postfix_ = postfix;
		String newName = UuidUtil.uid() + "." + postfix; 
		// 所有图片文件都放到 image 文件夹下
		String[] arr = this.getConfig("matrix-file.image_type").split(",");
		for(String s : arr) {
			if(s.equals(postfix)) {
				postfix = "image";  
				break;
			}
		}
		
		// 文件保存路径
		String hexFolder = DateUtil.getDateHex();
		String filePath = this.getConfig("matrix-file.server_basic_folder_" + this.getConfig("matrix-core.model")) + File.separator 
				+ this.getConfig("matrix-file.upload_path_" + postfix) + File.separator + hexFolder + File.separator;
		try {
			FileUtils.forceMkdir(new File(filePath));
			File out = new File(filePath + newName);
			FileCopyUtils.copy(file, out); // 复制文件到服务器
			result.put("status", "success");
			result.put("msg", this.getInfo(500010005));   // 500010005=文件上传完成
			result.put("title" , newName);
			// result.put("filePath" , filePath);
			result.put("original" , fileName);
			result.put("type" , postfix_);
			result.put("save", this.getConfig("matrix-file.upload_path_" + postfix) + "/"  + hexFolder + "/" + newName);  // 数据库的保存路径
			result.put("url" , this.getConfig("matrix-file.visit_url_" + this.getConfig("matrix-core.model")) + result.getString("save"));  // 文件的可访问路径 
			result.put("size", Long.toString(size));   
			if(postfix.equals("image")){
				result.put("type" , postfix);  // 图片全部归并为一个类型
				JSONObject img = FileSupport.getInstance().getImageProperty(result.getString("url")); 
				result.put("width", img.getString("width"));
				result.put("height", img.getString("height"));
			}
			result.put("ip", IpUtil.getHostIpAddress());
		} catch (IOException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(500010004));   // 500010004=文件持久化异常，请参照FileUploadServiceImpl.java -> saveFile() 
			return result;
		}
		this.getLogger(null).sysoutInfo(result.toJSONString() , this.getClass()); 
		return result;
	}


	@Override
	public JSONObject apiFileRemoteInject(HttpServletRequest request) {
		JSONObject result = null;
		List<FileItem> fileItems = this.getFileFromRequest(request);
		if(fileItems != null && fileItems.size() != 0){ 
			JarUtil.getInstance().jarInject(this.getConfig("matrix-core.spring_core") , fileItems.get(0));
		}else{
			result = new JSONObject();
			result.put("status", "error");
			result.put("msg", this.getInfo(500010006));   // 500010006=未发现要上传的文件，请核实。
		}
		return result;
	}

	/**
	 * @description: 插入文件后，添加文件信息
	 *
	 * @param uploadFile
	 * @author Yangcl
	 * @date 2018年11月1日 下午3:32:14 
	 * @version 1.0.0.1
	 */
	public void insertUploadFileInfo(JSONObject uploadFile) {
		try {
			SysFileRecord e = JSONObject.parseObject(uploadFile.toJSONString(), SysFileRecord.class);
			e.setCreateTime(new Date());
			e.setCreateUserId(1L);
			e.setCreateUserName("system");
			e.setUpdateTime(new Date());
			e.setUpdateUserId(1L);
			e.setUpdateUserName("system");
			sysFileRecordMapper.insertSelective(e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}




































