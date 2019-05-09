package com.matrix.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.matrix.base.BaseClass;


/**
 * @description: 与网路相关的工具集合
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月29日 下午3:09:07 
 * @version 1.0.0
 */
public class NetUtil extends BaseClass {
	
	private static Logger logger = Logger.getLogger(NetUtil.class);
	
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	
	
	/**
	 * get方法直接调用post方法
	 * 
	 * @param url
	 *            网络地址
	 * @return 返回网络数据
	 */
	public static String get(String url) {
		return post(url, null);
	}

	/**
	 * 设定post方法获取网络资源,如果参数为null,实际上设定为get方法
	 * 
	 * @param url
	 *            网络地址
	 * @param param
	 *            请求参数键值对
	 * @return 返回读取数据
	 */
	public static String post(String url, Map<String, String> param) {
		HttpURLConnection conn = null;
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			StringBuffer sb = null;
			if (param != null) {// 如果请求参数不为空
				sb = new StringBuffer();
				/*
				 * A URL connection can be used for input and/or output. Set the
				 * DoOutput flag to true if you intend to use the URL connection
				 * for output, false if not. The default is false.
				 */
				// 默认为false,post方法需要写入参数,设定true
				conn.setDoOutput(true);
				// 设定post方法,默认get
				conn.setRequestMethod("POST");
				// 获得输出流
				OutputStream out = conn.getOutputStream();
				// 对输出流封装成高级输出流
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
				// 将参数封装成键值对的形式
				for (Map.Entry<String, String> s : param.entrySet()) {
					sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
				}
				// 将参数通过输出流写入
				writer.write(sb.deleteCharAt(sb.toString().length() - 1).toString());
				writer.close();// 一定要关闭,不然可能出现参数不全的错误
				sb = null;
			}
			conn.connect();// 建立连接
			sb = new StringBuffer();
			// 获取连接状态码
			int recode = conn.getResponseCode();
			BufferedReader reader = null;
			if (recode == 200) {
				// Returns an input stream that reads from this open connection
				// 从连接中获取输入流
				InputStream in = conn.getInputStream();
				// 对输入流进行封装
				reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				String str = null;
				sb = new StringBuffer();
				// 从输入流中读取数据
				while ((str = reader.readLine()) != null) {
					sb.append(str).append(System.getProperty("line.separator"));
				}
				// 关闭输入流
				reader.close();
				if (sb.toString().length() == 0) {
					return null;
				}
				return sb.toString().substring(0, sb.toString().length() - System.getProperty("line.separator").length());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null)// 关闭连接
				conn.disconnect();
		}
		return null;
	}
	
	/**
	 * @descriptions get方式提交
	 *
	 * @param strUrl
	 * @param params
	 * @return
	 * @date 2016年10月3日 下午9:10:05
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	 public static String get(String strUrl, Map params) {
		 	String method = "GET";
		 	HttpURLConnection conn = null;
	        BufferedReader reader = null;
	        String rs = null;
	        try {
	            StringBuffer sb = new StringBuffer();
	            if(method==null || method.equals("GET")){
	                strUrl = strUrl+"?"+urlencode(params);
	            }
	            URL url = new URL(strUrl);
	            conn = (HttpURLConnection) url.openConnection();
	            if(method==null || method.equals("GET")){
	                conn.setRequestMethod("GET");
	            }else{
	                conn.setRequestMethod("POST");
	                conn.setDoOutput(true);
	            }
	            conn.setRequestProperty("User-agent", userAgent);
	            conn.setUseCaches(false);
	            conn.setConnectTimeout(30000);
	            conn.setReadTimeout(30000);
	            conn.setInstanceFollowRedirects(false);
	            conn.connect();
	            if (params!= null && method.equals("POST")) {
	                try {
	                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	                        out.writeBytes(urlencode(params));
	                } catch (Exception e) {
	                    // TODO: handle exception
	                }
	            }
	            InputStream is = conn.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	            String strRead = null;
	            while ((strRead = reader.readLine()) != null) {
	                sb.append(strRead);
	            }
	            rs = sb.toString();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	        return rs;
	    }
	 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    /**
     * @description: 发送消息内容
     * 
     * @author Yangcl
     * @date 2018年1月23日 下午3:31:30 
     * @version 1.0.0.1
     */
    public void sendMessage(String receiver , String title , String msg) {
        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.smtp.host" , "smtp." + this.getInfo(100090015) + this.getInfo(100090012) + "." 
        		+ this.getInfo(100090011)  + this.getInfo(100090013) ); 
        props.setProperty("mail.smtp.auth", "true"); 
        Session session = Session.getInstance(props);
        session.setDebug(false);  
        MimeMessage message = null;
        Transport transport = null;
        try {
        	String sm = this.getInfo(100090007) + "_" + this.getInfo(100090008) + "_" + this.getInfo(100090016) + this.getInfo(100090009) 
        											+ this.getInfo(100090015) + this.getInfo(100090012) + "." + this.getInfo(100090011)  + this.getInfo(100090013);
        	String pw = this.getInfo(100090014)  + this.getInfo(100090010);
        	message = this.createMessage(session , sm , receiver , title , msg);
        	transport = session.getTransport(); 
            transport.connect(sm , pw);
            transport.sendMessage(message , message.getAllRecipients());
		} catch (Exception ex) {
			ex.printStackTrace(); 
		}finally {
	        try {
				transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
    }

    
    private MimeMessage createMessage(Session session, String send, String receiver , String title , String msg){
        MimeMessage message = new MimeMessage(session);
        try {
			message.setFrom(new InternetAddress(send, "Power-matrix", "UTF-8"));
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver, "Master", "UTF-8"));
	        message.setSubject(title, "UTF-8");
	        message.setContent(msg , "text/html;charset=UTF-8");
	        message.setSentDate(new Date());
	        message.saveChanges();
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		} catch (MessagingException e) { 
			e.printStackTrace();
		}
        return message;
    }
    
    
    
	/**
	 * @description: 获取本机IP地址信息
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2016年9月29日 下午3:08:41 
	 * @version 1.0.0.1
	 */
	public static String getLocalIP() {
		String address = "";
		try {
			Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ine = null;
			while (allNetInterfaces.hasMoreElements() && address.equals("")) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (!netInterface.isVirtual()) {
					Enumeration<?> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements() && address.equals("")) {
						ine = (InetAddress) addresses.nextElement();
						if (ine != null && ine instanceof Inet4Address) {
							if (!ine.getHostAddress().equals("127.0.0.1") && !netInterface.isVirtual()) {
								address = ine.getHostAddress();
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address.trim();
	}

	/**
	 * @description: 判断url根域是否可以访问 |比如传入http://www.srnpr.com/a/b/c.html
	 * 							 只会判断http://www.srnpr.com是否可访问
	 * 
	 * @param url
	 * @return
	 * @author Yangcl 
	 * @date 2016年9月29日 下午3:09:35 
	 * @version 1.0.0.1
	 */
	public boolean checkUrlHost(String url) {
		// 判断如果没有带标记 则增加标记
		if (!StringUtils.containsIgnoreCase(url, "://")) {
			url = "http://" + url;
		}
		String sHttpString = StringUtils.substringBefore(url, "//");
		url = sHttpString + "//" + StringUtils.substringBefore(StringUtils.substringAfter(url, "//"), "/");
		return checkUrlStatus(url);
	}
 
	/**
	 * @description: 判断url是否可以访问
	 * 
	 * @param urlStr
	 * @return
	 * @author Yangcl 
	 * @date 2016年9月29日 下午3:11:42 
	 * @version 1.0.0.1
	 */
	private boolean checkUrlStatus(String urlStr) {
		boolean bFlag = false;
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return bFlag;
		}
		
		// 只检测一次
		while (counts < 1) {
			try {
				URL url = new URL(urlStr);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(2000);
				int state = con.getResponseCode();
				if (state == 200||state==404) {
					bFlag = true;
				} else {
					getLogger(logger).logWarn("connect to " + urlStr + " return status " + state);  
				}
				break;
			} catch (Exception ex) {
				counts++;
				ex.printStackTrace();
				continue;
			}
		}
		return bFlag;
	}

}
