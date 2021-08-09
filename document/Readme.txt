【pojo命名规则】




【info-国际化文件顺序】
		info.matrix-core.1000.properties
		info.matrix-permissions.1010.properties
		info.matrix-manager-api.1020.properties
		info.matrix-web-adapter.1030.properties
		info.matrix-websocket.1040.properties
		
		info.matrix-quartz.2000.properties
		info.matrix-cache.3000.properties
		info.matrix-file.5000.properties
		info.matrix-api.6000.properties
		info.matrix-route.1080.properties		【准备重构】
		
		
		
		dubbo服务配置文件起始标识：
		#info.dubbo-demo.9999.properties						|可以直接复用的项目，请根据具体业务修改【dubbo-demo.9999】关联具体的业务场景
		#info.matrix-financial.9010.properties				|财务服务-Dubbo 				 
		#info.matrix-decoration.9020.properties			|店铺装修服务-Dubbo 														
		#info.matrix-record.9030.properties					|日志服务-Dubbo 					
		
		#info.matrix-behavior.9040.properties   			|用户行为埋点服务
		#info.matrix-member.9050.properties              |基础会员服务-Dubbo
		#info.matrix-public.9060.properties                  |公共服务-Dubbo
		#info.matrix-order.9070.properties                   |订单服务-Dubbo
		#info.matrix-payment-service.9080.properties       |支付服务-Dubbo
		#info.matrix-commodity.9090.properties					 |商品服务-Dubbo
		
		#info.matrix-****.9100.properties
		#info.matrix-****.9110.properties
		#info.matrix-****.9120.properties
		#info.matrix-****.9130.properties
		#info.matrix-****.9140.properties
		
		
		
		web项目-配置文件起始标识：
		info.api-manager-web.8010.properties                           权限API网关
		
		info.api-commodity-web.8030.properties 						商品API网关
		info.api-order-web.8040.properties									订单API网关
		info.api-member-web.8050.properties								会员API网关
		info.api-behavior-web.8060.properties							埋点API网关
		info.api-payment-web.8070.properties							支付API网关 - 项目暂停
		info.api-financial-web.8080.properties							财务API网关
		info.api-public-web.8090.properties                                公共服务+字典API网关
		info.api-decoration-web.8100.properties						店铺装修API网关
		info.api-client-web.8110.properties									C端接口API网关
		
		JSP后台管理系统-配置文件起始标识：
		info.web-file.7000.properties												文件服务器|此配置文件并未放置到项目中
		info.matrix-omo.7010.properties										OMO运营管理后台
		info.matrix-mq.7020.properties										消息队列项目
		
		
		

【归档备注】
		HttpClientSupportTest.java					HttpClientSupport工具类的测试类
		api file remote upload								系统内部使用的高权限接口
		api file remote inject								系统内部使用的高权限接口|等待完善

		
		
		
		
alter table `mc_user_info` auto_increment= 2;
		
		
		
		
		
		
		
		
		
		
		
		
		
		