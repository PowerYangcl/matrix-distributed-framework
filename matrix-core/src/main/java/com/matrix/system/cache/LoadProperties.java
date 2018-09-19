package com.matrix.system.cache;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.map.MStringMap;

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
	public MStringMap loadMap(String dir) {
		Collection<File> files = FileUtils.listFiles((new File(dir)) , new String[] { "properties" } , true);
		return loadMapFromFiles(files);
	}
	
	
	/**
	 * @descriptions 从文件中读取配置文件
	 *
	 * @param files
	 * @return
	 * @date 2016年11月12日 下午12:18:39
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public MStringMap loadMapFromFiles(Collection<File> files) {
		MStringMap mReturnMap = new MStringMap();
		try {
			for (File f : files) {
				PropertiesConfiguration pconfig = new PropertiesConfiguration();
				FileInputStream fInputStream = FileUtils.openInputStream(f);
				pconfig.load(fInputStream , TopConst.CONST_BASE_ENCODING);
				Iterator<String> em = pconfig.getKeys();
				// 定义命名空间
				String sNameSpace = StringUtils.defaultString(pconfig.getString("@this$namespace") , "");

				while (em.hasNext()) {
					String key = em.next();  
					// 定义是否强制覆盖
					boolean bOverWrite = false;
					String sValueString = StringUtils.join(pconfig.getStringArray(key) , ",");
					if (StringUtils.isNotEmpty(sNameSpace)) {
						if (!StringUtils.startsWith(key, "@") && !StringUtils.startsWith(key , sNameSpace)) {
							key = sNameSpace + "." + key;
						}
					}
					// 进行特殊判断模式
					if (StringUtils.startsWith(key , "@")) {
						String sTarget = StringUtils.substringBetween(key , "@" , "$");
						// 覆写配置
						if (sTarget.equals("override")) {	// override
							bOverWrite = true;
						}
						// 本配置指向
						else if (sTarget.equals("this")) {			// TODO  项目配置文件默认指向此处
						}
						key = StringUtils.substringAfter(key, "$");
					} 
					if (bOverWrite || !mReturnMap.containsKey(key)) {
						mReturnMap.put(key , sValueString);
					}
				}
				fInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mReturnMap;
	}

}





















