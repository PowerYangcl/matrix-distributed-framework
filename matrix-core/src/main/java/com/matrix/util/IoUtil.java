package com.matrix.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


/**
 * @description: IO工具类 
 * @alias IoHelper
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月11日 下午8:51:10 
 * @version 1.0.0
 */
public class IoUtil {

	private IoUtil() {
	}
	private static class LazyHolder {
		private static final IoUtil INSTANCE = new IoUtil();
	}
	public static final IoUtil getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	/**
	 * @description:  创建目录
	 * 
	 * @param dirPath 目录地址
	 * @author Yangcl 
	 * @date 2016年11月11日 下午8:52:42 
	 * @version 1.0.0.1
	 */
	public static void createDir(String dirPath) {
		try {
			File file = new File(dirPath);			
			if(file.exists()) {
				return;
			}
			FileUtils.forceMkdir(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description: 根据名称获取资源 名称支持正则表达式 
	 * 
	 * @param pattern_ classpath通配查找。
	 * 		比如：classpath*:org/springframework/core/   -> 此处表达为查找项目路径下包含org/springframework/core/文件夹的资源。
	 * 		或者：classpath*:META-INF/api_pages/  	-> 此处表达为查找项目路径下含有META-INF/api_pages/的资源文件
	 *  	    or    ：classpath*:com/++/Driver.class	(此处需要把++替换为**) 
	 *  
	 * @author Yangcl 
	 * @date 2016年11月11日 下午8:52:16 
	 * @version 1.0.0.1
	 */
	public Resource[] listResources(String pattern_){
		Resource[] resources = null;
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		try {
			resources = patternResolver.getResources(pattern_);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resources;
	}
	

	/**
	 * @description: 复制资源
	 * 
	 * @param sFromClass
	 * @param sToPath
	 * @param sKeyName
	 * @author Yangcl 
	 * @date 2016年11月11日 下午8:53:06 
	 * @version 1.0.0.1
	 */
	public void copyResources(String sFromClass, String sToPath, String sKeyName) {
		try {
			Resource[] resources = this.listResources(sFromClass);
			for (Resource r : resources) {
				String sUrlString = StringUtils.substringAfter(r.getURI().toString(), sKeyName);
				InputStream inStream = r.getInputStream(); // 读入原文件
				new File(sToPath + sUrlString).getParentFile().mkdirs();
				this.fileCopy(inStream , sToPath + sUrlString); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @descriptions 文件夹拷贝(文件内含有文件和文件夹) 
	 *		从jar包中复制资源到指定的项目目录下
	 *
	 * @param sources 源
	 * @param target 目标路径
	 * @date 2016年11月30日 下午10:01:42
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public void copyDir(String sources, String target) throws IOException, URISyntaxException {  
		target = StringUtils.substringBefore(target, "api_pages/");
		Resource[] resources = this.listResources(sources);
		File mdir = null;
		for (Resource r : resources) {
			String path = StringUtils.substringAfter(r.getURI().toString(), "jar:file:/");
			String jarName = path.split("!")[0];
			JarFile jar = new JarFile(jarName);
			Enumeration<JarEntry>  ens = jar.entries();
			while(ens.hasMoreElements()){
				JarEntry e = ens.nextElement(); 
				if(StringUtils.startsWith(e.getName(), "META-INF/api_pages/")){
					if(e.isDirectory()){
						mdir = new File(target + StringUtils.substringAfter(e.getName(), "META-INF/")); 
						if(!mdir.exists()){
							mdir.mkdirs();
						}
					}else{
						InputStream input = jar.getInputStream(e);
						this.fileCopy(input, target + StringUtils.substringAfter(e.getName(), "META-INF/")); 
					}
				}
			}
		}
    }
	
	/**
	 * @description: 文件复制 
	 *
	 * @param input
	 * @param target_ 
	 * @author Yangcl
	 * @date 2018年1月26日 下午5:44:46 
	 * @version 1.0.0
	 */
	public void fileCopy(InputStream input, String target_){
		File target = new File(target_);
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(target);
			IOUtils.copy(input, fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fs.flush();
				fs.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		 
		
	}
	
}













