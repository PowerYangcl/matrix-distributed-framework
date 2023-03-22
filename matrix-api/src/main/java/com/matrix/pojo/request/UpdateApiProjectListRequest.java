package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.matrix.base.BaseClass;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateApiProjectListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 4031131855729422206L;
	
	@NotNull(message = "100020111")			// 100020111=主键丢失
	private Long id;
	
	@NotBlank(message = "600010060")			// 项目名称不得为空
	private String target;
    private String atype;  		// 接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
    private String serviceUrl; 		// 对应一个Tomcat web项目的服务器部署浏览器路径|也可以是一个Nginx前端负载路径
    
	private McUserInfoView userCache;
	
	public AcApiProject buildAjaxBtnApiProjectEdit() {
		AcApiProject e = new AcApiProject();
		e.setId(id);
		e.setTarget(target);
		e.setAtype(atype);
		e.setServiceUrl(serviceUrl);
		e.buildUpdateCommon(userCache);
		return e;
	}
}
