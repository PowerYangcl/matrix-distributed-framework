package com.matrix.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions 基础的文件操作工具
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2017年8月2日 下午5:37:18
 * @version 1.0.1
 */
public class FileUtil {
	
	private FileUtil(){
	}
	private static class LazyHolder{
		private static final FileUtil INSTANCE = new FileUtil();
	}
	public static final FileUtil getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	/**
	 * @description:获得指定文件的byte数组 
	 * 
	 * @param file 文件
	 * @author Yangcl 
	 * @date 2017年7月13日 上午11:40:11 
	 * @version 1.0.0.1
	 */
	public byte[] getFileBytes(File file){  
	    byte[] buffer = null;  
	    try {  
	        FileInputStream fis = new FileInputStream(file);  
	        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);  
	        byte[] b = new byte[1024];  
	        int n;  
	        while ((n = fis.read(b)) != -1) {  
	            bos.write(b, 0, n);  
	        }  
	        fis.close();  
	        bos.close();  
	        buffer = bos.toByteArray();  
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return buffer;  
	}
	
	/**
	 * @description: 将输入流转换为字节数组
	 *
	 * @param inStream
	 * @return 
	 * @author Yangcl
	 * @date 2018年1月27日 下午10:22:13 
	 * @version 1.0.0.1
	 */
	public byte[] inputStreamToByte(InputStream inStream){  
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        try {
			while ((len = inStream.read(buffer)) != -1) {  
			    outSteam.write(buffer, 0, len);  
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			 try {
				outSteam.close();
				inStream.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}  
        return outSteam.toByteArray();  
    }
	
	/**
	 * @description: 根据byte数组，生成文件
	 * 
	 * @param bfile
	 * @param filePath 目的目录
	 * @param name 文件名
	 * @author Yangcl 
	 * @date 2017年7月13日 下午2:07:25 
	 * @version 1.0.0.1
	 */
	public void writeFile(byte[] bfile, String filePath,String name) {  
	    BufferedOutputStream bos = null;  
	    FileOutputStream fos = null;  
	    File file = null;  
	    try {  
	        File dir = new File(filePath);  
	        if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
	            dir.mkdirs();  
	        }  
	        file = new File(filePath + File.separator + name);  
	        fos = new FileOutputStream(file);  
	        bos = new BufferedOutputStream(fos);  
	        bos.write(bfile);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (bos != null) {  
	            try {  
	                bos.close();  
	            } catch (IOException ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
	        if (fos != null) {  
	            try {  
	                fos.close();  
	            } catch (IOException ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
	    }  
	}
	
	/**
	 * @description: 文件信息过滤 | 根据指定的路径和文件扩展名返回文件名称列表
	 *
	 * @param dir  文件夹名称："D:\\tgc\\controller\\coupon"
	 * @param suffix		文件后缀扩展名：".jpg"
	 * @author Yangcl
	 * @date 2019年1月23日 下午2:55:23 
	 * @version 1.0.0.1
	 */
	public List<String> fileNameList(String dir , String suffix) {
		List<String> list = new ArrayList<String>();
		File[] files = this.suffixFilter(dir, suffix);
		if(files != null && files.length != 0){
			for (File f : files) {
				list.add(f.getName());
			}
		}
		return list;
	}

	/**
	 * @description: 文件信息过滤 | 根据指定的路径和文件扩展名返回文件列表
	 *
	 * @param dir  文件夹名称："D:\\tgc\\controller\\coupon"
	 * @param suffix		文件后缀扩展名：".jpg"
	 * @author Yangcl
	 * @date 2019年1月23日 下午2:55:23 
	 * @version 1.0.0.1
	 */
	public File[] suffixFilter(String dir, final String suffix) {
		File file = new File(dir);
		if (!file.isDirectory()) {
			System.out.println("No directory provided");
			return null;
		}

		File[] files = file.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(suffix);
			}
		});
		return files;
	}
	
	/**
	 * @description: 遍历磁盘指定文件夹下的文件
	 * 	File file = new File("D:\\cm2");
	 * 	List<String> contentList = new ArrayList<String>(); 
	 * 	List<String> list = FileUtil.getInstance().showDir(file, 0 , contentList ,false);
	 *
	 * @param dir
	 * @param level
	 * @param first 是否只获取第一层目录和文件
	 * @author Yangcl
	 * @date 2019年1月23日 下午3:26:35 
	 * @version 1.0.0.1
	 */
	public List<String> showDir(File dir , int level , List<String> list , boolean first){
		if(level == 0 && dir.getName().trim().length() == 0){
			list.add(getLevel(level) + dir); // 列出盘符下所有文件
		}else{
			list.add(getLevel(level) + dir.getName());
		}
		level ++;
		File[] files = dir.listFiles();
		if(files != null){
			for(int x = 0 ; x < files.length ; x++){
				if(files[x].isDirectory()){
					if(first) {
						list.add(getLevel(level) + files[x].getName());	
					}else {
						showDir(files[x], level , list , false);
					}
				}else{ // 这里如果直接使用files[x]将打印路径+文件名。
					list.add(getLevel(level) + files[x].getName());
				}
			}
		}
		return list;
	}
	
	private String getLevel(int level){
		StringBuilder sb = new StringBuilder();
		sb.append("|--");
		for(int x = 0 ; x < level ; x ++){
			sb.insert(0, "|    ");
		}
		return sb.toString();
	}

}

























