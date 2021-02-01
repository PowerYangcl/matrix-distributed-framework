package com.matrix.cache.enums;

/**
 * @descriptions 数据字典相关枚举
 * 	dict cache enum
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年12月11日 下午4:47:31
 * @version 1.0.1
 */
public enum DCacheEnum {
	
	redisLock, // 分布式锁
	
	zkLockRoot,        // zookeeper锁基础路径
	
	Increment,        // 通用自增前缀。例如自增+1、自增+2等等
	
	McSysFunc,     // mc_sys_function 表的缓存标识
	
	McRole,        // 角色信息
	
	McUserRole,      // 用户与角色关联
	
	UserInfoNp ,        // 用户登陆信息  暂时没有使用 name + password 为key
	
	UserInfoId ,        // 保存UserInfoNp对应key后缀，方便查找和删除；例如：kye:xd-UserInfoId-999|value:userName+","+password
	
	ApiRequester , // api模块的请求公钥信息标识。
	
	AccessToken   ,         // 客户端令牌 对应手机端的用户缓存信息的key，且client类型为0:IOS   1:Android   2:微信
	
	ApiProject  ,        // apicenter.ac_api_project表的字典缓存，用于标识API的分类
	
	ApiDomain  ,       //  ac_include_domain 表字典缓存，白名单标识
	
	ApiInfo,               // ac_api_info 与ac_include_domain 的复合缓存。
	
	SysJob,				// 系统定时任务
	
	SysJobGroup, 	// 系统定时任务分组
	
//	SysJobHost ,     // sys_job_host表缓存对象
	
	CompanyInfo, // 公司信息表缓存对象

    StoreInfo,  // 门店信息表缓存对象

    ValidateCode,// 验证码缓存对象

    TableOpreate, // 验证业务数据对表的操作是否重复

    DictAreaInfo,//区域信息缓存对象

	DictLabelType, //标签类型信息表缓存

	DictActivityType, //活动类型信息表缓存
	
	MsgId, // RocketMq消息队列
	
	McOrganization, // 组织结构
	
	TcShopInfo , // 店铺信息
}




































