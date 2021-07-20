package com.matrix.system.cache;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;

/**
 * @descriptions 读取classpath*:META-INF/matrix/config/*.properties 配置文件
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月12日 下午6:28:16
 * @version 1.0.1
 */
public class LoadProperties extends BaseClass {

	/**
	 * @descriptions 加载属性配置
	 *
	 * @param dir
	 * @return
	 * @date 2016年11月12日 下午12:16:19
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Map<String,String> loadMap(String dir) {
		Collection<File> files = FileUtils.listFiles((new File(dir)) , new String[] { "properties" } , true);
		return loadMapFromFiles(files);
	}
	
	/**
	 * @descriptions 从文件中读取配置文件
	 *
	 * @param files
	 * @date 2016年11月12日 下午12:18:39
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Map<String , String> loadMapFromFiles(Collection<File> files) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			for (File f : files) {
				PropertiesConfiguration pconfig = new PropertiesConfiguration();
				FileInputStream fInputStream = FileUtils.openInputStream(f);
				pconfig.load(fInputStream , TopConst.CONST_BASE_ENCODING);
				Iterator<String> em = pconfig.getKeys();
				// 定义命名空间
				String nameSpace = StringUtils.defaultString(pconfig.getString("@this$namespace") , "");
				while (em.hasNext()) {
					String key = em.next();  
					String value = StringUtils.join(pconfig.getStringArray(key) , ",");
					if (StringUtils.isNotEmpty(nameSpace)) {
						if (!StringUtils.startsWith(key, "@") && !StringUtils.startsWith(key , nameSpace)) {
							key = nameSpace + "." + key;
						}
					}
					// 进行特殊判断模式
					if(StringUtils.startsWith(key , "@")) {
						String target = StringUtils.substringBetween(key , "@" , "$");
						if (target.equals("this")) {
							continue;
						}
						key = StringUtils.substringAfter(key, "$");
					} 
					if(!map.containsKey(key)) {
						map.put(key, value);
					}
					
				}
				fInputStream.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
}





















