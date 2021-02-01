package com.matrix.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
 
 
/**
 * @description: 验证码支持类
 *
 * @author wanghao
 * @date 2018年11月27日 下午12:40:14 
 * @version 1.0.0.1
 */
public class ValidateCodeSupport{
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	private int width = 90;//验证码宽度 默认值：90
	private int height = 40;//验证码高度 默认值：40
	private int codeCount = 4;//验证码个数  默认值：4
	private int lineCount = 19;//混淆线个数  默认值：19 
	private int  fontSize = 20;//字体大小像素  
	
	public ValidateCodeSupport(){}
	/**
	 * @description: 
	 *
	 * @param width 验证码宽度
	 * @param height 验证码高度
	 * @param fontSize 字体大小像素 
	 * @author wanghao
	 * @date 2018年11月27日 下午12:40:30 
	 * @version 1.0.0.1
	 */
	public ValidateCodeSupport(int width,int height,int fontSize){
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
	}
	/**
	 * @description: 
	 *
	 * @param width 验证码宽度
	 * @param height 验证码高度
	 * @param codeCount 验证码个数
	 * @param fontSize 字体大小像素
	 * @author wanghao
	 * @date 2018年11月27日 下午12:40:52 
	 * @version 1.0.0.1
	 */
	public ValidateCodeSupport(int width,int height,int codeCount,int fontSize){
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.fontSize = fontSize;
	}
	
	/**
	 * @description: 
	 *
	 * @param width 验证码宽度
	 * @param height 验证码高度
	 * @param codeCount 验证码个数
	 * @param lineCount 混淆线个数
	 * @param fontSize 字体大小像素
	 * @author wanghao
	 * @date 2018年11月27日 下午12:41:22 
	 * @version 1.0.0.1
	 */
	public ValidateCodeSupport(int width,int height,int codeCount,int lineCount,int fontSize){
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.lineCount = lineCount;
		this.fontSize = fontSize;
	}
 
 
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
 
   /**
    * @description: 生成一个验证码，有效期10分钟
    *  返回内容 
	   {
		"status": "success",
		"data": {
			"key": "uuid字符串",
			"img": "图片base64字符串"
		},
		"msg": "ok"
	   }
    * @return 
    * @author wanghao
    * @date 2018年11月27日 下午12:35:58 
    * @version 1.0.0.1
    */
	public JSONObject createValidateCode() {
		// 定义随机数类
		Random r = new Random();
		// 定义存储验证码的类
		StringBuilder builderCode = new StringBuilder();
		// 定义画布
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 得到画笔
		Graphics g = buffImg.getGraphics();
		// 1.设置颜色,画边框
		g.setColor(Color.gray);
		g.drawRect(0, 0, width, height);
		// 2.设置颜色,填充内部
		g.setColor(Color.white);
		g.fillRect(1, 1, width - 2, height - 2);
		// 3.设置干扰线
		// g.setColor(Color.gray);
		for (int i = 0; i < lineCount; i++) {
			int _R = (int) Math.floor(Math.random() * 256);
			int _G = (int) Math.floor(Math.random() * 256);
			int _B = (int) Math.floor(Math.random() * 256);
			g.setColor(new Color(_R, _G, _B, 255));
			g.drawLine(r.nextInt(width), r.nextInt(width), r.nextInt(width), r.nextInt(width));
		}
		// 4.设置验证码
		g.setColor(Color.blue);
		// 4.1设置验证码字体
		g.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, fontSize));
		for (int i = 0; i < codeCount; i++) {
			char c = codeSequence[r.nextInt(codeSequence.length)];
			builderCode.append(c);
			g.drawString(c + "", ((width / codeCount) * i + 2), height * 4 / 5);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(buffImg, "png", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] b = out.toByteArray();
		String base64Str = Base64.encodeBase64String(b);
		JSONObject result = new JSONObject();
		String key = UUID.randomUUID().toString();
		launch.loadDictCache(DCacheEnum.ValidateCode, "").set(key, builderCode.toString(), 10 * 60);//10分钟过期
		JSONObject data = new JSONObject();
		data.put("key", key);
		data.put("img", "data:image/JPEG;base64," + base64Str);
		result.put("data", data);
		result.put("status", "success");
		result.put("msg", "ok"); 
		return result;
	}
	/**
	 * @description: 根据生成验证码返回的key查询对应的验证码值，该方法只能查询 生成验证码10分钟之内的码值
	 *
	 * @param id
	 * @return 
	 * @author wanghao
	 * @date 2018年11月27日 下午12:34:04 
	 * @version 1.0.0.1
	 */
	public static String getCode(String key) {
		ValidateCodeSupport support = new ValidateCodeSupport();
		return support.launch.loadDictCache(DCacheEnum.ValidateCode, "").get(key);
	}
}


