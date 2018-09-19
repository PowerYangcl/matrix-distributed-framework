package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class UserDemo extends BaseEntity{
	
	private static final long serialVersionUID = -1858440711882179611L;
	private Long id;
    private String userName;
    private String password;
    private String idNumber;
    private Integer sex;
    private String mobile;
    private String email;
  // private Date birthday;
  // 数据库中是Data类型，但是在页面传递到SpringMvc的时候会出现400错误，意味着参数类型与实体的不对应
  // 此时需要将Date类型的字段改成String类型，数据的插入不会受影响。
    private String birthday;
    
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
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}  
}