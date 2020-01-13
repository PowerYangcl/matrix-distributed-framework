package com.matrix.security;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.util.ExceptionUtils;
import com.matrix.util.FileUtil;

/**
 * @description: 文件安全维护者
 * 
 * @param json：{"type":"read/write/list/del","file":"//->根目录 or a real path in linux","content":"写入的文本","flag":"true->只显示第一层"}
 *
 * @author Yangcl
 * @date 2019年1月6日 下午7:52:19 
 * @version 1.0.0.1
 */
public class FileGuard extends BaseClass implements IBaseExecute {

	public String execute(String json) {
		JSONObject param = JSONObject.parseObject(json);
		String type = param.getString("type");
		if(type.equals("list")) {
			return this.list(param).toJSONString();
		}
		
		if(type.equals("add")) {
			return this.add(param).toJSONString();
		}
		
		if(type.equals("del")) {
			return this.del(param).toJSONString();
		}
		
		return null;
	}

	
	private JSONObject list(JSONObject param) {
		JSONObject result = new JSONObject();
		String file = param.getString("file");
		if(StringUtils.isBlank(file)) {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 100090002=没有查询到可以显示的数据
			return result;
		}
		boolean flag = false;
		if(StringUtils.isBlank(param.getString("flag")) ||param.getString("flag").equals("true") || file.equals("//")) {
			flag = true;  // 根目录只获取第一层级
		}
		File f = new File(file);
		List<String> contentList = new ArrayList<String>(); 
		List<String> list = FileUtil.getInstance().showDir(f, 0 , contentList , flag);
		if(list == null || list.size() == 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 100090002=没有查询到可以显示的数据
			return result;
		}
		result.put("status", "success");
		result.put("data", list);
		return result;
	}
	
	private JSONObject add(JSONObject param) {
		JSONObject result = new JSONObject();
		
		
		return result;
	}
	
	private JSONObject del(JSONObject param) {
		JSONObject result = new JSONObject();
		try {
			String file = param.getString("file");
			if(StringUtils.isBlank(file)) {
				result.put("status", "error");
				result.put("msg", this.getInfo(100090002));  // 100090002=没有查询到可以显示的数据
				return result;
			}
			
			File f = new File(file);
			this.delFile(f);
			result.put("status", "success");
			result.put("data", f.listFiles().length);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "系统异常");
			result.put("data", ExceptionUtils.getExceptionInfo(e));
			return result;
		}
		return result;
	}
	
	private boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
            return file.delete();
        }
    }
}







































