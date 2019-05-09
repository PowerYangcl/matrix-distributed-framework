package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class CompanyInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6736633349481291241L;
    /** 公司信息表id*/
    private Long id;

    /** 公司名称*/
    private String name;

    /** 公司介绍*/
    private String intro;

    /** 经营类型*/
    private String business;

    /** 营业执照url*/
    private String businessLicense;

    /** 关键字*/
    private String keyWord;

    /** 公司logo信息url*/
    private String logo;

    /** 联系电话*/
    private String officePhone;

    /** 公司地址*/
    private String address;

    /** 客户类型 1连锁 2单店*/
    private Integer type;

    /** 合同附件 url地址*/
    private String leaseCcessories;

    /** 客户联系人*/
    private String contactPerson;

    /** 联系方式*/
    private String contactPersonPhone;

    /** 联系人职位*/
    private String contactPersonPosition;

    /** 联系人地址*/
    private String contactPersonAddress;

    /** 短信充值数量*/
    private Integer smsNum;

    /** 标记用户是否进行过充值 0没充过 1充过*/
    private Integer smsRecharge;

    /** 公司全局短信阈值*/
    private Integer smsThreshold;

    /** 行业/职业树字典表id*/
    private Long dictVocationInfoId;

    /** 是否进行了微信授权 默认0 未授权 1授权*/
    private Integer wechatAuth;

    /** 统一社会信用代码*/
    private String unifiedSocialCreditCode;

    /** 注册资本 单位万元*/
    private Integer registeredCapital;

    /** 注册机构|等级机关*/
    private String registrationAuthority;

    /** 公司成立时间*/
    private Date establishTime;

    /** 公司类型|类型： 股份有限公司(台港澳与境内合资、未上市)*/
    private String companyType;

    /** 经营范围*/
    private String remark;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 登陆背景图
     */
    private String loginBackground;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business == null ? null : business.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone == null ? null : officePhone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLeaseCcessories() {
        return leaseCcessories;
    }

    public void setLeaseCcessories(String leaseCcessories) {
        this.leaseCcessories = leaseCcessories == null ? null : leaseCcessories.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone == null ? null : contactPersonPhone.trim();
    }

    public String getContactPersonPosition() {
        return contactPersonPosition;
    }

    public void setContactPersonPosition(String contactPersonPosition) {
        this.contactPersonPosition = contactPersonPosition == null ? null : contactPersonPosition.trim();
    }

    public String getContactPersonAddress() {
        return contactPersonAddress;
    }

    public void setContactPersonAddress(String contactPersonAddress) {
        this.contactPersonAddress = contactPersonAddress == null ? null : contactPersonAddress.trim();
    }

    public Integer getSmsNum() {
        return smsNum;
    }

    public void setSmsNum(Integer smsNum) {
        this.smsNum = smsNum;
    }

    public Integer getSmsRecharge() {
        return smsRecharge;
    }

    public void setSmsRecharge(Integer smsRecharge) {
        this.smsRecharge = smsRecharge;
    }

    public Integer getSmsThreshold() {
        return smsThreshold;
    }

    public void setSmsThreshold(Integer smsThreshold) {
        this.smsThreshold = smsThreshold;
    }

    public Long getDictVocationInfoId() {
        return dictVocationInfoId;
    }

    public void setDictVocationInfoId(Long dictVocationInfoId) {
        this.dictVocationInfoId = dictVocationInfoId;
    }

    public Integer getWechatAuth() {
        return wechatAuth;
    }

    public void setWechatAuth(Integer wechatAuth) {
        this.wechatAuth = wechatAuth;
    }

    public String getUnifiedSocialCreditCode() {
        return unifiedSocialCreditCode;
    }

    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode == null ? null : unifiedSocialCreditCode.trim();
    }

    public Integer getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Integer registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public void setRegistrationAuthority(String registrationAuthority) {
        this.registrationAuthority = registrationAuthority == null ? null : registrationAuthority.trim();
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getLoginBackground() {
		return loginBackground;
	}

	public void setLoginBackground(String loginBackground) {
		this.loginBackground = loginBackground;
	}
    
}