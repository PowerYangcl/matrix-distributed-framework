package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class McSysFunction extends BaseEntity{
	
	private static final long serialVersionUID = 5229443632033099730L;
	
	private Long id;
    private Long mcSellerCompanyId;
    private String name;
    private String parentId;
    private Integer seqnum;
    private Integer navType;
    private String platform; 			// 平台默认标识码|nav_type=0，此处为系统生成默认值
    private String styleClass;
    private String styleKey;
    private String funcUrl;
    private String remark;
    /**
     * 按钮节点所在页面的位置，只有只有导航树的最后一层：按钮节点才会有。
     * 	10001：功能区域(在查询区域上部。如：添加按钮、导出按钮等等)；
     * 	10002：查询区域(查询、重置等等)；
     * 	10003：数据区域 (即：页面列表所在区域。如：编辑、修改、删除、授权、弹出层等等，多数为a标签。)
     */
    private String btnArea; 
    /**
     * 元素ID标识 配合btn_area使用。btnArea = 10003，则eleValue在页面表达为一个类(html class)
     * btnArea = 10001 或 btnArea = 10002 则eleValue在页面表达为一个id(html id)
     */
    private String eleValue;
    
    
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}
	public Integer getNavType() {
		return navType;
	}
	public void setNavType(Integer navType) {
		this.navType = navType;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getStyleKey() {
		return styleKey;
	}
	public void setStyleKey(String styleKey) {
		this.styleKey = styleKey;
	}
	public String getFuncUrl() {
		return funcUrl;
	}
	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBtnArea() {
		return btnArea;
	}
	public void setBtnArea(String btnArea) {
		this.btnArea = btnArea;
	}
	public String getEleValue() {
		return eleValue;
	}
	public void setEleValue(String eleValue) {
		this.eleValue = eleValue;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}   
}







































