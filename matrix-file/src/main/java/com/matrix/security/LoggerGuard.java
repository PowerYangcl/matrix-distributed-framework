package com.matrix.security;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.util.ExceptionUtils;

/**
 * @description:日志守卫 
 * 	
 * @param json：{"line":"300","file":"stdout.log"}
 *
 * @author Yangcl
 * @date 2019年1月7日 上午11:29:15 
 * @version 1.0.0.1
 */
public class LoggerGuard extends BaseClass implements IBaseExecute {

	public String execute(String json) {
		JSONObject result = new JSONObject();
		
		JSONObject param = JSONObject.parseObject(json);
//		int count = 500; // 默认500行
//		String line = param.getString("line");
//		if(StringUtils.isBlank(line)) {
//			count = Integer.parseInt(line);
//		}
		
		String fileName = param.getString("file");
		if(StringUtils.isBlank(fileName)) {
			fileName = "stdout.log";
		}
		
		String path = this.getClass().getClassLoader().getResource("").getPath().split("properties")[0]  + "logs/" + fileName;  // 默认dubbo应用服务
		if(StringUtils.isNotBlank(this.getConfig("matrix-web.model"))) {		// web系统
			if(StringUtils.isBlank(param.getString("file"))) {
				fileName = "catalina.out";
			}
			path = "/data/tomcat/logs/" + fileName;
		}
		
		result.put("status", "success");
		File file = new File(path);
		List<String> lines = new ArrayList<>();
		try {
			lines = FileUtils.readLines(file, "UTF-8");
			result.put("lines", lines);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("exception", ExceptionUtils.getExceptionInfo(e)); 
		}
		
		this.getLogger(null).sysoutInfo("LoggerGuard.java 文件读取完成！", this.getClass());
		
		return result.toJSONString();
	}

}




































