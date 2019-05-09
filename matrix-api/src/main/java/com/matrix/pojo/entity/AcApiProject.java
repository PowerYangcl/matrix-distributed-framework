package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

/**
 * @description: api所属项目|每一个web-api项目会对应这里的一条记录，然后由此向下延展其提供的接口 
 *
 * @author Yangcl
 * @date 2018年10月8日 上午11:30:18 
 * @version 1.0.0.1
 */
public class AcApiProject extends BaseEntity{
	
	private static final long serialVersionUID = -908554295129017669L;
	
	private Long id;
    private String target;
    private String atype;  // 接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
    private String serviceUrl; // 对应一个Tomcat web项目的服务器部署浏览器路径|也可以是一个Nginx前端负载路径
    private Integer aflag;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getAtype() {
		return atype;
	}
	public void setAtype(String atype) {
		this.atype = atype;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public Integer getAflag() {
		return aflag;
	}
	public void setAflag(Integer aflag) {
		this.aflag = aflag;
	}
}