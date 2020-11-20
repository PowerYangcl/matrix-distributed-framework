package com.matrix.util;

import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
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

	public JSONObject jarInject(JSONObject param) {
		JSONObject result = null;
		String jarFilePath = param.getString("jarFilePath");
		String entryName = param.getString("entryName"); 
		String data = param.getString("data");
		try {
			result = this.jarInject(jarFilePath, entryName, data);
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "Jar Util Exception");
			result.put("dto", param);
			result.put("Exception", ExceptionUtils.getExceptionInfo(e));
		}
		return result;
	}
	/**
	 * @description: 定向修复一个类文件
	 *
	 * @author Yangcl
	 * @date 2018年1月25日 下午2:57:16 
	 * @version 1.0.0.1
	 */
	public JSONObject jarInject(String jarFilePath, String entryName, String data) throws Exception {
		String[] arr = data.split(",");
		byte[] by = new byte[arr.length];
		for(int i = 0 ; i < arr.length ; i ++) {
			by[i] = Byte.parseByte(arr[i].trim());
		}
		return this.syringe(jarFilePath, entryName, by);
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
	
	public static void main(String args[]) throws Exception{
		long start = System.currentTimeMillis();
		String jarfile = "D:\\jar\\adefadf.jar";
		String data = "-54, -2, -70, -66, 0, 0, 0, 52, 0, 34, 10, 0, 6, 0, 20, 9, 0, 21, 0, 22, 8, 0, 23, 10, 0, 24, 0, 25, 7, 0, 26, 7, 0, 27, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 39, 76, 99, 111, 109, 47, 109, 97, 116, 114, 105, 120, 47, 99, 111, 110, 116, 114, 111, 108, 108, 101, 114, 47, 65, 100, 109, 105, 110, 67, 111, 110, 116, 114, 111, 108, 108, 101, 114, 59, 1, 0, 4, 109, 97, 105, 110, 1, 0, 22, 40, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 1, 0, 4, 97, 114, 103, 115, 1, 0, 19, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 20, 65, 100, 109, 105, 110, 67, 111, 110, 116, 114, 111, 108, 108, 101, 114, 46, 106, 97, 118, 97, 12, 0, 7, 0, 8, 7, 0, 28, 12, 0, 29, 0, 30, 1, 0, 52, -50, -75, -17, -68, -99, -50, -75, -17, -68, -99, -50, -75, -17, -68, -99, -50, -75, -17, -68, -99, 40, 35, 62, -48, -76, 60, 41, -17, -66, -119, 32, 32, 32, 87, 104, 111, 39, 115, 32, 121, 111, 117, 114, 32, 100, 97, 100, 100, 121, 32, 63, 32, 7, 0, 31, 12, 0, 32, 0, 33, 1, 0, 37, 99, 111, 109, 47, 109, 97, 116, 114, 105, 120, 47, 99, 111, 110, 116, 114, 111, 108, 108, 101, 114, 47, 65, 100, 109, 105, 110, 67, 111, 110, 116, 114, 111, 108, 108, 101, 114, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 121, 115, 116, 101, 109, 1, 0, 3, 111, 117, 116, 1, 0, 21, 76, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 19, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 1, 0, 7, 112, 114, 105, 110, 116, 108, 110, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 0, 33, 0, 5, 0, 6, 0, 0, 0, 0, 0, 2, 0, 1, 0, 7, 0, 8, 0, 1, 0, 9, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 2, 0, 10, 0, 0, 0, 6, 0, 1, 0, 0, 0, 3, 0, 11, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 12, 0, 13, 0, 0, 0, 9, 0, 14, 0, 15, 0, 1, 0, 9, 0, 0, 0, 55, 0, 2, 0, 1, 0, 0, 0, 9, -78, 0, 2, 18, 3, -74, 0, 4, -79, 0, 0, 0, 2, 0, 10, 0, 0, 0, 10, 0, 2, 0, 0, 0, 5, 0, 8, 0, 6, 0, 11, 0, 0, 0, 12, 0, 1, 0, 0, 0, 9, 0, 16, 0, 17, 0, 0, 0, 1, 0, 18, 0, 0, 0, 2, 0, 19";
		
		JarUtil u = new JarUtil();
		JSONObject o = u.jarInject(jarfile, "\\com\\Dddddde.class", data);
		
		long end = System.currentTimeMillis();
        System.out.println(end-start + " : " + o );
	}
	
	public static void main2(String args[]) throws Exception{
		long start = System.currentTimeMillis();
		File file = new File("D:\\jar\\Aler.class");
		byte[] fileBytes = FileUtil.getInstance().getFileBytes(file);
		
		String jarfile = "D:\\jar\\medfe.jar";
		
		JarUtil u = new JarUtil();
		JSONObject o = u.syringe(jarfile , "\\com\\Dddddde.class" , fileBytes ); 
		
		long end = System.currentTimeMillis();
        System.out.println(end-start + " : " + o );
	}
}




































