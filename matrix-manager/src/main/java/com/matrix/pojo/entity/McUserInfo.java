package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;

public class McUserInfo extends BaseEntity{

	private static final long serialVersionUID = 7497311251212822363L;
	private Long id;
    private Long mcSellerCompanyId;
    private String userName;
    private String password;
    private String idcard;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String remark;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMcSellerCompanyId() {
		return mcSellerCompanyId;
	}
	public void setMcSellerCompanyId(Long mcSellerCompanyId) {
		this.mcSellerCompanyId = mcSellerCompanyId;
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
}
























