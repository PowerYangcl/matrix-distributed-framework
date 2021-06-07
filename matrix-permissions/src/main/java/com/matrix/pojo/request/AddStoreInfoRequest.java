package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddStoreInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 2514542612578002868L;

	private McUserInfoView userCache;
	
	/** 客户id*/
    private Long cid;
	
    /** 门店名称*/
    private String name;
    
    private Long mcOrganizationId;

    /** 门店省份id*/
    private Long provinceId;

    /** 门店城市id*/
    private Long cityId;

    /** 门店区县id*/
    private Long areaId;

    /** 门店详细地址*/
    private String address;

    /** 门店联系电话*/
    private String phone;

    /** 门店星级*/
    private Short starLevel;

    /** 1=加盟店 2直营店 3代理商 4连锁店 0其他*/
    private Short type;

    /** 0 暂停营业 1正常运营*/
    private Short status;

    /** 暂停营业原因*/
    private String remark;

    /** 门店所属行业id*/
    private Long dictVocationInfoId;

    /** 地理位置:经度*/
    private String longitude;

    /** 地理位置:纬度*/
    private String latitude;

    /** 负责人id*/
    private Long managerId;
    
    public StoreInfo buildAddStoreInfo() {
    	StoreInfo e = new StoreInfo();
    	e.setCid(cid);
    	e.setName(name);
    	e.setMcOrganizationId(mcOrganizationId);
    	e.setProvinceId(provinceId);
    	e.setCityId(cityId);
    	e.setAreaId(areaId);
    	e.setAddress(address);
    	e.setPhone(phone);
    	e.setStarLevel(starLevel);
    	e.setType(type);
    	e.setStatus(status);
    	e.setRemark(remark);
    	e.setDictVocationInfoId(dictVocationInfoId);
    	e.setLongitude(longitude);
    	e.setLatitude(latitude);
    	e.setManagerId(managerId);
    	e.buildAddCommon(userCache);
    	return e;
    }
    
    public Result<?> validateAddStoreInfo() {
    	if(StringUtils.isBlank(name)) {		// 100020103=参数缺失：{0}
    		return Result.ERROR(this.getInfo(100020103, "name"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(StringUtils.isBlank(address)) {
    		return Result.ERROR(this.getInfo(100020103, "address"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(StringUtils.isBlank(phone)) {
    		return Result.ERROR(this.getInfo(100020103, "phone"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(StringUtils.isBlank(longitude)) {
    		return Result.ERROR(this.getInfo(100020103, "longitude"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(StringUtils.isBlank(latitude)) {
    		return Result.ERROR(this.getInfo(100020103, "latitude"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(null == provinceId) {
    		return Result.ERROR(this.getInfo(100020103, "provinceId"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(null == cityId) {
    		return Result.ERROR(this.getInfo(100020103, "cityId"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(null == areaId) {
    		return Result.ERROR(this.getInfo(100020103, "areaId"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(null == managerId) {
    		return Result.ERROR(this.getInfo(100020103, "managerId"), ResultCode.MISSING_ARGUMENT);
    	}
		return Result.SUCCESS();
	}
}














