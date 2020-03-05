package com.matrix.support;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.util.ExceptionUtils;
import com.matrix.util.ZkUtil;

/**
 * @description: zk简易操作封装支持，请使用者陆续补充功能
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月02日 下午7:21:58 
 * @version 1.0.0.1
 */
public class ZookeeperSupport extends BaseClass{

	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		ZkUtil zk = null;
		try {
			String host = this.getConfig("matrix-core.zk_host");
			if(StringUtils.isBlank(host)) {
				host = param.getString("host");
			}
			zk = new ZkUtil(host);
			String func = param.getString("type");
			switch (func) { 
				case "a":
					zk.addNode( param.getString("path") , param.getString("data"));
					break;
				case "b":
					zk.deleteNode(param.getString("path"));
					break;
				case "c":
					zk.updateData(param.getString("path"), param.getString("data"));
					break;
				case "d":
					result.put("data", zk.listFirstNode(param.getString("path")));
					break;
				case "e":
					result.put("data", zk.listAllNode(param.getString("path")));
					break;
				case "f":
					result.put("data", zk.findNodeValue(param.getString("path")));
					break;
				case "g":
					result.put("data", zk.getCreateTime( param.getString("path") ));
					break;
				case "h":
					result.put("data", zk.getChildrenNum( param.getString("path") ));
					break;
				default:
					break;
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "Zookeeper Exception!");
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
			return result;
		}finally {
			if(zk != null) {
				zk.closeConn();
			}
		}
		
		result.put("status", "success");
		result.put("msg", "命令执行成功!");
		result.put("dto", param);
		return result;
	}

}



