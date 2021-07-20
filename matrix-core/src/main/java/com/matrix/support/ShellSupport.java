package com.matrix.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.util.ExceptionUtils;

/**
 * @deprecated
 * @description:动态触发Shell
 * 	TODO 无法实现权限解封
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年8月2日 下午8:36:00 
 * @version 1.0.0.1
 */
public class ShellSupport extends BaseClass{

	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		String cmd = param.getString("cmd");
		if(StringUtils.isBlank(cmd)) {
			result.put("status", "error");
			result.put("msg", "执行命令不得为空!");
			return result;
		}
		
		String[] args = null;
		if(StringUtils.isNotBlank(param.getString("args"))) {
			args = param.getString("args").split(",");
		}
		
		String[] cmds = null;
		if(args != null) {
			cmds = new String[args.length+1];
			cmds[0] = cmd;
			for(int i = 0 ; i < args.length ; i++) {
				cmds[i+1] = args[i];
			}
		}else {
			cmds = new String[1]; 
			cmds[0] = cmd;
		}
		
		return this.executer(cmds);
	}

	private JSONObject executer(String[] cmds){
		JSONObject result = new JSONObject();
        try{
        	Process process=Runtime.getRuntime().exec(cmds);
            // 等待程序执行结束并输出状态
            int code = process.waitFor();
            if (code == 0) {
            	result.put("status", "success");
    			result.put("msg", "The shell has been executed");
            	result.put("data", this.read(process.getInputStream()));
                return result;
            } else {
            	result.put("status", "error");
    			result.put("msg", "The shell execution failed");
    			result.put("data", this.read(process.getErrorStream()));
            }
        }catch(Exception e){
        	e.printStackTrace();
        	result.put("status", "error");
        	result.put("msg", "Shell Exception");
        	result.put("exception",  ExceptionUtils.getExceptionInfo(e));
        }
        return result;
    }
	
	private List<String> read(InputStream inputStream) {
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}

















