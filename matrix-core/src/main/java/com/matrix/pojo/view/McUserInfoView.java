package com.matrix.pojo.view;

import java.util.Date;

import com.matrix.base.BaseView;


/**
 * @description: 多表联合视图
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月25日 下午3:05:01 
 * @version 1.0.0
 */
public class McUserInfoView extends BaseView{
	
	private static final long serialVersionUID = 3770893646228836140L;
	
	//	McUserInfo实体数据
	private Long id; 
    private String userName;
    private String password;
    private String idcard;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String remark;
    private Date createTime;
    private Integer deleteFlag;
    
//    McUserInfoExt实体数据
    private String picUrl;
    private String pageCss;
    private String platform;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPageCss() {
		return pageCss;
	}
	public void setPageCss(String pageCss) {
		this.pageCss = pageCss;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
}



























