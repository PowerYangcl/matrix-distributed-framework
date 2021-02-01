package com.matrix.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.service.IPowerCacheService;
import com.matrix.system.cache.PowerCache;
import com.matrix.util.ExceptionUtils;
import com.matrix.util.ZkUtil;

@Service("powerCacheService")
public class PowerCacheServiceImpl extends BaseClass implements IPowerCacheService{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	/**
	 * @description: 查看缓存中的数据状态信息
	 * 
	 * @param prefix 缓存key的前缀
	 * @param key 缓存key的后缀
	 * @param type 缓存类型 ：dict|serv
	 * @return
	 * @author Yangcl 
	 * @date 2017年5月26日 下午1:16:56 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnGetCache(String prefix , String key  , String type) {
		JSONObject result = new JSONObject();
		
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		String value = "";
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , prefix + "Init" ).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , prefix + "Init" ).get(key); 
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
		
		
		if(StringUtils.isNotBlank(value)){
			result.put("status", "success");
			result.put("msg", "查询成功");
			try {
				result.put("data", JSONObject.parseObject(value)); 
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", value); // 非对象而是一个提示消息，比如：300010100=该缓存key指向唯一{0},请勿传入其他key
			}
		}else{
			result.put("status", "error");
			result.put("msg", "未找到对应的值");
		}
		return result;
	}

	/**
	 * @description: 删除缓存中的数据
	 * 
	 * @param prefix 缓存key的前缀
	 * @param key 缓存中的key
	 * @param type 缓存类型 ：dict|serv
	 * @author Yangcl 
	 * @date 2018年11月14日 上午11:14:52 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnDeleteCache(String prefix, String key, String type) {
		JSONObject result = new JSONObject();
		
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		String value = "";
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).get(key); 
			}
			
			if(StringUtils.isNotBlank(value)){
				if(type.equals("dict")){
					DCacheEnum [] arr = DCacheEnum.values();
					launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).del(key);
				}else{
					SCacheEnum [] arr = SCacheEnum.values();
					launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).del(key); 
				}
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(300010103));  // 300010103=缓存不存在
				return result;
			}
			
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).get(key); 
			}
			
			if(StringUtils.isBlank(value)) {
				result.put("status", "success");
				result.put("msg", this.getInfo(300010101));  // 300010101=缓存删除成功
				return result;
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(300010102));  // 300010102=缓存删除失败
				return result;
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
	}

    /**
     * @description: 通过关键字，批量删除缓存的数据
     *
     * @param prefix 缓存key的前缀
     * @param key 缓存中的key
     * @param type 缓存类型 ：dict|serv
     * @author Sjh
     * @date 2018/11/21 10:55
     * @version 1.0.0.1
     */
	public JSONObject ajaxBtnBatchDeleteCache(String prefix, String key, String type) {
		JSONObject result = new JSONObject();
//		if(StringUtils.isBlank(key)){
//			result.put("status", "error");
//			result.put("msg", "缓存key不得为空");
//			return result;
//		}
		if(StringUtils.isBlank(prefix)){
			result.put("status", "error");
			result.put("msg", "缓存prefix不得为空");
			return result;
		}

		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).batchDel(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).batchDel(key);
			}
			result.put("status", "success");
			result.put("msg", this.getInfo(300010101));  // 300010101=缓存删除成功
			return result;
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
	}

	/**
	 * @description: 重新设置一个缓存值
	 * 
	 * @param prefix 缓存key的前缀
	 * @param key 缓存中的key
	 * @param type 缓存类型 ：dict|serv
	 * @param jsonStr 缓存的json字符串
	 * @author Yangcl 
	 * @date 2018年11月14日 下午19:42:26 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnResetCache(String prefix, String key, String type , String jsonStr) {
		JSONObject result = new JSONObject();
		
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr , 30*60);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr , 30*60); 
			}
			
			result.put("status", "success");
			result.put("msg", this.getInfo(300010104));  // 300010104=缓存重置成功
			return result;
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
	}


	/**
	 * @description: 重新设置一个缓存值|永久生效
	 *
	 * @param prefix 缓存key的前缀
	 * @param key 缓存中的key
	 * @param type 缓存类型 ：dict|serv
	 * @param jsonStr 缓存的json字符串
	 * @author Yangcl
	 * @date 2018年11月14日 下午19:42:26
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnResetCacheForever(String prefix, String key, String type, String jsonStr) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr); 
			}
			
			result.put("status", "success");
			result.put("msg", this.getInfo(300010104));  // 300010104=缓存重置成功
			return result;
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
	}

	
	
    /**
     * @description: 缓存API实例化
     *
     * @param dto.name
     * @param dto.param
     * @author Yangcl
     * @date 2018年12月22日 上午9:21:15 
     * @version 1.0.0.1
     */
	public JSONObject apiCacheInit(JSONObject dto, HttpSession session) {
		JSONObject result = new JSONObject();
		try {
			Class<?> clazz = Class.forName(dto.getString("name"));   
			if (clazz != null && clazz.getDeclaredMethods() != null) {
				Method m = clazz.getMethod(dto.getString("func") , JSONObject.class);
				Object re = m.invoke(clazz.newInstance() , dto.getJSONObject("param"));
				return JSONObject.parseObject(re.toString());
			}
		}catch (Exception e) {
			result.put("status" , "error");
			result.put("msg" , "exception in you request");
			result.put("exception" , ExceptionUtils.getExceptionInfo(e));
			result.put("dto", dto);
			return result;
		}  
		
		result.put("status" , "success");
		result.put("msg" , "类名反射异常");
		return result;
	}

}





































