【pojo命名规则】
		com.matrix.pojo.entity 直接与之对应的是每一张数据库中的表
			McUserInfo
		
		com.matrix.pojo.dto 数据传输模型。请求中如果包含entity之外的字段则使用dto
			McUserInfoDto
		
		com.matrix.pojo.view 返回数据模型。多表联查返回的结果集通常是以一个联合视图的形式反馈给service层。
			McUserInfoView
			
		
		com.matrix.pojo.cache 返回的缓存数据模型。通常用key=json-string的情况下



【info-国际化文件顺序】
		info.matrix-core.1000.properties
		info.matrix-quartz.2000.properties
		info.matrix-cache.3000.properties
		info.matrix-manager.4000.properties
		info.matrix-file.5000.properties
		info.matrix-api.6000.properties
		info.matrix-api-launch.6100.properties
		----------------------------------------------------------------------------------------info.matrix-circuit-breaker.7000.properties------------------------项目废弃
		info.matrix-route.1080.properties
		info.matrix-rocket-mq.1090.properties
		info.matrix-mongo.1100.properties
		info.matrix-tenant.1110.properties
		
		info.matrix-example.99999.properties

		
		dubbo服务配置文件起始标识：
		info.dubbo-demo.9999.properties						|可以直接复用的项目，请根据具体业务修改【dubbo-demo.9999】关联具体的业务场景
		info.matrix-financial.9010.properties				|财务服务-Dubbo 				 
		info.matrix-decoration.9020.properties			|店铺装修服务-Dubbo 														
		info.matrix-record.9030.properties					|日志服务-Dubbo 					
		
		info.matrix-behavior.9040.properties   			|用户行为埋点服务
		info.matrix-member.9050.properties              |基础会员服务-Dubbo
		info.matrix-public.9060.properties                  |公共服务-Dubbo
		info.matrix-order.9070.properties                   |订单服务-Dubbo
		info.matrix-payment-service.9080.properties       |支付服务-Dubbo
		info.matrix-commodity.9090.properties					 |商品服务-Dubbo
		
		info.matrix-****.9100.properties
		info.matrix-****.9110.properties
		info.matrix-****.9120.properties
		info.matrix-****.9130.properties
		info.matrix-****.9140.properties
		
		
		
		web项目-配置文件起始标识：
		info.api-manager-web.8010.properties                           权限API网关
		info.matrix-manager-api.8020.properties						权限API网关-具体实现项目
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

		
		
核心数据库初始化脚本：
        链接：https://pan.baidu.com/s/1Y535pVUkbj36emQ4BfpNXg 

		
		
		
		
		
		
【TODO】dubbo之SPI解析 https://blog.csdn.net/qiangcai/article/details/77750541		
【TODO】完善熔断器
【TODO】	完善Dubbo控制台
		
		
		
alter table `mc_user_info` auto_increment= 2;
		
		
		
		
		
		
		
		
		
		
		
		
		