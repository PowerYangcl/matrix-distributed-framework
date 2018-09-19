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
	McSysFunc,     // mc_sys_function 表的缓存标识
	
	McRole,        // 角色信息
	
	McUserRole,      // 用户与角色关联
	
	UserInfoNp ,        // 用户登陆信息  暂时没有使用 name + password 为key
	
	ApiRequester , // api模块的请求公钥信息标识。
	
	AccessToken   ,         // 客户端令牌 对应手机端的用户缓存信息的key，且client类型为0:IOS   1:Android   2:微信
	
	ApiProject  ,        // apicenter.ac_api_project表的字典缓存，用于标识API的分类
	
	ApiDomain  ,       //  ac_include_domain 表字典缓存，白名单标识
	
	ApiInfo,               // ac_api_info 与ac_include_domain 的复合缓存。
	
	SysJob,				// 系统定时任务
	
	SysJobGroup, 	// 系统定时任务分组
	
	SysJobHost ,     // sys_job_host表缓存对象
	
	
	
	
}




































