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
		info.matrix-circuit-breaker.7000.properties

		info.matrix-example.99999.properties

		
		dubbo服务配置文件起始标识：
		info.mip-member.9010.properties
		info.mip-****-service.9020.properties
		info.mip-****-service.9030.properties
		info.mip-****-service.9040.properties
		info.mip-****-service.9050.properties
		info.mip-****-service.9060.properties
		info.mip-****-service.9070.properties
		info.mip-****-service.9080.properties
		info.mip-****-service.9090.properties
		info.mip-****-service.9100.properties
		
		

【归档备注】
		HttpClientSupportTest.java					HttpClientSupport工具类的测试类
		api file remote upload								系统内部使用的高权限接口
		api file remote inject								系统内部使用的高权限接口|等待完善

		
		
		
		
		
		
		
		
		
【TODO】接口模拟请求测试弹窗		
【TODO】系统开放接口 - api信息树 此模块需要重新设计		
【TODO】dubbo之SPI解析 https://blog.csdn.net/qiangcai/article/details/77750541		
【TODO】完善熔断器
【TODO】	完善Dubbo控制台
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		