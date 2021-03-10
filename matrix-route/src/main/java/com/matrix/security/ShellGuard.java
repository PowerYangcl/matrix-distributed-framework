package com.matrix.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.util.ExceptionUtils;


/**
 * @description: 执行shell脚本 
 *
 *@param json  利用"sh -c"命令让 bash 将一个字串作为完整的命令来执行
			 	{
					"cmd": "sh",
					"args": "-c,/apps/+++++++++++++/-----------/&&&&&&&&&&/************.sh"
				}
		或：
				{
					"cmd": "sh",
					"args": "-c,/apps/+++++++++++++/-----------/&&&&&&&&&&/************.sh,param1,param2,param3............"
				}
 * @author Yangcl
 * @date 2019年1月10日 下午12:18:08 
 * @version 1.0.0.1
 */
public class ShellGuard extends BaseClass implements IBaseExecute {

	public String execute(String json) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(json)) {
			result.put("status", "error");
			result.put("msg", "参数不得为空!");
			return result.toJSONString();
		}
		JSONObject param = JSONObject.parseObject(json);
		String cmd = param.getString("cmd");
		if(StringUtils.isBlank(cmd)) {
			result.put("status", "error");
			result.put("msg", "执行命令不得为空!");
			return result.toJSONString();
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
		return this.executer(cmds).toJSONString();
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
			result.put("msg", "Exception ! The shell execution failed , find a exception : " + ExceptionUtils.getExceptionInfo(e));
//			this.getLogger(null).sysoutInfo("--------------------------------------------------------------------------------Exception ! The shell execution failed , find a exception", this.getClass());
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


























