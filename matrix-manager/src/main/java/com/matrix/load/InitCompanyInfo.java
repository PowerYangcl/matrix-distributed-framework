package com.matrix.load;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.ICompanyAgeGroupMapper;
import com.matrix.dao.ICompanyInfoMapper;
import com.matrix.dao.ICompanyMemberDayMapper;
import com.matrix.pojo.dto.CompanyInfoDto;
import com.matrix.pojo.entity.CompanyAgeGroup;
import com.matrix.pojo.entity.CompanyMemberDay;
import com.matrix.pojo.view.CompanyInfoView;
/**
 * @description: 公司信息缓存
 *
 * @author wanghao
 * @date 2018年11月6日 下午6:40:39 
 * @version 1.0.0.1
 * 
 * {
	"msg": "查询成功",
	"data": {
		"createTime": "2018-09-22 13:56:22",
		"createUserId": "1",
		"createUserName": "admin",
		"updateTime": "2018-11-23 14:42:41",
		"updateUserId": "2001",
		"updateUserName": "admin-lqx",
		"deleteFlag": 1,
		"userCache": null,
		"id": "2",
		"name": "北京隆庆祥服饰有限公司",
		"intro": "北京隆庆祥服饰有限公司",
		"business": null,
		"businessLicense": null,
		"keyWord": null,
		"logo": null,
		"officePhone": null,
		"address": "北京大兴亦庄",
		"type": 0,
		"leaseCcessories": null,
		"contactPerson": "滴滴的车",
		"contactPersonPhone": "18500977248",
		"contactPersonPosition": null,
		"contactPersonAddress": null,
		"smsNum": 0,
		"smsRecharge": 0,
		"smsThreshold": 10000,
		"dictVocationInfoId": null,
		"wechatAuth": 0,
		"unifiedSocialCreditCode": "",
		"registeredCapital": 200,
		"registrationAuthority": "",
		"establishTime": null,
		"companyType": "1",
		"remark": null,
		"systemName": "会员管理平台",
		"loginBackground": "测试url",
		"companyMemberDay": {
			"startIndex": 1,
			"pageSize": 10,
			"orderBy": null,
			"createTime": "2018-11-23 14:42:41",
			"createUserId": "2",
			"createUserName": "admin-lqx",
			"updateTime": "2018-11-23 14:42:41",
			"updateUserId": "2",
			"updateUserName": "admin-lqx",
			"deleteFlag": 1,
			"userCache": null,
			"id": "5",
			"cid": "2",
			"timeType": 1,
			"startTime": "2018-11-22",
			"endTime": "2018-11-25"
		},
		"companyAgeGroups": [{
			"startIndex": 1,
			"pageSize": 10,
			"orderBy": null,
			"createTime": null,
			"createUserId": null,
			"createUserName": null,
			"updateTime": null,
			"updateUserId": null,
			"updateUserName": null,
			"deleteFlag": null,
			"userCache": null,
			"id": "5",
			"cid": "2",
			"name": "而立之年",
			"startAge": 30,
			"endAge": 40
		}, {
			"startIndex": 1,
			"pageSize": 10,
			"orderBy": null,
			"createTime": null,
			"createUserId": null,
			"createUserName": null,
			"updateTime": null,
			"updateUserId": null,
			"updateUserName": null,
			"deleteFlag": null,
			"userCache": null,
			"id": "6",
			"cid": "2",
			"name": "不惑之年",
			"startAge": 40,
			"endAge": 50
		}]
	},
	"status": "success"
}
 */
public class InitCompanyInfo extends BaseClass implements ILoadCache<String> {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private ICompanyInfoMapper companyInfoMapper; 
	@Inject
	private ICompanyMemberDayMapper companyMemberDayMapper;
	@Inject
	private ICompanyAgeGroupMapper companyAgeGroupMapper;
	
	@Override
	public String load(String key, String field) {
		CompanyInfoDto dto = new CompanyInfoDto();
		dto.setId(Long.parseLong(key));
		String value ="";
		CompanyInfoView view = companyInfoMapper.findViewByDto(dto);
		if(view!=null) {
			//公司会员日
			CompanyMemberDay memberDay = new CompanyMemberDay();
			memberDay.setCid(view.getId());
			memberDay = companyMemberDayMapper.findByType(memberDay);
			view.setCompanyMemberDay(memberDay);
			//公司年龄段配置
			CompanyAgeGroup ageGroup = new CompanyAgeGroup();
			ageGroup.setCid(view.getId());
			List<CompanyAgeGroup>ageGroups = companyAgeGroupMapper.findList(ageGroup);
			view.setCompanyAgeGroups(ageGroups);
			
			value = JSONObject.toJSONString(view);
			launch.loadDictCache(DCacheEnum.CompanyInfo , null).set(key , value , 30*24*60*60);
		}
	    return value;
	}

}





























