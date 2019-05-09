package com.matrix.service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.pojo.entity.SysTableOpreateRecord;

public interface ISysOpreateService {

	/**
	 * @description: 系统业务对应的添加 修改 删除操作|防止重复,dubbo retry等
	 *					添加操作：SysTableOpreateRecord(Long cid, String mdvalue, null , String tableName)  第三个参数非必填，
	 *										添加完成后获取主键id，同时调用******方法更新sys_table_opreate_record表的记录
	 *					修改操作：SysTableOpreateRecord(Long cid, String mdvalue, Long zid, String tableName)，所有参数必填
	 *										修改操作调用此方法会一次性返回是否存在同时更新数据库中的记录
	 *
	 * @author Yangcl
	 * @date 2018年11月29日 下午5:35:05 
	 * @version 1.0.0.1
	 */
	public JSONObject findOpreateMd5(SysTableOpreateRecord e);
	
	/**
	 * @description: 系统添加操作完成后，需要将主键id更新到sys_table_opreate_record表
	 *						SysTableOpreateRecord(Long cid, String mdvalue, Long zid, String tableName)，所有参数必填
	 *
	 * @author Yangcl
	 * @date 2018年11月29日 下午5:53:02 
	 * @version 1.0.0.1
	 */
	public JSONObject updateOpreateMd5(SysTableOpreateRecord e);
	
	public JSONObject delOpreateMd5(SysTableOpreateRecord e);
	
}
