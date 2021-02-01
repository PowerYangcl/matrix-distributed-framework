package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import java.io.Serializable;

public class StoreInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3273549383221413337L;
    /** 注册门店表id*/
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getMcOrganizationId() {
		return mcOrganizationId;
	}

	public void setMcOrganizationId(Long mcOrganizationId) {
		this.mcOrganizationId = mcOrganizationId;
	}

	public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Short getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Short starLevel) {
        this.starLevel = starLevel;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getDictVocationInfoId() {
        return dictVocationInfoId;
    }

    public void setDictVocationInfoId(Long dictVocationInfoId) {
        this.dictVocationInfoId = dictVocationInfoId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}