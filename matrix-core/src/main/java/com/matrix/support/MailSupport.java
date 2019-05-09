package com.matrix.support;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.matrix.base.BaseClass;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @description: 发送邮件支持类 
 *  	JavaMail 版本: 1.6.0
 *  	JDK 版本: JDK 1.7 以上（必须）
 *  	更多内容请参考：http://blog.csdn.net/xietansheng/article/details/51673073
 *  	http://blog.csdn.net/xietansheng/article/details/51722660
 *  
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年1月23日 下午2:11:19 
 * @version 1.0.0
 */
public class MailSupport extends BaseClass{
	private MailSupport() {
	}
	private static class LazyHolder {
		private static final MailSupport INSTANCE = new MailSupport();
	}
	public static final MailSupport getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
    /**
     * @description: 发送纯文本邮件到指定的邮箱
     *
     * @param receiveMail 收件人
     * @param title 邮件标题
     * @param msg 邮件内容
     * @author Yangcl
     * @date 2018年1月23日 下午3:08:52 
     * @version 1.0.0
     */
    public void sendSimpleEmail(String receiveMail , String title , String msg) {
        Properties props = new Properties(); // 参数配置
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host" , this.getConfig("matrix-core.mail_smtp")); // 发件人的邮箱的 SMTP 服务器地址	 
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
        Session session = Session.getInstance(props);
        session.setDebug(false);   // 设置为debug模式, 可以查看详细的发送 log
        MimeMessage message = null;
        Transport transport = null;
        try {
        	String sendMail = this.getConfig("matrix-core.mail_service");
        	String password = this.getConfig("matrix-core.mail_password");
        	message = this.createSimpleMessage(session , sendMail , receiveMail , title , msg);
        	transport = session.getTransport(); 
            transport.connect(sendMail , password);
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
    
	
	
	/**
	 * @description: 创建一封只包含文本的简单邮件
	 *
	 * @param sendMail 发件人邮箱 ***********@126.com
	 * @param receiveMail 收件人邮箱 ***********@126.com
	 * @param title 邮件标题
	 * @param msg 文本消息
	 * 
	 * @author Yangcl
	 * @date 2018年1月23日 下午2:39:48 
	 * @version 1.0.0.1
	 */
    private MimeMessage createSimpleMessage(Session session, String sendMail, String receiveMail , String title , String msg){
        MimeMessage message = new MimeMessage(session);
        try {
			message.setFrom(new InternetAddress(sendMail, "Power-matrix", "UTF-8"));
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "Master", "UTF-8"));
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
}























