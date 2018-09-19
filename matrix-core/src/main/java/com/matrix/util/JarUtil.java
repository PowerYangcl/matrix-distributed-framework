package com.matrix.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;

/**
 * @description: 定向修复工具
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年1月25日 上午10:57:44 
 * @version 1.0.0
 */
public class JarUtil extends BaseClass{
	private JarUtil() {}  
	private static class LazyHolder {
		private static final JarUtil INSTANCE = new JarUtil();
	}
	public static final JarUtil getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	/**
	 * @description: 向匹配的jar文件中注入资源 
	 *
	 * @param pattern_
	 * @return 
	 * @author Yangcl
	 * @date 2018年1月25日 下午4:56:20 
	 * @version 1.0.0
	 */ 
	public JSONObject jarInject(String pattern_ , FileItem item) {
		JSONObject result = new JSONObject();
		if(!StringUtils.endsWith(pattern_ , "/")) {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090021));   
			return result;
		}
		result = this.classpathScanning(pattern_);
		if(result.getString("status").equals("success")){
			String name = "";
			List<String> list = JSONArray.parseArray(result.getJSONArray("list").toJSONString() , String.class); 
			try {
				for(String s : list) { 
					name = s;
					@SuppressWarnings("resource")
					JarFile jar = new JarFile(s); 
					Enumeration<JarEntry>  ens = jar.entries();
					while(ens.hasMoreElements()){
						JarEntry e = ens.nextElement(); 
						if(e.getName().equals(pattern_)) {
							this.syringe(name , e , item);  
							break;
						}
					}
				}
			} catch (Exception e) {
				result.put("status", "error");
				result.remove("list");
				result.put("msg", this.getInfo(100090020 , name, ExceptionUtils.getExceptionInfo(e)));   
				return result;
			}
		}
		return result;
	}
	
	/**
	 * @description: 扫描指定项目资源文件的jar包
	 *
	 * @param pattern_ classpath通配查找。
	 * @return 返回匹配资源文件名列表
	 * @author Yangcl
	 * @date 2018年1月25日 下午3:20:37 
	 * @version 1.0.0
	 */
	public JSONObject classpathScanning(String pattern_){
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(pattern_)) {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090018)); 
			return result;
		}
		List<String> files = new ArrayList<String>();
		try {
			Resource[] resources = IoUtil.getInstance().listResources("classpath*:" + pattern_);
			if(resources.length == 0) {
				result.put("status", "error");
				result.put("msg", this.getInfo(100090001));  
				return result;
			}
			for (Resource r : resources) {
				String path_ = StringUtils.substringAfter(r.getURI().toString(), "jar:file:/");
				files.add(path_.split("!")[0]); 
			}
		}catch (Exception e) { 
			result.put("status", "error");
			result.put("msg", this.getInfo(100090019, ExceptionUtils.getExceptionInfo(e)));   
			return result;
		}
		result.put("status", "success");
		result.put("list", files);   
		return result;
	}
   
	/**
	 * @description: Master get a syringe from the system and stabs the specified jar
	 *
	 * @param je JarEntry
	 * @param item FileItem
	 * @param path The jar file path in you system deploy
	 * @throws IOException 
	 * @author Yangcl
	 * @date 2018年1月27日 下午10:05:34 
	 * @version 1.0.0.1
	 */
	private JSONObject syringe(String path , JarEntry je , FileItem item) throws IOException{
		JSONObject result = new JSONObject();
		try {
			this.syringe(path , je.getName() + item.getName() , item.get()); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
        return result;  
    }
	
	/**
	 * @description: A real processer
	 * 	https://www.cnblogs.com/pfblog/p/7227184.html?utm_source=itdadao&utm_medium=referral  
	 *
	 * @param jarFilePath jar包部署路径信息
	 * @param entryName   要修改的文件名称，比如："\\META-INF\\matrix\\config.matrix-file.properties"
	 * @param data    
	 * @throws Exception 
	 * @author Yangcl
	 * @date 2018年1月27日 下午10:35:05 
	 * @version 1.0.0.1
	 */
	private JSONObject syringe(String jarFilePath, String entryName, byte[] data) throws Exception {
		JSONObject result = new JSONObject();
		@SuppressWarnings("resource")
		JarFile jarFile = new JarFile(jarFilePath); // 首先将原Jar包里的所有内容读取到内存里，用TreeMap保存
		TreeMap<String, byte[]> treemap = new TreeMap<String, byte[]>(); // 排列保存
		Enumeration<JarEntry> es = jarFile.entries();
		while (es.hasMoreElements()) {
			JarEntry je = (JarEntry) es.nextElement();
			byte[] b = FileUtil.getInstance().inputStreamToByte(jarFile.getInputStream(je));
			treemap.put(je.getName(), b);
		}

		JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFilePath));
		Iterator<Entry<String, byte[]>> it = treemap.entrySet().iterator();
		boolean has = false;
		while (it.hasNext()) { // 将TreeMap重新写到原jar里，如果TreeMap里已经有entryName文件那么覆盖，否则在最后添加
			Map.Entry<String, byte[]> item = (Map.Entry<String, byte[]>) it.next();
			String name = (String) item.getKey();
			JarEntry entry = new JarEntry(name);
			jos.putNextEntry(entry);
			byte[] temp;
			if (name.equals(entryName)) { // 定位到了jar包中的文件，并修改他；has = true代表这是一个修改的情况
				temp = data;
				has = true;
			} else {
				temp = (byte[]) item.getValue();
			}
			jos.write(temp, 0, temp.length);
		}

		if (!has) { // 如果便利jar包没有找到对应的替换文件，则添加。
			JarEntry newEntry = new JarEntry(entryName);
			jos.putNextEntry(newEntry);
			jos.write(data, 0, data.length);
		}
		jos.finish();
		jos.close();
		
		// 判断是否成功
		Boolean flag = false;
		jarFile = new JarFile(jarFilePath);  
		Enumeration<JarEntry> es_ = jarFile.entries();
		while (es_.hasMoreElements()) {
			JarEntry je = (JarEntry) es_.nextElement();
			if(je.getName().equals(entryName)) {
				flag = true;
				break;
			}
		}
		if(flag) {
			result.put("status" , "success"); 
		}else {
			result.put("status" , "error"); 
			result.put("msg" , this.getInfo(100090022) );         // 100090022=操作失败!
		}
		return result;
	}
	
	private JSONObject syringe2(String jarFilePath, String entryName, byte[] data , String type) {
		JSONObject result = new JSONObject();
		
		String cmd = "jar -uf D:\\matrix-file-0.0.1-SNAPSHOT.jar config.matrix-file.properties";
//		String[] cmds = {"/bin/sh","-c",cmd};  
		String[] cmds = {cmd};  
        Process pro;
        try {
            pro = Runtime.getRuntime().exec(cmds);
            pro.waitFor();  
            InputStream in = pro.getInputStream();  
            BufferedReader read = new BufferedReader(new InputStreamReader(in));  
            String line = null;  
            while((line = read.readLine())!=null){  
                System.out.println(line);  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        } 
	
		return result;
	}
	
	public static void main(String args[]) throws Exception{
        
		String jarfile = "D:\\matrix-file-0.0.1-SNAPSHOT.jar";
        

        
        String data = "visit_url_master=testqqq";
        long start = System.currentTimeMillis();
        
        JarUtil.getInstance().syringe2(jarfile , "\\META-INF\\matrix\\config.matrix-file.properties",data.getBytes() , ""); 
        
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
    
	
}





































