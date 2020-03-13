package com.matrix.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.service.IFileUploadService;

/**
 * @descriptions 上传文件相关接口 | 所有上传的文件必须以二进制方式上传
 * 		所有的文件上传操作每次只允许上传一个文件，不允许多个文件同时提交
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2017年7月31日 下午10:34:37
 * @version 1.0.1
 */
@Controller
@RequestMapping("file")
public class FileUploadController  extends BaseController{

	@Autowired
	private IFileUploadService service;
	
	/**
	 * @descriptions 支持其他web系统上传文件到公司指定文件服务器||TODO 还缺少一个方法，将服务器图片发布到CDN  ！！！！！！！！！！！！！！！！
	 * 		这个接口不公开给第三方调用。
	 * @返回结构示例：
			 {
			    "status": "success",				状态：success or error
			    "msg": "文件上传完成",
			    "original": "bc.png",			图片原标题
			    "title": "bf92f1576b23470a948dbdcb8feba788.png",  		图片新标题
			    "size": "62091",        文件大小
			    "save": "image/29c10/bf92f1576b23470a948dbdcb8feba788.png",            用于保存到数据库
			    "type": "image",		文件类型
			    "url": "http://192.168.1.34:8080/matrix-file/image/29c10/bf92f1576b23470a948dbdcb8feba788.png",	可访问路径
			    "height": "247"		图片高
			    "width": "163",		图片宽
			}
	 *
	 * @param request 页面提交的数据
	 * @param key 所属项目名称
	 * @param value md5加密验证的结果
	 *   
	 * @date 2017年7月31日 下午10:42:29
	 * @author Yangcl 
	 * @version 1.0.0.1
	 * @throws IOException 
	 */
	@RequestMapping(value = "api_file_remote_upload", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject apiFileRemoteUpload(HttpServletRequest request , HttpServletResponse response) throws IOException{
		JSONObject result = new JSONObject();
		
		String originHeader = request.getHeader("Origin");
		if (StringUtils.isNotBlank(originHeader)) {
			
			if(this.getConfig("matrix-core.model").equals("master")) {
				String allowDomain = this.getConfig("matrix-file.access_control_allow_origin_" + this.getConfig("matrix-core.model")); 
				if (StringUtils.contains(allowDomain, originHeader)){
					response.setHeader("Access-Control-Allow-Origin", originHeader); // 解决跨域访问限制
				}else {
					result.put("status", "error");
					result.put("msg", this.getInfo(500010007));  // 您所请求的接口不对第三方开放
					return result;
				} 
			}else {
				response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域访问限制，开发环境和测试环境不在限制跨域
			}
			
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Methods", "POST");  // , GET, OPTIONS, DELETE
			response.setHeader("Access-Control-Max-Age", "3600");  // 头指定了preflight请求的结果能够被缓存3600s
			// 标明服务器支持的所有头信息字段
			response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
			// Access-Control-Allow-Credentials true 则允许浏览器读取response的内容。当用在对preflight预检测请求的响应中时，它指定了实际的请求是否可以使用credentials。
			//请注意：简单 GET 请求不会被预检；如果对此类请求的响应中不包含该字段，这个响应将被忽略掉，并且浏览器也不会将相应内容返回给网页。
			response.setHeader("Access-Control-Allow-Credentials", "true"); 
			response.setHeader("XDomainRequestAllowed","1");
			
			result = service.apiFileRemoteUpload(request);
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(500010008));  // 500010008=文件上传接口验证失败!
			return result;
		}
		
		return result;
	}
}



























