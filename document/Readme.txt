【pojo命名规则】
		com.matrix.pojo.entity 直接与之对应的是每一张数据库中的表
			McUserInfo
		
		com.matrix.pojo.dto 数据传输模型。请求中如果包含entity之外的字段则使用dto
			McUserInfoDto
		
		com.matrix.pojo.view 返回数据模型。多表联查返回的结果集通常是以一个联合视图的形式反馈给service层。
			McUserInfoView
			
		com.matrix.pojo.input api相关内容中的输入参数描述
			McUserInfoInput
		
		com.matrix.pojo.result api相关内容中的输出参数描述
			McUserInfoResult
		
		com.matrix.pojo.cache 返回的缓存数据模型。通常用key=json-string的情况下



【info-国际化文件顺序】
		info.matrix-core.1000.properties
		info.matrix-quartz.2000.properties
		info.matrix-cache.3000.properties
		info.matrix-manager.4000.properties
		info.matrix-file.5000.properties
		info.matrix-api.6000.properties
		info.matrix-api-launch.6100.properties
		info.matrix-circuit-breaker.7000.properties
		info.matrix-route.1080.properties
		info.matrix-rocket-mq.1090.properties
		info.matrix-mongo.1100.properties
		
		info.matrix-example.99999.properties

		
		dubbo服务配置文件起始标识：
		info.dubbo-demo.9999.properties						|可以直接复用的项目，请根据具体业务修改【dubbo-demo.9999】关联具体的业务场景
		info.mip-member.9010.properties					|会员信息服务 												- 此项目会被废弃 - 20190504 - Yangcl
		info.dict-info.9020.properties				    		|字典服务 														- 此项目会被废弃 - 20190504 - Yangcl
		info.mip-common.9030.properties					|短信和邮件服务 											- 此项目会被废弃 - 20190504 - Yangcl
		info.user-behavior-service.9040.properties   |用户行为埋点服务
		info.matrix-member.9050.properties              |基础会员服务-Dubbo
		info.matrix-public.9060.properties                  |公共服务-Dubbo
		info.matrix-coupon.9070.properties                |优惠券服务-Dubbo
		info.mip-****-service.9080.properties
		info.mip-****-service.9090.properties
		info.mip-****-service.9100.properties
		
		
		
		web项目配置文件起始标识：
		info.mip-web.8010.properties                                                                - 此项目会被废弃 - 20190504 - Yangcl
		info.matrix-manager-api.8020.properties
		info.mop-web-api.8030.properties                                                        - 此项目会被废弃 - 20190504 - Yangcl
		info.monitor-web.8040.properties
		info.member-web.8050.properties
		info.coupon-web.8060.properties
		

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
		
		
		
		
		
		
		
		
		
		
		
		
		