package com.matrix.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.ICompanyPaymentInfoMapper;
import com.matrix.pojo.dto.CompanyPaymentInfoDto;
import com.matrix.pojo.entity.CompanyPaymentInfo;
import com.matrix.pojo.view.CompanyPaymentInfoView;
import com.matrix.service.ICompanyPaymentInfoService;

/** @description: 
 *
 * @author wanghao
 * @date 2018年11月21日 下午6:02:01 
 * @version 1.0.0.1
 */
@Service("companyPaymentInfoService")
public class CompanyPaymentInfoServiceImpl  extends BaseServiceImpl<Long, CompanyPaymentInfo, CompanyPaymentInfoDto, CompanyPaymentInfoView>  implements ICompanyPaymentInfoService{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Resource
	private ICompanyPaymentInfoMapper companyPaymentInfoMapper;
	
	/**
	 * @description: 
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月21日 下午6:07:52 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject addCompanyPaymentInfo(CompanyPaymentInfo e) {
		JSONObject json = new JSONObject();
		e.setCreateTime(new Date());
		e.setCreateUserId(e.getUserCache().getId());
		e.setCreateUserName(e.getUserCache().getUserName());
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		Integer result = companyPaymentInfoMapper.insertSelective(e);
		if(result > 0) {
			json.put("status", "success");
			json.put("msg", this.getInfo(400010022));	// 400010022=添加成功
			//添加缓存
			//launch.loadDictCache(DCacheEnum.CompanyPaymentInfo, "InitCompanyPaymentInfo").get(String.valueOf(e.getId()));
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010023));	// 400010023=添加失败
		}
		return json;
	}

	/**
	 * @description: 
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月21日 下午6:07:47 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject editCompanyPaymentInfo(CompanyPaymentInfo e) {
		JSONObject json = new JSONObject();
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		Integer result = companyPaymentInfoMapper.updateSelective(e);
		if(result > 0) {
			json.put("status", "success");
			json.put("msg", this.getInfo(400010024));	// 400010024=更新成功
			//删除缓存
			//launch.loadDictCache(DCacheEnum.CompanyPaymentInfo, "InitCompanyPaymentInfo").del(String.valueOf(e.getId()));
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010025));  // 400010025=更新失败
		}
		return json;
	}

	/**
	 * @description: 
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月21日 下午6:07:30 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject delCompanyPaymentInfo(CompanyPaymentInfo e) {
		JSONObject json = new JSONObject();
		e.setDeleteFlag(0);//标记为删除状态
		Integer result = companyPaymentInfoMapper.updateSelective(e);
		if(result > 0) {
			//删除缓存
			//launch.loadDictCache(DCacheEnum.CompanyPaymentInfo, "InitCompanyPaymentInfo").del(String.valueOf(e.getId()));
			json.put("status", "success");
			json.put("msg", this.getInfo(400010001)); // 400010001=删除成功
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010002));  // 400010002=删除失败
		}
		return json;
	}

	/**
	 * @description: 
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月21日 下午6:07:40 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject getCompanyPaymentInfo(CompanyPaymentInfoDto dto) {
		JSONObject json = new JSONObject();
//		String entityJson = launch.loadDictCache(DCacheEnum.CompanyPaymentInfo, "InitCompanyPaymentInfo").get(String.valueOf(dto.getId()));
//		if(StringUtils.isNoneBlank(entityJson)) {
//			CompanyPaymentInfoView view = JSONObject.parseObject(entityJson, CompanyPaymentInfoView.class);
//			json.put("status", "success");
//			json.put("data", view);
//			json.put("msg", this.getInfo(400010014));		// 400010014=查询成功
//		}else {
//			json.put("status", "error");
//			json.put("msg", this.getInfo(400010034));		// 400010034=后台数据查询失败
//		}
		return json;
	}
	
	
}
