package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class AcApiProjectView extends BaseView{

	private static final long serialVersionUID = -8469464889923652199L;

	private Long id;
    private String target;
    private String atype;  // 接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
    private String serviceUrl; // 对应一个Tomcat web项目的服务器部署浏览器路径|也可以是一个Nginx前端负载路径
    private Integer aflag;
    private String creator;
    private String updater;
    
    
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
}














































