import com.matrix.support.HttpClientSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.alibaba.fastjson.JSONObject;
import com.matrix.util.FileUtil;

/**
 * @description: 将本地文件上传到文件服务器示例
 *
 * @author Yangcl
 * @date 2018年11月23日 下午4:57:24 
 * @version 1.0.0.1
 */
public class HttpClientWordExample extends HttpClientSupport{
	
	
	@Override
	public InputStream streamInit() {
		File file = new File("D:\\数据库精深.docx");
		InputStream inputStream = new ByteArrayInputStream(FileUtil.getInstance().getFileBytes(file));
		return inputStream; 
	}

	@Override
	public HttpPost postInit(String url , InputStream inputStream) {
		HttpPost post = new HttpPost(url);
		try {
			inputStream.reset();
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			post.addHeader("Origin", "http");
			String filename = "数据库精深.docx"; 
			builder.addBinaryBody("binFile", inputStream, ContentType.DEFAULT_BINARY, filename);// 文件
			builder.addTextBody("filename", filename, ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), "UTF-8"));
			post.setEntity(builder.build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return post;
	}

	
	public static void main(String[] args) {
		JSONObject result = 	new HttpClientWordExample().requestServerStream("http://10.12.52.41:8080/matrix-admin/file/api_file_remote_upload.do");
		System.out.println(result);  
	}
}





















