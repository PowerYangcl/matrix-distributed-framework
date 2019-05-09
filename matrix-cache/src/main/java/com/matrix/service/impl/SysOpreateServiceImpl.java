package com.matrix.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.ISysTableOpreateRecordMapper;
import com.matrix.pojo.entity.SysTableOpreateRecord;
import com.matrix.service.ISysOpreateService;

/**
 * @deprecated 
 * 	废弃的类，存在严重的bug，不建议使用，后续将会删除。	
 * 
 * 
 * @description: 系统操作提供者|签名请使用com.matrix.util.SignUtil.java进行统一
 * 		操作|这种方法不靠谱，建议使用者反查数据库来验证字段是否重复
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年11月29日 下午3:36:49 
 * @version 1.0.0.1
 */
@Service("sysOpreateService")
public class SysOpreateServiceImpl extends BaseClass implements ISysOpreateService {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private ISysTableOpreateRecordMapper sysTableOpreateRecordMapper;

	/**
	 * @description: 系统业务对应的添加 修改 删除操作|防止重复,dubbo retry等|如果验证的是公共库中的数据，那么约定：cid=0
	 *					添加操作：SysTableOpreateRecord(Long cid, String mdvalue, null , String tableName)  第三个参数非必填，
	 *										添加完成后获取主键id，同时调用******方法更新sys_table_opreate_record表的记录
	 *					修改操作：SysTableOpreateRecord(Long cid, String mdvalue, Long zid, String tableName)，所有参数必填
	 *										修改操作调用此方法会一次性返回是否存在同时更新数据库中的记录
	 *
	 * @author Yangcl
	 * @date 2018年11月29日 下午5:35:05 
	 * @version 1.0.0.1
	 */
	public JSONObject findOpreateMd5(SysTableOpreateRecord e) {
		JSONObject result = new JSONObject();
		if(e.getCid() == null || StringUtils.isBlank(e.getMdvalue())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(300010106));	 // 300010106=关键字段cid和mdvalue不得为空
			return result;
		}
		
		try {
			String value = launch.loadDictCache(DCacheEnum.TableOpreate , "InitTableOpreate").get(e.getMdvalue() + "@" + e.getCid() + "@" + e.getTableName());
			if(StringUtils.isNotBlank(value) && !value.equals("error")) {  // 如果value=error则在数据库中新建一条记录
				result.put("status", "error");
				if(value.equals("exception")) {
					result.put("flag", value);  // exception：程序异常 | error：zid为空
					result.put("msg", this.getInfo(200010023));	 // 200010023=系统查询异常
				}else {
					result.put("flag", "id"); 
					result.put("msg", this.getInfo(300010105));	 // 300010105=数据已经存在，请勿重复操作
					result.put("id", value);  // 如果存在则返回当前记录的id
				}
				return result;
			}
			
			e.setUpdateTime(new Date());																											// TODO 有问题
			e.setUpdateUserId(e.getUserCache().getId());
			e.setUpdateUserName(e.getUserCache().getUserName());
			if(e.getZid() != null && StringUtils.isNotBlank(e.getTableName())) {  // 修改时会传入当前操作记录的主键id
				sysTableOpreateRecordMapper.updateSelective(e);  // cid zid table_name 定位一条记录，同时更新mdvalue
			}else {
				e.setCreateTime(new Date());
				e.setCreateUserId(e.getUserCache().getId());
				e.setCreateUserName(e.getUserCache().getUserName());
				sysTableOpreateRecordMapper.insertSelective(e);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		result.put("status", "success");
		result.put("msg", this.getInfo(300010107));			// 300010107=数据不存在，可以操作
		return result;
	}
	
	/**
	 * @description: 系统添加操作完成后，需要将主键id更新到sys_table_opreate_record表
	 *						SysTableOpreateRecord(Long cid, String mdvalue, Long zid, String tableName)，所有参数必填
	 *
	 * @author Yangcl
	 * @date 2018年11月29日 下午5:53:02 
	 * @version 1.0.0.1
	 */
	public JSONObject updateOpreateMd5(SysTableOpreateRecord e) {
		JSONObject result = new JSONObject();
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		int flag = 0;
		if(e.getCid() != null && e.getZid() != null && StringUtils.isNotBlank(e.getTableName()) && StringUtils.isNotBlank(e.getMdvalue()) ) {
			flag = sysTableOpreateRecordMapper.updateZid(e);
		}
		
		if(flag == 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(300010108));	 // 300010108=数据更新失败
			return result;
		}
		result.put("status", "success");
		result.put("msg", this.getInfo(300010109));			// 300010109=数据更新成功
		return result;
	}
	
	/**
	 * @description: 系统中的删除行为完成后，需要删除对饮的锁记录。													// TODO 有问题
	 *								必传参数：mdvalue，md5加密后的字符串
	 *													cid
	 * @author Yangcl
	 * @date 2018年11月29日 下午6:04:22 
	 * @version 1.0.0.1
	 */
	public JSONObject delOpreateMd5(SysTableOpreateRecord e) {
		JSONObject result = new JSONObject();
		int flag = sysTableOpreateRecordMapper.deleteByMdvalue(e);
		launch.loadDictCache(DCacheEnum.TableOpreate , null).del(e.getMdvalue() + "@" + e.getCid()); 
		
		if(flag == 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(300010108));	 // 300010108=数据更新失败
			return result;
		}
		result.put("status", "success");
		result.put("msg", this.getInfo(300010109));			// 300010109=数据更新成功
		return result;
	}

	
	
	
	
}











































