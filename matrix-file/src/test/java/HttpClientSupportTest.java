import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.alibaba.fastjson.JSONObject;
import com.matrix.support.HttpClientSupport;

/**
 * @description: 为HttpClientSupport.java提供使用示例
 *		非加密方式请求第三方服务器接口，向对方发送一个二进制文本流，包含一个字符串
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月6日 下午4:46:31 
 * @version 1.0.0
 */
public class HttpClientSupportTest extends HttpClientSupport{
	private static char[] commonDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e','f' };
	
	@Override
	public InputStream streamInit() {
		String result = "{\"platform\":\"平台标识\",\"key\":\"平台身份识别码\"}";
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return inputStream; 
	}

	@Override
	public HttpPost postInit(String url , InputStream inputStream) {
		String md5 = hash(inputStream);
		HttpPost post = new HttpPost(url);
		try {
			inputStream.reset();
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			post.addHeader("Origin", "http://10.12.52.28:80");
			post.addHeader("binfile-md5", md5);
			post.addHeader("binfile-auth", "platform-name");
			post.addHeader("binfile-gzip", "false");
			post.addHeader("binfile-reqlen", String.valueOf(inputStream.available()));
			String filename = "platform-name_token_access_req_20161229135901126.json"; 
			builder.addBinaryBody("binFile", inputStream, ContentType.DEFAULT_BINARY, filename);// 文件
			builder.addTextBody("filename", filename, ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), "UTF-8"));
			post.setEntity(builder.build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return post;
	}

	private static String hash(InputStream in) {
		try {
			byte[] buffer = new byte[1024];
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int numRead = 0;
			while ((numRead = in.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			in.close();
			return toHexString(md5.digest());
		} catch (Exception e) {
			return "";
		}
	}
	
	
	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(commonDigit[(b[i] & 0xf0) >>> 4]);
			sb.append(commonDigit[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		JSONObject result = 	new HttpClientSupportTest().requestServerStream("http://10.12.52.41:8080/matrix-admin/file/api_file_remote_upload.do");
		System.out.println(result);  
	}
}



















