package com.matrix.pojo.view;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**
 * @description: 多表联合视图
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月25日 下午3:05:01 
 * @version 1.0.0
 */
public class McUserInfoView implements Serializable {
	
	private static final long serialVersionUID = 3770893646228836140L;
	
	//	McUserInfo实体数据
	private Long id; 
	private Long cid;
	private Long tenantInfoId;
    private String userName;
    private String userCode;
    private Long mcOrganizationId;
    private String password;
    private String type;	// leader Leader后台用户|admin 其他平台管理员|user其他平台用户
    private Integer flag;
    private String idcard;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String qq;
    private String remark;
    private Date createTime;
    private Integer deleteFlag;
    private String picUrl;
    private String pageCss;
    private String platform;
    
    // type=user|则此字段生效，为mc_user_info_organization表的mc_organization_id记录集合
    private List<Long> orgidList; 
    
    private JSONObject mcOrg;  // mc_organization表对象，代表当前用户的归属组织，mcOrganizationId=0时此处为null
    
    /**
     *  店铺列表(cid != 0等情况)：用户可能为多个店铺。
     *  key: "key-" + cid
     */
    private Map<String , JSONObject> shopInfoMap = new HashMap<String , JSONObject>(); 
    
    
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Long getMcOrganizationId() {
		return mcOrganizationId;
	}
	public void setMcOrganizationId(Long mcOrganizationId) {
		this.mcOrganizationId = mcOrganizationId;
	}
	public List<Long> getOrgidList() {
		return orgidList;
	}
	public void setOrgidList(List<Long> orgidList) {
		this.orgidList = orgidList;
	}
	public JSONObject getMcOrg() {
		return mcOrg;
	}
	public void setMcOrg(JSONObject mcOrg) {
		this.mcOrg = mcOrg;
	}
	public Map<String, JSONObject> getShopInfoMap() {
		return shopInfoMap;
	}
	public void setShopInfoMap(Map<String, JSONObject> shopInfoMap) {
		this.shopInfoMap = shopInfoMap;
	}
	public Long getTenantInfoId() {
		return tenantInfoId;
	}
	public void setTenantInfoId(Long tenantInfoId) {
		this.tenantInfoId = tenantInfoId;
	}
	
}



























