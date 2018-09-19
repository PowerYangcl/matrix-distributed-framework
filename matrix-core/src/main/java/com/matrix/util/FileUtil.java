package com.matrix.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;

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
}
