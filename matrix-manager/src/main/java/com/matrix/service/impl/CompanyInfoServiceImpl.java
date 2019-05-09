package com.matrix.service.impl;

import java.util.Collections;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.ICompanyAgeGroupMapper;
import com.matrix.dao.ICompanyInfoMapper;
import com.matrix.dao.ICompanyMemberDayMapper;
import com.matrix.pojo.dto.CompanyInfoDto;
import com.matrix.pojo.entity.CompanyAgeGroup;
import com.matrix.pojo.entity.CompanyInfo;
import com.matrix.pojo.entity.CompanyMemberDay;
import com.matrix.pojo.view.CompanyInfoView;
import com.matrix.service.ICompanyInfoService;

/** @description: 
 *
 * @author wanghao
 * @date 2018年11月5日 下午2:30:02 
 * @version 1.0.0.1
 */
@Service("companyInfoService") 
public class CompanyInfoServiceImpl  extends BaseServiceImpl<Long, CompanyInfo, CompanyInfoDto, CompanyInfoView>  implements ICompanyInfoService{
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Resource
	private ICompanyInfoMapper companyInfoMapper;
	@Resource
	private ICompanyMemberDayMapper companyMemberDayMapper;
	@Resource
	private ICompanyAgeGroupMapper companyAgeGroupMapper;
	/** @description: 添加公司信息
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午7:29:26 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject addCompanyInfo(CompanyInfo e) {
		JSONObject json = new JSONObject();
		e.setCreateTime(new Date());
		e.setCreateUserId(e.getUserCache().getId());
		e.setCreateUserName(e.getUserCache().getUserName());
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		Integer result = companyInfoMapper.insertGotEntityId(e);
		if(result > 0) {
			json.put("status", "success");
			json.put("msg", this.getInfo(400010022));	// 400010022=添加成功
			//添加缓存
			launch.loadDictCache(DCacheEnum.CompanyInfo, "InitCompanyInfo").get(String.valueOf(e.getId()));
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010023));	// 400010023=添加失败
		}
		return json;
	}

	/** @description: 编辑公司信息
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:01:49 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject editCompanyInfo(CompanyInfo e) {
		JSONObject json = new JSONObject();
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		Integer result = companyInfoMapper.updateSelective(e);
		if(result > 0) {
			json.put("status", "success");
			json.put("msg", this.getInfo(400010024));	// 400010024=更新成功
			//删除缓存
			launch.loadDictCache(DCacheEnum.CompanyInfo, "InitCompanyInfo").del(String.valueOf(e.getId()));
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010025));  // 400010025=更新失败
		}
		return json;
	}

	/** @description: 删除公司信息
	 *
	 * @param e
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:07:00 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject delCompanyInfo(CompanyInfo e) {
		JSONObject json = new JSONObject();
		e.setDeleteFlag(0);//标记为删除状态
		Integer result = companyInfoMapper.updateSelective(e);
		if(result > 0) {
			//删除缓存
			launch.loadDictCache(DCacheEnum.CompanyInfo, "InitCompanyInfo").del(String.valueOf(e.getId()));
			json.put("status", "success");
			json.put("msg", this.getInfo(400010001)); // 400010001=删除成功
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010002));  // 400010002=删除失败
		}
		return json;
	}

	/** @description: 查询公司信息
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:19:11 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject getCompanyInfo(CompanyInfoDto dto) {
		JSONObject json = new JSONObject();
		String entityJson = launch.loadDictCache(DCacheEnum.CompanyInfo, "InitCompanyInfo").get(String.valueOf(dto.getId()));
		if(StringUtils.isNoneBlank(entityJson)) {
			CompanyInfoView view = JSONObject.parseObject(entityJson, CompanyInfoView.class);
			json.put("status", "success");
			json.put("data", view);
			json.put("msg", this.getInfo(400010014));		// 400010014=查询成功
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010034));		// 400010034=后台数据查询失败
		}
		return json;
	}

	/** @description: 
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月23日 上午11:46:50 
	 * @version 1.0.0.1
	 */
	@Transactional
	public JSONObject updateCompanyInfo(CompanyInfoDto dto) {
		JSONObject json = new JSONObject();
		dto.setId(dto.getUserCache().getCid());
		dto.setUpdateTime(new Date());
		dto.setUpdateUserId(dto.getUserCache().getId());
		dto.setUpdateUserName(dto.getUserCache().getUserName());
		//修改公司会员日
		updateCompanyMemberDay(dto);
		//修改公司年龄段
		updateCompanyAgeGroup(dto);
		Integer result = companyInfoMapper.updateSelectiveByDto(dto);
		if(result > 0) {
			json.put("status", "success");
			json.put("msg", this.getInfo(400010024));	// 400010024=更新成功
			//删除缓存
			launch.loadDictCache(DCacheEnum.CompanyInfo, "InitCompanyInfo").del(String.valueOf(dto.getId()));
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010025));  // 400010025=更新失败
		}
		return json;
	}
	
	private void updateCompanyMemberDay(CompanyInfoDto dto) {
		CompanyMemberDay delEntity = new CompanyMemberDay();
		delEntity.setCid(dto.getId());
		delEntity.setDeleteFlag(0);//标记为删除状态
		companyMemberDayMapper.updateSelective(delEntity);
		CompanyMemberDay entity = dto.getCompanyMemberDay();
		if(entity != null) {
			entity.setCreateTime(dto.getUpdateTime());
			entity.setCreateUserId(dto.getId());
			entity.setCreateUserName(dto.getUpdateUserName());
			entity.setUpdateTime(dto.getUpdateTime());
			entity.setUpdateUserId(dto.getId());
			entity.setUpdateUserName(dto.getUpdateUserName());
			entity.setCid(dto.getId());
			companyMemberDayMapper.insertSelective(entity);
		}
		
	}
	private void updateCompanyAgeGroup(CompanyInfoDto dto) {
		CompanyAgeGroup dbEntity = new CompanyAgeGroup();
		dbEntity.setCid(dto.getId());
		dbEntity.setDeleteFlag(0);//标记为删除状态
		companyAgeGroupMapper.updateSelective(dbEntity);
		if(dto.getCompanyAgeGroups() != null) {
			for (CompanyAgeGroup e:dto.getCompanyAgeGroups()) {
				e.setCid(dto.getId());
				e.setCreateTime(dto.getUpdateTime());
				e.setCreateUserId(dto.getId());
				e.setCreateUserName(dto.getUpdateUserName());
				e.setUpdateTime(dto.getUpdateTime());
				e.setUpdateUserId(dto.getId());
				e.setUpdateUserName(dto.getUpdateUserName());
				e.setDeleteFlag(1);
			}
		    companyAgeGroupMapper.batchInsert(dto.getCompanyAgeGroups());
		}
		
	}

	/** @description: 
	 *
	 * @param dto
	 * @return 
	 * @author wanghao
	 * @date 2018年11月23日 下午2:59:09 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject selectCompanyInfo(CompanyInfoDto dto) {
		JSONObject json = new JSONObject();
		dto.setId(dto.getUserCache().getCid());
		String entityJson = launch.loadDictCache(DCacheEnum.CompanyInfo, "InitCompanyInfo").get(String.valueOf(dto.getId()));
		if(StringUtils.isNoneBlank(entityJson)) {
			CompanyInfoView view = JSONObject.parseObject(entityJson, CompanyInfoView.class);
			json.put("status", "success");
			json.put("data", view);
			json.put("msg", this.getInfo(400010014));		// 400010014=查询成功
		}else {
			json.put("status", "error");
			json.put("msg", this.getInfo(400010034));		// 400010034=后台数据查询失败
		}
		return json;
	}
}
