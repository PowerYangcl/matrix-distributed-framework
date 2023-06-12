package com.matrix.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.matrix.base.BaseController;


/**
 * @description: 灰度测试
 * 
 * @author Yangcl
 * @date 2023-6-9 11:43:01
 * @home https://github.com/PowerYangcl
 * @path matrix-jsp-demo / com.matrix.controller.HdTestController.java
 * @version v-matrix-hd-test
 */
@Controller
@RequestMapping("hd")
public class HdTestController extends BaseController{

	/**
	 * @description: 根据灰度版本不同，返回A/B两张图片
	 * 		http://localhost:8080/matrix-jsp-demo/hd/api_hd_request.do
	 * 
	 * @author Yangcl
	 * @date 2023-6-9 11:49:19
	 * @home https://github.com/PowerYangcl
	 * @version v-matrix-hd-test
	 */
    @RequestMapping(value = "api_hd_request", method = RequestMethod.GET, produces = "image/jpeg")
    public void apiHdRequest(HttpServletResponse response) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("images/B.png");			// 模拟版本切换
        InputStream  inputStream = classPathResource.getInputStream();
        //将InputStream 转 File
        File file = asFile(inputStream);
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        response.setHeader("Content-Type", "application/octet-stream");
    }

    /**
     * InputStream To File
     * @param in
     * @return
     * @throws IOException
     */
    public static File asFile(InputStream in) throws IOException {
        File tempFile = File.createTempFile("test", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);
        return tempFile;
    }
}































