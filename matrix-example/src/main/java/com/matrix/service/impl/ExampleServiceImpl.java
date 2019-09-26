package com.matrix.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseMqProducer;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.BaseTransactionMqProducer;
import com.matrix.dao.IUserDemoMapper;
import com.matrix.pojo.dto.ApiExampleDto;
import com.matrix.pojo.dto.UserDemoDto;
import com.matrix.pojo.entity.UserDemo;
import com.matrix.pojo.view.UserDemoView;
import com.matrix.rocket.GroupDefaultTestRocket;
import com.matrix.rocket.GroupDefaultTestRocket;
import com.matrix.service.IExampleService;
import com.matrix.util.SignUtil;

@Service("exampleService")
public class ExampleServiceImpl  extends BaseServiceImpl<Long , UserDemo, UserDemoDto , UserDemoView> implements IExampleService {
	
	GenericService genericService;
	
	@Resource
	private IUserDemoMapper userDemoMapper;

	public JSONObject addInfo(UserDemo entity, HttpSession session) {
		JSONObject result = new JSONObject();
		String pass = SignUtil.md5Sign(entity.getPassword());
		entity.setPassword(pass); 
		entity.setCreateTime(new Date());
		Integer count = userDemoMapper.insertSelective(entity);
		if(count == 1){
			result.put("status", true);
			result.put("msg", "数据插入成功！");
		}else{
			result.put("status", false);
			result.put("msg", "数据插入异常！");
		}
		return result;
	}

	public JSONObject deleteOne(UserDemo entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getId().toString())){
			Integer count = userDemoMapper.deleteById(entity.getId());
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "删除成功");
			}else{
				result.put("status", "error");
				result.put("msg", "删除失败");
			}
		}else{
			result.put("status", "error");
			result.put("msg", "删除记录Id不可为空");
		}
		return result;
	}

	
	/**
	 * @description: 针对UE，采用自定义的上传图片方式|此种方式使用cfile接口将图片上传到图片服务器
	 * 
	 * @param type uploadimage:上传图片|uploadfile:上传文件
	 * @param request
	 * @param response
	 * @return
	 * @author 付强 
	 * @date 2017年6月8日 下午3:21:48 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxUploadFileCfile(String type , HttpServletRequest request) {
		if(type.equals("uploadimage")){
			return null;   
		}else{
			return null;  
		}
	}

	/**
	 * @description: http client 请求测试。HttpClientSupportTest.java将会调用此接口。api_http_client_test接口将会从
	 * 		请求的request对象中取出二进制文本中的字符串信息
	 *
	 * @param request
	 * @author Yangcl
	 * @date 2017年11月6日 下午5:09:38 
	 * @version 1.0.0
	 */
	@Override
	public JSONObject apiHttpClientTest(HttpServletRequest request) {
		JSONObject o = new JSONObject();
		o.put("status", "success");
		o.put("msg", "成功！");
		
		List<FileItem> list = this.getFileFromRequest(request); 
		FileItem e = list.get(0);
		byte[] bs = e.get(); 
		
		String string = "";
		try {
			string = new String(bs,"UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		System.out.println("string = " + string);  // string = {"platform":"平台标识","key":"平台身份识别码"}
		return o;  
	}
	
	private List<FileItem> getFileFromRequest(HttpServletRequest request) {
		List<FileItem> items = null;   // 得到所有的文件
		String contentType = request.getContentType();
		if (StringUtils.contains(contentType , "multipart/form-data")) {  // 如果文件是以二进制方式上传的
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			} 
		}
		return items;
	}

	/**
	 * @description: com.matrix.processor.privates.ExamplePrivateProcessor.java对应测试方法
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2017年12月25日 下午8:47:53 
	 * @version 1.0.0.1
	 */
	public JSONObject apiProcessorTest(ApiExampleDto dto) {
		JSONObject r = new JSONObject();
		List<UserDemo> list = userDemoMapper.findEntityByApiDto(dto);
		if(list != null && list.size() != 0) { 
			r.put("status", "success");
			r.put("msg", "测试成功!");
			r.put("data", list);
		}else {
			r.put("status", "error");
			r.put("msg", "未找到对应的值");
		}
		return r;
	}

	/**
	 * @description: 模拟RocketMq发送消息请求
	 *
	 * @param request
	 * @author Yangcl
	 * @date 2019年6月13日 下午5:13:12 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRocketmqProducerInit(HttpServletRequest request) {
		JSONObject result= new JSONObject();
		
		// 顺序消息使用示例
		Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dateStr = sdf.format(date);
    	BaseMqProducer baseMqProducer = GroupDefaultTestRocket.getInstance().getBaseMqProducer();
    	
    	try {
    		for(int i = 1 ; i <= 5 ; i ++) {
    			String body = dateStr + " 订单编号-1-" + i;
    			Message msg = baseMqProducer.initMqMessage("TopicOrder", "TagOrder", body);
    			
    			baseMqProducer.getDefaultMQProducer().send(
    					msg, 
    					new MessageQueueSelector() { 
    						@Override
    						public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
    							Integer id  = (Integer) arg;
    							return mqs.get(id); 
    						}
    					}, 
    					0, 				// 队列的下标
    					60_000		// 超时时间
    					);
    			System.out.println("Producer -----> body：" + body);
    		}
    		
    		for(int i = 1 ; i <= 5 ; i ++) {
    			String body = dateStr + " 订单编号-2-" + i;
    			Message msg = baseMqProducer.initMqMessage("TopicOrder", "TagOrder", body);
    			
    			baseMqProducer.getDefaultMQProducer().send(
    					msg, 
    					new MessageQueueSelector() { 
    						@Override
    						public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
    							Integer id  = (Integer) arg;
    							return mqs.get(id); 
    						}
    					}, 
    					1, 				// 队列的下标
    					60_000		// 超时时间
    					);
    			System.out.println("Producer -----> body：" + body);
    		}
    		
    		for(int i = 1 ; i <= 5 ; i ++) {
    			String body = dateStr + " 订单编号-3-" + i;
    			Message msg = baseMqProducer.initMqMessage("TopicOrder", "TagOrder", body);
    			
    			baseMqProducer.getDefaultMQProducer().send(
    					msg, 
    					new MessageQueueSelector() { 
    						@Override
    						public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
    							Integer id  = (Integer) arg;
    							return mqs.get(id); 
    						}
    					}, 
    					2, 				// 队列的下标
    					60_000		// 超时时间
    					);
    			System.out.println("Producer -----> body：" + body);
    		}
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// 事物消息使用示例
		/*BaseTransactionMqProducer transProducer = GroupDefaultTestRocket.getInstance().getTransProducer();
		String body = "Hello-Transaction-RocketMq-";
		String[] args = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
		for(int i = 1 ; i < args.length + 1 ; i ++) {
			Message msg = transProducer.initMqMessage(
					"TopicOrder",				 Topic 
					"TagOrder", 					 Tag   
					body + i);
			msg.putUserProperty("up", "user-property-" + i);
			
			JSONObject obj = GroupTransTestSupport.getInstance().getTransProducer().sendMsg(msg, args[i-1]);
			
			System.err.println(args[i-1] + " Producer SendResult " + msg.getUserProperty("up") 
								+ " SendStatus = " + obj.getJSONObject("data").getString("sendStatus")); 
		}*/
		return result;
	}


}



