// 以下为反射测试，等待迁移到其他包 - 2018年1月9日 

class Dsupport extends BaseClass {
	
	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		String type = param.getString("type");
		String shell = "";
		String arr[] = this.getConfig("matrix-core.s_" + type).split("@");
		for(String s : arr) {
			shell += s + " ";
		}
		shell += param.getString("t_n") + " ";
		for(int i = 0 ;i < param.getInteger("num") ; i ++) {
			shell += param.getString(param.getString("type") + "_" + i) + " ";
		}

		Connection con = this.getConnection(param.getString("b_u") , param.getString("b_n") , param.getString("b_p")); // 获取连接
		try {
			PreparedStatement pstmt = con.prepareStatement(shell);
			if(!type.equals("s")) {
				int count=pstmt.executeUpdate();
				result.put("count", count);
			}else {
				ResultSet rs = pstmt.executeQuery();
				result.put("data", this.convertList(rs));
			}
			this.close(pstmt, con);
			
			result.put("status", "success");
			result.put("msg", "命令执行成功!");
			result.put("dto", param);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "SQLException!");
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
		}
		return result;
	}

	private Connection getConnection(String url , String name , String password) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, name, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void close(PreparedStatement pstmt,Connection con){
		if(pstmt!=null){
			try {
				pstmt.close();
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @description: 返回查询结果集 
	 *
	 * @author Yangcl
	 * @date 2018年1月23日 下午6:07:30 
	 * @version 1.0.0.1
	 */
	private List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    ResultSetMetaData md = rs.getMetaData();
       int columnCount = md.getColumnCount();
       while (rs.next()) {
           Map<String, Object> entity = new HashMap<String, Object>();
           for (int i = 1; i <= columnCount; i++) {
               entity.put(md.getColumnName(i), rs.getObject(i));
           }
           list.add(entity);
       }
	    return list;
	}
}

class Psupport extends BaseClass{

	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		try {
			String type = param.getString("type");
			String name = param.getString("name");
			switch (type) {
				case "s":
					String value = (String) PowerCache.getInstance().find(name , param.getString("key"));
					if(StringUtils.isBlank(value)) {
						value = "未找到对应的缓存";
					}
					result.put("data", value );
					break;
				case "i":
					PowerCache.getInstance().compelPut(name , param.getString("key"), param.getString("value"));
					break;
				case "d":
					boolean remove = PowerCache.getInstance().remove(name , param.getString("key"));
					if(remove) {
						result.put("data", "Ehcache has removed" );
					}else {
						result.put("data", "This operation fails, check your key or cache name" );
					}
					break;
				case "u":
					PowerCache.getInstance().reset(name , param.getString("key"), param.getString("value"));
					break;
				default:
					break;
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "Ehcache Exception!");
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
			return result;
		}
		
		result.put("status", "success");
		result.put("msg", "命令执行成功!");
		result.put("dto", param);
		return result;
	}
}

class Ssupport extends BaseClass{

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

class Zsupport extends BaseClass{

	public JSONObject support(JSONObject param) {
		JSONObject result = new JSONObject();
		ZkUtil zk = null;
		try {
			String host = this.getConfig("matrix-core.zk_host");
			if(StringUtils.isBlank(host)) {
				host = param.getString("host");
			}
			zk = new ZkUtil(this.getConfig("matrix-core.zk_host"));
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
