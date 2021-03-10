package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class AcApiInfo extends BaseEntity{
	
	private static final long serialVersionUID = 7388908004393425485L;
	
	private Long id;
    private String name;  // 接口中文描述   树展示使用
    private String target; // 系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识
    private String dtoInfo; // 接口请求参数描述
    private String atype;
    private String module;
    private String processor;
    private Integer domain;
    private Long parentId;
    private Integer seqnum;
    private Integer discard;
    private Integer login;  // 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
    private String remark;
    
    
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
		this.name = name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getDtoInfo() {
		return dtoInfo;
	}
	public void setDtoInfo(String dtoInfo) {
		this.dtoInfo = dtoInfo;
	}
	public String getAtype() {
		return atype;
	}
	public void setAtype(String atype) {
		this.atype = atype;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public Integer getDomain() {
		return domain;
	}
	public void setDomain(Integer domain) {
		this.domain = domain;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}
	public Integer getDiscard() {
		return discard;
	}
	public void setDiscard(Integer discard) {
		this.discard = discard;
	}
	public Integer getLogin() {
		return login;
	}
	public void setLogin(Integer login) {
		this.login = login;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

















