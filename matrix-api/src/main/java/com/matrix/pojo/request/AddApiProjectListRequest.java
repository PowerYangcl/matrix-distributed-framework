package com.matrix.pojo.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.matrix.base.BaseClass;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddApiProjectListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 5789538769462434088L;
	
	@NotBlank(message = "600010060")		// 项目名称不得为空
	@Length(max = 52, message = "100020117")		// 100020117=字符串过长
	private String target;
	
	@NotBlank(message = "100010126")		// 100010126=请求参数不允许为空
    private String atype;  		// 接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口
    
	@NotBlank(message = "100010126")		// 100010126=请求参数不允许为空
    private String serviceUrl; 		// 对应一个Tomcat web项目的服务器部署浏览器路径|也可以是一个Nginx前端负载路径
    
	private McUserInfoView userCache;
	
	public AcApiProject buildAjaxBtnApiProjectAdd() {
		AcApiProject e = new AcApiProject();
		e.setTarget(target);
		e.setAtype(atype);
		e.setServiceUrl(serviceUrl);
		e.buildAddCommon(userCache);
		return e;
	}
}
